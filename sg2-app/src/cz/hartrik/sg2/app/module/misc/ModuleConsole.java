
package cz.hartrik.sg2.app.module.misc;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.ApplicationModule;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import java.util.logging.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @version 2017-08-08
 * @author Patrik Harag
 */
@ServiceProvider
public class ModuleConsole implements ApplicationModule {

    public static final String SERVICE_SHOW_CONSOLE = "console-show";
    public static final String SERVICE_HIDE_CONSOLE = "console-hide";

    private static final double HEIGHT = 300;
    private static final int MARGIN = 10;
    private static final int INPUT_HEIGHT = 30;  // circa
    private static final Duration SLIDE_DURATION = Duration.seconds(0.3);

    private final BooleanProperty visible = new SimpleBooleanProperty(false);
    private TextField input;

    @Override
    public void init(Application app) {
        StackPane rootPane = app.getController().getRootPane();

        SimpleDoubleProperty pos = new SimpleDoubleProperty(0);

        VBox box = new VBox();
        box.getStyleClass().add("console-box");
        box.getChildren().addAll(input = createInput(app), createConsole());
        box.translateYProperty().bind(rootPane.heightProperty().subtract(pos));

        addSlideHandlers(pos, box, app);
        rootPane.getChildren().add(box);
    }

    private Node createConsole() {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPrefHeight(HEIGHT - (2 * MARGIN) - (INPUT_HEIGHT + 2 * MARGIN));
        VBox.setMargin(textArea, new Insets(MARGIN));

        Logger logger = Logger.getLogger("");
        SimpleFormatter formatter = new SimpleFormatter();
        logger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                textArea.appendText(formatter.format(record));
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
            }
        });

        return textArea;
    }

    private TextField createInput(Application app) {
        AutocompleteTextField field = new AutocompleteTextField(
                app.getServiceManager().getServices()::keySet);

        VBox.setMargin(field, new Insets(MARGIN));

        field.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                app.getServiceManager().run(field.getText());
            }
        });

        return field;
    }

    private void addSlideHandlers(SimpleDoubleProperty pos, Node content, Application app) {
        Scene scene = app.getStage().getScene();

        Timeline slideIn = new Timeline(
                new KeyFrame(SLIDE_DURATION, new KeyValue(pos, HEIGHT))
        );

        Timeline slideOut = new Timeline(
                new KeyFrame(SLIDE_DURATION, new KeyValue(pos, 0))
        );

        slideIn.setOnFinished(e -> {
            input.requestFocus();
        });

        slideOut.setOnFinished(e -> {
            content.setVisible(false);
        });

        visible.addListener((ov, o, n) -> {
            if (o == n) return;

            if (n) {
                slideOut.stop();  // reset previous animation

                content.setVisible(true);
                slideIn.play();
            } else {
                slideIn.stop();  // reset previous animation

                slideOut.play();
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.F12) {
                if (visible.get()) {
                    app.getServiceManager().run(SERVICE_HIDE_CONSOLE);
                } else {
                    app.getServiceManager().run(SERVICE_SHOW_CONSOLE);
                }
            }
        });
    }

    @Service(SERVICE_HIDE_CONSOLE)
    public void hide(Application app) {
        visible.set(false);
    }

    @Service(SERVICE_SHOW_CONSOLE)
    public void show(Application app) {
        visible.set(true);
    }

}