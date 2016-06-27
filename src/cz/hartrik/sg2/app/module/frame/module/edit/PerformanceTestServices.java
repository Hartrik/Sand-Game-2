
package cz.hartrik.sg2.app.module.frame.module.edit;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.Registerable;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.frame.module.script.ToolFactory;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.SourceableBrush;
import cz.hartrik.sg2.brush.Wrapper;
import cz.hartrik.sg2.engine.EngineListenerDef;
import cz.hartrik.sg2.engine.ThreadFactoryName;
import cz.hartrik.sg2.tool.Rectangle;
import cz.hartrik.sg2.world.BasicElement;
import cz.hartrik.sg2.world.ModularWorld;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.special.Source;
import cz.hartrik.sg2.world.element.special.Sourceable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static cz.hartrik.sg2.app.module.frame.module.edit.EditServices.*;

/**
 * @version 2016-06-27
 * @author Patrik Harag
 */
public class PerformanceTestServices implements Registerable {

    protected final FrameController controller;

    private volatile boolean benchmarkIsRunning = false;

    public PerformanceTestServices(FrameController controller) {
        this.controller = controller;
    }

    // pomocné metody

    private void fall(World world, Source top, Brush bottom) {
        world.getTools().clear();

        // horní okraj
        for (int x = 0; x < world.getWidth(); x++)
            world.set(x, 0, top);

        // dolní okraj
        final int y = world.getHeight() - 1;
        for (int x = 0; x < world.getWidth(); x++)
            world.set(x, y, bottom.getElement());
    }

    private void cube(World world, Brush brush) {
        Rectangle square = ToolFactory.getInstance().centeredSquare(200);
        world.take(square, world.getWidth() / 2, world.getHeight() / 2)
                .getTools().fill(brush);
    }

    private Source createSource(Brush brush) {
        SourceableBrush sourceable = (SourceableBrush) Wrapper.unwrap(brush);
        Supplier<Sourceable> supp = sourceable.getSourceSupplier();
        return new Source(Color.RED, RatioChance.of(20), supp);
    }

    // služby

    public void sandTest() {
        Brush sandBrush = controller.getBrushManager().getBrush(40);
        Source sandSource = createSource(sandBrush);
        Brush blackholeBrush = controller.getBrushManager().getBrush(202);

        controller.getSyncTools().synchronize((world) -> {
            fall(world, sandSource, blackholeBrush);
        });
    }

    private static final ThreadFactory TF
           = new ThreadFactoryName("SG2 - benchmark [%d]");

    public void benchmark() {
        if (benchmarkIsRunning)
            return;

        benchmarkIsRunning = true;

        Brush sandBrush = controller.getBrushManager().getBrush(40);
        Brush waterBrush = controller.getBrushManager().getBrush(70);
        Source sandSource = createSource(sandBrush);
        Brush blackHole = controller.getBrushManager().getBrush(202);

        Thread thread = TF.newThread(() -> {
            controller.getSyncTools().stopBoth();

            System.out.println("### FALING SAND ###");
            benchmark(800, 500, 100, (w) -> fall(w, sandSource, blackHole));
            benchmark(800, 500, 50,  (w) -> fall(w, sandSource, blackHole));
            benchmark(800, 500, 20,  (w) -> fall(w, sandSource, blackHole));
            benchmark(800, 500, 10,  (w) -> fall(w, sandSource, blackHole));
            benchmark(800, 500, 5,   (w) -> fall(w, sandSource, blackHole));

            System.out.println("### SAND CUBE ###");
            benchmark(800, 500, 100, (w) -> cube(w, sandBrush));
            benchmark(800, 500, 50,  (w) -> cube(w, sandBrush));
            benchmark(800, 500, 20,  (w) -> cube(w, sandBrush));
            benchmark(800, 500, 10,  (w) -> cube(w, sandBrush));
            benchmark(800, 500, 5,   (w) -> cube(w, sandBrush));

            System.out.println("### WATER CUBE ###");
            benchmark(800, 500, 100, (w) -> cube(w, waterBrush));
            benchmark(800, 500, 50,  (w) -> cube(w, waterBrush));
            benchmark(800, 500, 20,  (w) -> cube(w, waterBrush));
            benchmark(800, 500, 10,  (w) -> cube(w, waterBrush));
            benchmark(800, 500, 5,   (w) -> cube(w, waterBrush));

            controller.setUpCanvas(800, 500);
            controller.getEngine().processorStart();

            benchmarkIsRunning = false;
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void benchmark(int w, int h, int chunkSize, Consumer<World> c) {
        System.out.print(String.format("# %d×%d (%d)", w, h, chunkSize));

        // nastavení plátna

        ModularWorld world = new ModularWorld(w, h, chunkSize, BasicElement.AIR);
        controller.setUpCanvas(world);
        c.accept(world);

        // nastavení enginu

        AtomicInteger i = new AtomicInteger(0);

        controller.getEngine().setMaxCycles(1000);
        controller.getEngine().setListener(new EngineListenerDef() {
            @Override
            public void processorCycleEnd() {
                i.incrementAndGet();
            }
        });

        // spuštění benchmarku

        controller.getEngine().processorStart();
        controller.getEngine().rendererStart();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        int cycles = i.get();

        System.out.print("\t => " + cycles + " (" + (cycles / 5) + "/s)");
        System.out.println();
        controller.getSyncTools().stopBoth();
    }

    // registrační metoda

    @Override
    public void register(ServiceManager serviceManager) {
        serviceManager.register(SERVICE_TEST_SAND_FALL, this::sandTest);
        serviceManager.register(SERVICE_TEST_BENCHMARK, this::benchmark);
    }

}