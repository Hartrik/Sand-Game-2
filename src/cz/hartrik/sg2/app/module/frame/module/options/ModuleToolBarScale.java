package cz.hartrik.sg2.app.module.frame.module.options;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.frame.module.ToolBarModule;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;

/**
 * Přidá do toolbaru slider pro změnu přiblížení a zaregistruje službu pro
 * reset přiblížení.
 * 
 * @version 2014-11-29
 * @author Patrik Harag
 */
public class ModuleToolBarScale extends ToolBarModule<Frame, FrameController> {
    
    private final String text;
    private final double min, max;
    private final double initial;
    
    public ModuleToolBarScale(String text) {
        this(text, 1., 10., 1.);
    }
    
    public ModuleToolBarScale(String text,
            double min, double max, double initial) {
        
        super(true);
        this.text = text;
        this.min = min;
        this.max = max;
        this.initial = initial;
    }
    
    @Override
    public void register(Frame stage, FrameController controller,
            ServiceManager manager) { }

    @Override
    public void setUp(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        final Label lDescription = new Label(text);
        final Label lScaleFactor = new Label(format(initial));
        lScaleFactor.setFont(Font.font("Courier New", 11));
        
        final Slider slider = new Slider(min, max, initial);
        slider.setBlockIncrement(1);
        
        slider.valueProperty().addListener((ov, o, scale) -> {
            lScaleFactor.setText(format(scale.doubleValue()));
            controller.getCanvas().update(scale.doubleValue());
        });
        
        manager.register(OptionsServices.SERVICE_SCALE_RESET,
                () -> slider.setValue(1));
        
        controller.getToolBar().getItems()
                .addAll(lDescription, lScaleFactor, slider);
    }
    
    protected String format(double value) {
        return String.format((value < 10) ? " %.2fx" : "%.2fx", value);
    }
    
}