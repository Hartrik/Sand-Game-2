
package cz.hartrik.sg2.app.module.misc;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.brush.build.Thumbnailable;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.engine.JFXPlatform;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Jedna buňka v {@link javafx.scene.control.ListView}.
 *
 * @version 2017-07-05
 * @author Patrik Harag
 */
public class BrushCell extends ListCell<Brush> {

    public static final int ICON_SIZE = 16;
    private final Controls controls;

    public BrushCell(Controls current) {
        this.controls = current;
    }

    @Override
    public void updateItem(Brush brush, boolean empty) {
        super.updateItem(brush, empty);
        if (empty) {
            setGraphic(new Pane());
        } else {
            setGraphic(createContent(brush));
            initListener(brush);
        }
    }

    private Node createContent(Brush item) {
        final HBox imageBox = new HBox();
        imageBox.setFillHeight(true);
        imageBox.getStyleClass().add("border-light");

        Image image = null;
        if (item instanceof Thumbnailable) {
            Thumbnailable temp = ((Thumbnailable) item);
            image = JFXPlatform.asJFXImage(temp.getThumb(ICON_SIZE, ICON_SIZE));
        }
        
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(ICON_SIZE);
        imageView.setFitWidth(ICON_SIZE);
        imageBox.getChildren().add(imageView);

        // jméno
        final Label name = new Label(item.getInfo().getName());
        name.getStyleClass().add("label-brush-name");

        // složení
        final HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);
        box.setSpacing(10);
        box.getChildren().addAll(imageBox, name);
        
        installTooltip(box, item.getInfo());
        
        return box;
    }

    private void installTooltip(Node node, BrushInfo info) {
        String tooltip = "id: " + info.getId();

        // štítky
        final Set<String> labels = info.getLabels();
        if (!labels.isEmpty())
            tooltip += "\n"
                    + labels.stream().collect(Collectors.joining(", ", "[", "]"));

        // popis
        final String description = info.getDescription();
        if (!description.isEmpty())
            tooltip += "\n\n" + description;

        Tooltip.install(node, new Tooltip(tooltip));
    }
    
    private void initListener(final Brush brush) {
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            if (brush == null) return;
            if (event.getButton() == MouseButton.PRIMARY) {
                if (controls.getPrimaryBrush() != brush)
                    controls.setPrimaryBrush(brush);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                if (controls.getSecondaryBrush() != brush)
                    controls.setSecondaryBrush(brush);
            }
        });
    }

}