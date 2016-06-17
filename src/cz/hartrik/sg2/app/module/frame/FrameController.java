
package cz.hartrik.sg2.app.module.frame;

import cz.hartrik.sg2.app.module.canvas.CanvasWithCursor;
import cz.hartrik.sg2.app.module.canvas.Cursorable;
import cz.hartrik.sg2.app.module.canvas.MouseControllerExt;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.frame.module.script.ToolFactory;
import cz.hartrik.sg2.app.sandbox.element.StandardBrushCollection;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.jfx.JFXControls;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.brush.manage.ObservedBrushManagerBC;
import cz.hartrik.sg2.engine.*;
import cz.hartrik.sg2.process.HeatProcessor;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.BasicElement;
import cz.hartrik.sg2.world.ChunkedArea;
import cz.hartrik.sg2.world.ModularWorld;
import cz.hartrik.sg2.world.module.BasicWorldModuleManager;
import cz.hartrik.sg2.world.module.WorldModule;
import cz.hartrik.sg2.world.module.WorldModuleManager;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

/**
 * Kontroler GUI.
 *
 * @version 2015-04-06
 * @author Patrik Harag
 */
public class FrameController extends FrameControllerTemplate implements Initializable {

    private final ImageView imageView = new ImageView();

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

    // service manager

    private final ServiceManager serviceManager = new ServiceManager();

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    // on set-up services

    private final List<Runnable> onSetUp = new LinkedList<>();

    public void addOnSetUp(Runnable runnable) {
        onSetUp.add(runnable);
    }

    // brush manager

    private final ObservedBrushManagerBC<Brush> brushManager
            = StandardBrushCollection.create(
                () -> new ObservedBrushManagerBC<>(
                        StandardBrushCollection.getVersion(),
                        StandardBrushCollection::getBCConverter),
                () -> engine.getProcessor().getTools());

    public BrushManager<Brush> getBrushManager() {
        return brushManager;
    }

    // engine sync tools

    private EngineSyncToolsMW syncTools;

    public EngineSyncToolsMW getSyncTools() {
        return syncTools;
    }

    //

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle rb) {
        super.initialize(url, rb);

        // plátno
        this.canvas = new CanvasWithCursor(scrollPane, imageView);

        controls.primaryToolProperty().addListener((ob, o, tool) -> {
            canvas.removeCursor();
            if (tool instanceof Cursorable)
                canvas.setCursor(((Cursorable) tool).createCursor(canvas));
        });

        controls.setPrimaryTool(ToolFactory.getInstance().centeredSquare(10));
        controls.setSecondaryTool(ToolFactory.getInstance().centeredSquare(10));

        controls.setPrimaryBrush(brushManager.getBrush(10));
        controls.setSecondaryBrush(brushManager.getBrush(1));

        new MouseControllerExt(imageView, controls,
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
        setUpCanvas(new ModularWorld(width, height, BasicElement.AIR, sup), width, height);
    }

    public void setUpCanvas(ModularWorld world) {
        setUpCanvas(world, world.getWidth(), world.getHeight());
    }

    protected void setUpCanvas(ModularWorld world, int width, int height) {
        boolean processorRunning = true;

        if (engine != null) {
            processorRunning = !engine.isProcessorStopped();

            engine.rendererStop();
            engine.processorStop();
        }

        this.world = world;

        JFXRenderer renderer = rendererSupplier.apply(world);
        Processor proc = new HeatProcessor(world, new Tools(world, brushManager));

        engine = new JFXEngineT<>(world, proc, renderer, imageView);
        syncTools = new EngineSyncToolsMW(engine, world);

        for (Runnable runnable : onSetUp) {
            runnable.run();
        }

        engine.rendererStart();
        if (processorRunning)
            engine.processorStart();
    }

}