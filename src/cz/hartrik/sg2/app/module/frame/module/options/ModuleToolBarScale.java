package cz.hartrik.sg2.app.module.frame.module.options;

import cz.hartrik.sg2.app.module.frame.Application;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.ApplicationModule;
import cz.hartrik.sg2.app.module.frame.service.ServiceManager;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;

/**
 * Modul přidá do toolbaru slider pro změnu přiblížení a zaregistruje službu pro
 * reset přiblížení.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class ModuleToolBarScale implements ApplicationModule {

    public static final String SERVICE_SCALE_RESET = "scale-reset";

    private final String text;
    private final double min, max;
    private final double initial;

    public ModuleToolBarScale(String text) {
        this(text, 1.0, 10.0, 1.0);
    }

    public ModuleToolBarScale(String text,
            double min, double max, double initial) {

        this.text = text;
        this.min = min;
        this.max = max;
        this.initial = initial;
    }

    @Override
    public void init(Application app) {
        FrameController controller = app.getController();
        ServiceManager manager = app.getServiceManager();

        final Label lDescription = new Label(text);
        final Label lScaleFactor = new Label(format(initial));
        lScaleFactor.setFont(Font.font("Courier New", 11));

        final Slider slider = new Slider(min, max, initial);
        slider.setBlockIncrement(1);

        slider.valueProperty().addListener((ov, o, scale) -> {
            lScaleFactor.setText(format(scale.doubleValue()));
            controller.getCanvas().update(scale.doubleValue());
        });

        manager.register(SERVICE_SCALE_RESET, () -> slider.setValue(1));

        controller.getToolBar().getItems()
                .addAll(lDescription, lScaleFactor, slider);
    }

    protected String format(double value) {
        return String.format((value < 10) ? " %.2fx" : "%.2fx", value);
    }

}