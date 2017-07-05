
package cz.hartrik.sg2.app.module.misc;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.FrameController;
import cz.hartrik.sg2.app.module.ApplicationModule;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.brush.Controls.Button;
import cz.hartrik.sg2.brush.jfx.JFXControls;
import cz.hartrik.sg2.brush.jfx.Thumbnailable;
import cz.hartrik.sg2.world.Element;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Modul přidávající náhledy na vybrané štětce.
 *
 * @version 2015-04-06
 * @author Patrik Harag
 */
public class ModuleBrushThumbnails implements ApplicationModule {

    private static final String CSS_CLASS = "border-light";

    private final int width;
    private final int height;

    public ModuleBrushThumbnails(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void init(Application app) {
        final FrameController controller = app.getController();
        final JFXControls controls = controller.getControls();

        final ImageView primaryBrushThumb = new ImageView();
        final ImageView secondaryBrushThumb = new ImageView();

        // nastevení rozměrů
        primaryBrushThumb.setFitWidth(width);
        primaryBrushThumb.setFitHeight(height);

        secondaryBrushThumb.setFitWidth(width);
        secondaryBrushThumb.setFitHeight(height);

        // zobrazení aktuálních štětců
        updateImage(primaryBrushThumb, controls.getPrimaryBrush());
        updateImage(secondaryBrushThumb, controls.getSecondaryBrush());

        // inicializace listenerů - aktualizace náhledů
        controls.primaryBrushProperty().addListener((ob, o, n)
                -> updateImage(primaryBrushThumb, n));

        controls.secondaryBrushProperty().addListener((ob, o, n)
                -> updateImage(secondaryBrushThumb, n));

        // inicializace listenerů - při kliknutí na náhled
        primaryBrushThumb.addEventHandler(MouseEvent.MOUSE_CLICKED,
                (event) -> onThumbClick(controller, event, Button.PRIMARY));

        secondaryBrushThumb.addEventHandler(MouseEvent.MOUSE_CLICKED,
                (event) -> onThumbClick(controller, event, Button.SECONDARY));

        // imageView musejí být kvůli rámečku vloženy do dalšího boxu
        HBox pBox = new HBox(primaryBrushThumb);
        HBox sBox = new HBox(secondaryBrushThumb);

        pBox.getStyleClass().add(CSS_CLASS);
        sBox.getStyleClass().add(CSS_CLASS);

        HBox box = new HBox(10, pBox, sBox);
        box.setAlignment(Pos.CENTER);
        controller.getLeftPanel().getChildren().add(box);
    }

    private void updateImage(ImageView imageView, Brush brush) {
        imageView.setImage((brush instanceof Thumbnailable)
                ? ((Thumbnailable) brush).getThumb(width, height) : null);
    }

    private void onThumbClick(FrameController controller,
            MouseEvent event, Button button) {

        final Controls controls = controller.getControls();

        // odstranění štětce při kliknutí primárním tlačítkem
        if (event.getButton() == MouseButton.PRIMARY) {
            Element background = controller.getWorld().getBackground();
            Brush remove = controller.getBrushManager().getProducer(background);

            controls.setBrush(button, remove);
            event.consume();

        // prohození štětců při kliknutí sekundárním tlačítkem
        } else if (event.getButton() == MouseButton.SECONDARY) {
            Brush primaryBrush = controls.getPrimaryBrush();
            Brush secondaryBrush = controls.getSecondaryBrush();
            controls.setPrimaryBrush(secondaryBrush);
            controls.setSecondaryBrush(primaryBrush);
        }
    }

}