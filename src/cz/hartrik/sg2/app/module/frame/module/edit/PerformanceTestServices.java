
package cz.hartrik.sg2.app.module.frame.module.edit;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.app.module.frame.Application;
import cz.hartrik.sg2.app.module.frame.module.script.ToolFactory;
import cz.hartrik.sg2.app.module.frame.service.Service;
import cz.hartrik.sg2.app.module.frame.service.ServiceProvider;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.SourceableBrush;
import cz.hartrik.sg2.brush.Wrapper;
import cz.hartrik.sg2.engine.EngineListenerDef;
import cz.hartrik.sg2.engine.ThreadFactoryName;
import cz.hartrik.sg2.random.RatioChance;
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

/**
 * Poskytuje služby pro testování výkonu aplikace.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@ServiceProvider
public class PerformanceTestServices {

    public static final String SERVICE_TEST_SAND_FALL = "test-sand-fall";
    public static final String SERVICE_TEST_BENCHMARK = "test-benchmark";

    // služby

    @Service(SERVICE_TEST_SAND_FALL)
    public void sandTest(Application app) {
        Brush sandBrush = app.getBrushManager().getBrush(40);
        Source sandSource = createSource(sandBrush);
        Brush blackholeBrush = app.getBrushManager().getBrush(202);

        app.getSyncTools().synchronize((world) -> {
            fall(world, sandSource, blackholeBrush);
        });
    }


    private volatile boolean benchmarkIsRunning = false;

    private static final ThreadFactory TF
           = new ThreadFactoryName("SG2 - benchmark [%d]");

    @Service(SERVICE_TEST_BENCHMARK)
    public void benchmark(Application app) {
        if (benchmarkIsRunning)
            return;

        benchmarkIsRunning = true;

        Brush sandBrush = app.getBrushManager().getBrush(40);
        Brush waterBrush = app.getBrushManager().getBrush(70);
        Source sandSource = createSource(sandBrush);
        Brush blackHole = app.getBrushManager().getBrush(202);

        Thread thread = TF.newThread(() -> {
            app.getSyncTools().stopBoth();

            System.out.println("### FALING SAND ###");
            benchmark(800, 500, 100, (w) -> fall(w, sandSource, blackHole), app);
            benchmark(800, 500, 50,  (w) -> fall(w, sandSource, blackHole), app);
            benchmark(800, 500, 20,  (w) -> fall(w, sandSource, blackHole), app);
            benchmark(800, 500, 10,  (w) -> fall(w, sandSource, blackHole), app);
            benchmark(800, 500, 5,   (w) -> fall(w, sandSource, blackHole), app);

            System.out.println("### SAND CUBE ###");
            benchmark(800, 500, 100, (w) -> cube(w, sandBrush), app);
            benchmark(800, 500, 50,  (w) -> cube(w, sandBrush), app);
            benchmark(800, 500, 20,  (w) -> cube(w, sandBrush), app);
            benchmark(800, 500, 10,  (w) -> cube(w, sandBrush), app);
            benchmark(800, 500, 5,   (w) -> cube(w, sandBrush), app);

            System.out.println("### WATER CUBE ###");
            benchmark(800, 500, 100, (w) -> cube(w, waterBrush), app);
            benchmark(800, 500, 50,  (w) -> cube(w, waterBrush), app);
            benchmark(800, 500, 20,  (w) -> cube(w, waterBrush), app);
            benchmark(800, 500, 10,  (w) -> cube(w, waterBrush), app);
            benchmark(800, 500, 5,   (w) -> cube(w, waterBrush), app);

            app.setUpCanvas(800, 500);
            app.getEngine().processorStart();

            benchmarkIsRunning = false;
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void benchmark(int w, int h, int chunkSize, Consumer<World> c, Application app) {
        System.out.print(String.format("# %d×%d (%d)", w, h, chunkSize));

        // nastavení plátna

        ModularWorld world = new ModularWorld(w, h, chunkSize, BasicElement.AIR);
        app.setUpCanvas(world);
        c.accept(world);

        // nastavení enginu

        AtomicInteger i = new AtomicInteger(0);

        app.getEngine().setMaxCycles(1000);
        app.getEngine().setListener(new EngineListenerDef() {
            @Override
            public void processorCycleEnd() {
                i.incrementAndGet();
            }
        });

        // spuštění benchmarku

        app.getEngine().processorStart();
        app.getEngine().rendererStart();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        int cycles = i.get();

        System.out.print("\t => " + cycles + " (" + (cycles / 5) + "/s)");
        System.out.println();
        app.getSyncTools().stopBoth();
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

}