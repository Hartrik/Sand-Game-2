
package cz.hartrik.sg2.app.module.options;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.FrameController;
import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.module.ApplicationModule;
import cz.hartrik.sg2.app.service.ServiceManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;

/**
 * Modul přidá do toolbaru slider pro změnu přiblížení, inicializuje změnu
 * přiblížení po ctrl+scroll a zaregistruje službu pro reset přiblížení.
 *
 * @version 2017-07-13
 * @author Patrik Harag
 */
public class ModuleToolBarScale implements ApplicationModule {

    public static final String SERVICE_SCALE_RESET = "scale-reset";

    private final double min, max;
    private final double initial;

    public ModuleToolBarScale() {
        this(1.0, 10.0, 1.0);
    }

    public ModuleToolBarScale(double min, double max, double initial) {
        this.min = min;
        this.max = max;
        this.initial = initial;
    }

    @Override
    public void init(Application app) {
        FrameController controller = app.getController();
        ServiceManager manager = app.getServiceManager();

        final Label lDescription = new Label(Strings.get("module.misc.scale"));
        final Label lScaleFactor = new Label(format(initial));
        lScaleFactor.setFont(Font.font("Courier New", 11));

        final Slider slider = new Slider(min, max, initial);
        slider.setBlockIncrement(1);

        slider.valueProperty().addListener((ov, o, scale) -> {
            lScaleFactor.setText(format(scale.doubleValue()));
            controller.getCanvas().update(scale.doubleValue());
        });

        manager.register(SERVICE_SCALE_RESET, () -> slider.setValue(1));
        initOnScroll(app, slider);

        controller.getToolBar().getItems()
                .addAll(lDescription, lScaleFactor, slider);
    }

    private void initOnScroll(Application app, Slider slider) {
        Canvas canvas = app.getController().getCanvas().getFxCanvas();
        ScrollPane scrollPane = app.getController().getCanvas().getScrollPane();

        canvas.setOnScroll(e -> {
            if (e.isControlDown()) {
                e.consume();

                // scale
                double deltaY = e.getDeltaY();
                if (deltaY < 0)
                    slider.decrement();
                else if (deltaY > 0)
                    slider.increment();

                // center to mouse position
                scrollPane.setVvalue(e.getY() / canvas.getHeight());
                scrollPane.setHvalue(e.getX() / canvas.getWidth());
            }
        });
    }

    protected String format(double value) {
        return String.format((value < 10) ? " %.2fx" : "%.2fx", value);
    }

}