package cz.hartrik.sg2.app.sandbox;

import cz.hartrik.sg2.engine.JFXPlatform;
import cz.hartrik.sg2.engine.Platform;
import javafx.application.Application;

/**
 * @author Patrik Harag
 * @version 2022-10-26
 */
public class MainClass {

    public static void main(String[] args) {
        Platform.init(new JFXPlatform());
        Application.launch(Main.class, args);
    }
}
