
package cz.hartrik.sg2.app.module.edit;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.script.ToolFactory;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.SourceableBrush;
import cz.hartrik.sg2.brush.Wrapper;
import cz.hartrik.sg2.engine.EngineListenerDef;
import cz.hartrik.sg2.engine.ThreadFactoryName;
import cz.hartrik.sg2.random.RatioChance;
import cz.hartrik.sg2.random.XORShiftRandom;
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
 * @version 2017-08-12
 * @author Patrik Harag
 */
@ServiceProvider
public class PerformanceTestServices {

    public static final String SERVICE_TEST_BENCHMARK_1 = "test-sand-fall";
    public static final String SERVICE_TEST_BENCHMARK_2 = "test-benchmark-1";
    public static final String SERVICE_TEST_BENCHMARK_3 = "test-benchmark-2";

    // služby

    @Service(SERVICE_TEST_BENCHMARK_1)
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

    @Service(SERVICE_TEST_BENCHMARK_2)
    public void benchmark1(Application app) {
        if (benchmarkIsRunning)
            return;

        benchmarkIsRunning = true;

        Brush sandBrush = app.getBrushManager().getBrush(40);
        Source sandSource = createSource(sandBrush);
        Brush waterBrush = app.getBrushManager().getBrush(70);
        Brush blackHole = app.getBrushManager().getBrush(202);
        Brush fireBrush = app.getBrushManager().getBrush(130);
        Brush napalmBrush = app.getBrushManager().getBrush(141);
        Source napalmSource = createSource(napalmBrush);
        Brush ironBrush = app.getBrushManager().getBrush(20);

        Thread thread = TF.newThread(() -> {
            app.getSyncTools().stopBoth();

            System.out.println("### FALLING SAND ###");
            benchmark(800, 500, 20, (w) -> fall(w, sandSource, blackHole), app);

            System.out.println("### SAND CUBE ###");
            benchmark(800, 500, 20, (w) -> cube(w, sandBrush), app);

            System.out.println("### WATER CUBE ###");
            benchmark(800, 500, 20, (w) -> cube(w, waterBrush), app);

            System.out.println("### FIRE ###");
            benchmark(800, 500, 20, (w) -> fill(w, fireBrush), app);

            System.out.println("### NAPALM ###");
            benchmark(800, 500, 20, (w) -> {
                fall(w, napalmSource, ironBrush);
                terrain(w, ironBrush);
            }, app);

            System.out.println("### STILL SAND ###");
            benchmark(800, 500, 20, (w) -> {
                fill(w, sandBrush);
            }, app);

            System.out.println("### WARM SAND ###");
            benchmark(800, 500, 20, (w) -> {
                fill(w, sandBrush);
                w.forEachPoint((x, y) -> w.setTemperature(x, y, 500));
            }, app);

            System.out.println("### WARM IRON ###");
            benchmark(800, 500, 20, (w) -> {
                fill(w, ironBrush);
                w.forEachPoint((x, y) -> w.setTemperature(x, y, 500));
            }, app);

            System.out.println("### MOLTEN IRON ###");
            benchmark(800, 500, 20, (w) -> {
                fill(w, ironBrush);
                w.forEachPoint((x, y) -> w.setTemperature(x, y, 2000));
            }, app);

            app.setUpCanvas(800, 500);
            app.getEngine().processorStart();

            benchmarkIsRunning = false;
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Service(SERVICE_TEST_BENCHMARK_3)
    public void benchmark2(Application app) {
        if (benchmarkIsRunning)
            return;

        benchmarkIsRunning = true;

        Brush sandBrush = app.getBrushManager().getBrush(40);
        Brush waterBrush = app.getBrushManager().getBrush(70);
        Source sandSource = createSource(sandBrush);
        Brush blackHole = app.getBrushManager().getBrush(202);

        Thread thread = TF.newThread(() -> {
            app.getSyncTools().stopBoth();

            System.out.println("### FALLING SAND ###");
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
        // horní okraj
        for (int x = 0; x < world.getWidth(); x++)
            world.set(x, 0, top);

        // dolní okraj
        final int y = world.getHeight() - 1;
        for (int x = 0; x < world.getWidth(); x++)
            world.set(x, y, bottom.getElement());
    }

    private void terrain(World world, Brush bottom) {
        final XORShiftRandom rnd = new XORShiftRandom(4545);
        final int maxDiff = 13;
        final int MIN = world.getHeight() - 100;
        final int MAX = world.getHeight() - 10;

        int y = world.getHeight() - 1 - 70;
        for (int x = 0; x < world.getWidth(); x++) {
            y += rnd.nextInt(maxDiff) - maxDiff/2;
            y = Math.max(MIN, y);
            y = Math.min(MAX, y);

            for (int j = world.getHeight() - 1; j > y; j--) {
                world.set(x, j, bottom.getElement());
            }
        }
    }

    private void cube(World world, Brush brush) {
        Rectangle square = ToolFactory.getInstance().centeredSquare(200);
        world.take(square, world.getWidth() / 2, world.getHeight() / 2)
                .getTools().fill(brush);
    }

    private void fill(World world, Brush brush) {
        world.getTools().fill(brush);
    }

    private Source createSource(Brush brush) {
        SourceableBrush sourceable = (SourceableBrush) Wrapper.unwrap(brush);
        Supplier<Sourceable> supp = sourceable.getSourceSupplier();
        return new Source(Color.RED, RatioChance.of(20), supp);
    }

}