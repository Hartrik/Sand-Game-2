package cz.hartrik.sg2.app.module.frame.module;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import javafx.geometry.Insets;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Patrik Harag
 * @version 2015-03-28
 */
public class TitleSubmodule extends MenuSubmodule<Frame, FrameController> {

    private final String title;
    
    public TitleSubmodule(String title) {
        super(false);
        this.title = title;
    }

    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {

        final Label lTitle = new Label(title);
        Font def = Font.getDefault();
        Font font = Font.font(def.getFamily(), FontWeight.BOLD, def.getSize());
        lTitle.setFont(font);
        
        final VBox box = new VBox(lTitle);
        box.setPadding(new Insets(0, 5, 0, 5));
        
        return new MenuItem[] { new CustomMenuItem(box, false) };
    }
    
}
