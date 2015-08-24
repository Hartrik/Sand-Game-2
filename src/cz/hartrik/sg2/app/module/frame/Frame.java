
package cz.hartrik.sg2.app.module.frame;

import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @version 2014-12-02
 * @author Patrik Harag
 */
public class Frame extends Stage {
    
    private final FrameController frameController;
    
    public Frame(StageModule<Frame, FrameController>[] modules) {
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
        
        ServiceManager serviceManager = frameController.getServiceManager();
        
        for (StageModule<Frame, FrameController> iStageModule : modules) {
            iStageModule.init(this, frameController, serviceManager);
        }
    }
    
    public FrameController getFrameController() {
        return frameController;
    }
    
}