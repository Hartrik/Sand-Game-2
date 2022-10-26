
package cz.hartrik.sg2.app.module.options;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.FrameController;
import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.engine.JFXEngine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

/**
 * Sub-modul do menu, který přidává slider(y) s nastavením maximálního počtu
 * FPS a cyklů za sekundu.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
public class EngineSpeedSubmodule implements MenuSubmodule {

    public enum Settings { RENDERER, PROCESSOR, BOTH }

    private static final int DEFAULT_FPS = 55;
    private static final int DEFAULT_C   = 200;

    private final Settings settings;

    public EngineSpeedSubmodule(Settings settings) {
        this.settings = settings;
    }

    @Override
    public MenuItem[] createMenuItems(Application app) {
        FrameController controller = app.getController();

        Slider sMaxFPS = null;
        Slider sMaxC = null;
        final VBox box = new VBox(10);

        if (settings == Settings.RENDERER || settings == Settings.BOTH) {
            Label lMaxFPS = new Label(Strings.get("module.options.max-fps"));
            lMaxFPS.setPrefWidth(150);

            sMaxFPS = new Slider(5, 100, 55);
            sMaxFPS.setBlockIncrement(1);
            sMaxFPS.setPrefWidth(300);
            sMaxFPS.setMajorTickUnit(10);
            sMaxFPS.setMinorTickCount(5);
            sMaxFPS.setShowTickLabels(true);
            sMaxFPS.setShowTickMarks(true);
            sMaxFPS.setValue(DEFAULT_FPS);

            box.getChildren().add(new HBox(lMaxFPS, sMaxFPS));

            sMaxFPS.valueProperty().addListener((ov, o, n)
                    -> controller.getEngine().setMaxFPS(n.intValue()));
        }

        if (settings == Settings.PROCESSOR || settings == Settings.BOTH) {
            Label lMaxC = new Label(Strings.get("module.options.max-cycles"));
            lMaxC.setPrefWidth(150);

            sMaxC = new Slider(5, 1000, 200);
            sMaxC.setBlockIncrement(10);
            sMaxC.setPrefWidth(300);
            sMaxC.setMajorTickUnit(100);
            sMaxC.setMinorTickCount(5);
            sMaxC.setShowTickLabels(true);
            sMaxC.setShowTickMarks(true);
            sMaxC.setValue(DEFAULT_C);

            box.getChildren().add(new HBox(lMaxC, sMaxC));

            sMaxC.valueProperty().addListener((ov, o, n)
                -> controller.getEngine().setMaxCycles(n.intValue()));
        }

        Label lInfo = new Label(Strings.get("module.options.max-info"));
        lInfo.setFont(Font.font(null, FontPosture.ITALIC, 10)); // default fam.
        lInfo.setPadding(new Insets(-10, 0, 0, 0));

        final HBox bInfo = new HBox(lInfo);
        bInfo.setAlignment(Pos.TOP_RIGHT);

        box.getChildren().add(bInfo);
        box.setPadding(new Insets(0, 10, 0, 10));

        final Slider finalMaxFPS = (sMaxFPS == null) ? null : sMaxFPS;
        final Slider finalsMaxC  = (sMaxC   == null) ? null : sMaxC;

        Runnable engineSpeedUpdate = () -> {
            JFXEngine<?> engine = controller.getEngine();
            if (finalMaxFPS != null)
                engine.setMaxFPS((int) finalMaxFPS.getValue());
            if (finalsMaxC != null)
                engine.setMaxCycles((int) finalsMaxC.getValue());
        };

        controller.addOnSetUp(engineSpeedUpdate);

        return new MenuItem[] { new CustomMenuItem(box, false) };
    }

}