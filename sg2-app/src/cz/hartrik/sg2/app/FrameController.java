
package cz.hartrik.sg2.app;

import cz.hartrik.sg2.app.extension.canvas.CanvasWithCursor;
import cz.hartrik.sg2.app.extension.canvas.Cursorable;
import cz.hartrik.sg2.app.extension.canvas.MouseControllerExt;
import cz.hartrik.sg2.app.module.script.ToolFactory;
import cz.hartrik.sg2.app.sandbox.element.StandardBrushCollection;
import cz.hartrik.sg2.brush.jfx.JFXControls;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.brush.manage.ObservedBrushManagerBC;
import cz.hartrik.sg2.engine.*;
import cz.hartrik.sg2.engine.process.HeatProcessor;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.engine.render.JFXRenderer;
import cz.hartrik.sg2.engine.render.JFXRendererBlur;
import cz.hartrik.sg2.world.BasicElement;
import cz.hartrik.sg2.world.ChunkedArea;
import cz.hartrik.sg2.world.ModularWorld;
import cz.hartrik.sg2.world.module.BasicWorldModuleManager;
import cz.hartrik.sg2.world.module.WorldModule;
import cz.hartrik.sg2.world.module.WorldModuleManager;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;

/**
 * Kontroler GUI.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class FrameController extends FrameControllerTemplate implements Initializable {

    private final Canvas fxCanvas = new Canvas();

    // world

    private ModularWorld world;

    public ModularWorld getWorld() {
        return world;
    }

    // world modules

    private final List<WorldModule<ModularWorld>> worldModules = new LinkedList<>();

    public void addWorldModule(WorldModule<ModularWorld> module) {
        worldModules.add(module);
        world.getModuleManager().addModule(module);
    }

    public void removeWorldModule(WorldModule<ModularWorld> module) {
        worldModules.remove(module);
        world.getModuleManager().removeModule(module);
    }

    // engine

    private JFXEngine<? extends Processor> engine;

    public JFXEngine<? extends Processor> getEngine() {
        return engine;
    }

    // renderer

    private Function<ChunkedArea, JFXRenderer> rendererSupplier = JFXRendererBlur::new;

    public void setRendererSupplier(
            Function<ChunkedArea, JFXRenderer> rendererSupplier) {

        this.rendererSupplier = rendererSupplier;
    }

    // canvas

    private CanvasWithCursor canvas;

    public CanvasWithCursor getCanvas() {
        return canvas;
    }

    // controls

    private final JFXControls controls = new JFXControls();

    public JFXControls getControls() {
        return controls;
    }

    // on set-up services

    private final List<Runnable> onSetUp = new LinkedList<>();

    public void addOnSetUp(Runnable runnable) {
        onSetUp.add(runnable);
    }

    public boolean removeOnSetUp(Runnable runnable) {
        return onSetUp.remove(runnable);
    }

    // engine state

    private final EngineStateMonitor engineStateMonitor
            = new EngineStateMonitor(this::getEngine);

    public void addOnEngineStateChanged(Consumer<ProcessingState> consumer) {
        engineStateMonitor.addOnEngineStateChanged(consumer);
    }

    public boolean removeOnEngineStateChanged(Consumer<ProcessingState> consumer) {
        return engineStateMonitor.removeOnEngineStateChanged(consumer);
    }

    private void initEngineStateMonitor() {
        engineStateMonitor.start();
    }

    // brush manager

    private final ObservedBrushManagerBC brushManager = StandardBrushCollection.create(
            () -> new ObservedBrushManagerBC(
                    StandardBrushCollection.getVersion(),
                    StandardBrushCollection::getBCConverter),
            () -> engine.getProcessor().getTools());

    public BrushManager getBrushManager() {
        return brushManager;
    }

    // engine sync tools

    private EngineSyncTools<ModularWorld> syncTools;

    public EngineSyncTools<ModularWorld> getSyncTools() {
        return syncTools;
    }

    //

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle rb) {
        super.initialize(url, rb);

        // plátno
        this.canvas = new CanvasWithCursor(scrollPane, fxCanvas);

        controls.primaryToolProperty().addListener((ob, o, tool) -> {
            canvas.removeCursor();
            if (tool instanceof Cursorable)
                canvas.setCursor(((Cursorable) tool).createCursor(canvas));
        });

        controls.setPrimaryTool(ToolFactory.getInstance().centeredSquare(10));
        controls.setSecondaryTool(ToolFactory.getInstance().centeredSquare(10));

        controls.setPrimaryBrush(brushManager.getBrush(10));
        controls.setSecondaryBrush(brushManager.getBrush(1));

        initEngineStateMonitor();

        new MouseControllerExt(fxCanvas, controls,
                this::getEngine, this::getWorld, this::getSyncTools,
                brushManager, canvas).init();
    }

    // metody

    private final Function<ModularWorld, WorldModuleManager<ModularWorld>> sup = (d) ->  {
        BasicWorldModuleManager<ModularWorld> man = new BasicWorldModuleManager<>(d);
        for (WorldModule<ModularWorld> module : worldModules)
            man.addModule(module);
        return man;
    };

    public void setUpCanvas(int width, int height) {
        setUpCanvas(new ModularWorld(width, height, 20, BasicElement.AIR, sup));
    }

    public void setUpCanvas(ModularWorld world) {
        boolean processorRunning = true;

        if (engine != null) {
            processorRunning = !engine.isProcessorStopped();

            engine.shutdown();
        }

        this.world = world;

        JFXRenderer renderer = rendererSupplier.apply(world);
        Processor proc = new HeatProcessor(world, new Tools(world, brushManager));

        engine = new JFXEngineSEQ<>(world, proc, renderer, fxCanvas);
        syncTools = new EngineSyncTools<>(engine, world);

        for (Runnable runnable : onSetUp) {
            runnable.run();
        }

        engine.rendererStart();
        if (processorRunning)
            engine.processorStart();
    }

}