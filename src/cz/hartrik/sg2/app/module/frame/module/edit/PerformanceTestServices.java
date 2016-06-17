
package cz.hartrik.sg2.app.module.frame.module.edit;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.Registerable;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.SourceableBrush;
import cz.hartrik.sg2.brush.Wrapper;
import cz.hartrik.sg2.world.element.special.Source;
import cz.hartrik.sg2.world.element.special.Sourceable;
import java.util.function.Supplier;

import static cz.hartrik.sg2.app.module.frame.module.edit.EditServices.*;

/**
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class PerformanceTestServices implements Registerable {

    protected final FrameController controller;

    public PerformanceTestServices(FrameController controller) {
        this.controller = controller;
    }

    // pomocné metody

    protected void fallTest(Source top, Brush bottom) {
        controller.getSyncTools().synchronize((world) -> {
            world.getTools().clear();

            // horní okraj
            for (int x = 0; x < world.getWidth(); x++)
                world.set(x, 0, top);

            // dolní okraj
            final int y = world.getHeight() - 1;
            for (int x = 0; x < world.getWidth(); x++)
                world.set(x, y, bottom.getElement());
        });
    }

    // služby

    public void sandTest() {
        Brush sandBrush = controller.getBrushManager().getBrush(40);
        SourceableBrush sourceable = (SourceableBrush) Wrapper.unwrap(sandBrush);
        Supplier<Sourceable> supp = sourceable.getSourceSupplier();
        Source sandSource = new Source(Color.RED, RatioChance.of(20), supp);

        fallTest(sandSource, controller.getBrushManager().getBrush(202));
    }

    // registrační metoda

    @Override
    public void register(ServiceManager serviceManager) {
        serviceManager.register(SERVICE_TEST_SAND_FALL, this::sandTest);
    }

}