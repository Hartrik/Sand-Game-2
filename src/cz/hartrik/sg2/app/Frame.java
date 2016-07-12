
package cz.hartrik.sg2.app;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class Frame extends Stage {

    private final FrameController frameController;

    public Frame() {
        URL location = Frame.class.getResource("Frame.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root;
        try {
            root = (Parent) fxmlLoader.load(location.openStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        this.frameController = (FrameController) fxmlLoader.getController();

        setScene(new Scene(root, 1100, 650));
        setOnCloseRequest((e) -> System.exit(0));
    }

    public FrameController getFrameController() {
        return frameController;
    }

}