package cz.hartrik.sg2.app.module.tools;


import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

/**
 * Abstraktní třída pro panel s nastavením štětce.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public abstract class PanelTool {

    protected final int min, max, def;
    protected Pane pane;

    public PanelTool(int min, int max, int def) {
        this.min = min;
        this.max = max;
        this.def = def;
    }

    public abstract void updateTool();

    public Pane getPane() {
        return pane;
    }

    protected static Spinner<Integer> createSpinner(int min, int max, int def) {
        Spinner<Integer> spinner = new Spinner<>(min, max, def);
        spinner.setEditable(true);
        spinner.getValueFactory().setConverter(new StringConverterImpl(min, max, def));
        spinner.focusedProperty().addListener((ov, o, n) -> {
            if (!n) commitSpinner(spinner);
        });

        return spinner;
    }

    /**
     * Uloží aktuální hodnotu spinneru - stejné, jako když se zmáčkne ENTER.
     *
     * @param <T> typ spinneru
     * @param spinner spinner
     */
    protected static <T> void commitSpinner(Spinner<T> spinner) {
        if (!spinner.isEditable()) return;
        String text = spinner.getEditor().getText();
        SpinnerValueFactory<T> valueFactory = spinner.getValueFactory();
        if (valueFactory != null) {
            StringConverter<T> converter = valueFactory.getConverter();
            if (converter != null) {
                T value = converter.fromString(text);
                valueFactory.setValue(value);
            }
        }
    }

}