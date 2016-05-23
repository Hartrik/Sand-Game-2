package cz.hartrik.sg2.app.module.frame.module.options;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.engine.*;
import cz.hartrik.sg2.world.ChunkedArea;
import java.util.function.Function;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;

/**
 * Modul pro nastavení vykreslování.
 *
 * @version 2016-05-23
 * @author Patrik Harag
 */
public class RendererOptionsSubmodule extends MenuSubmodule<Frame, FrameController> {

    public RendererOptionsSubmodule() {
        super(true);
    }

    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {

        CheckMenuItem itemBlur = new CheckMenuItem("Motion blur");
        CheckMenuItem itemHC = new CheckMenuItem("Zvýraznit aktivní chunky");
        CheckMenuItem itemHeatmap = new CheckMenuItem("Heatmap");

        itemBlur.setSelected(true);

        ChangeListener<Boolean> listener = (ov, o, n) -> {
            controller.setRendererSupplier(selectRenderer(
                    itemBlur.isSelected(),
                    itemHC.isSelected(),
                    itemHeatmap.isSelected()));
            controller.setUpCanvas(controller.getWorld());
        };

        itemBlur.selectedProperty().addListener(listener);
        itemHC.selectedProperty().addListener(listener);
        itemHeatmap.selectedProperty().addListener(listener);

        return new MenuItem[] { itemBlur, itemHC, itemHeatmap };
    }

    private Function<ChunkedArea, JFXRenderer> selectRenderer(
            boolean blur, boolean highlight, boolean heatMap) {

        if (heatMap) {
            Function<ChunkedArea, JFXRenderer> select =
                    selectRendererFactory(blur, highlight);

            return (area) -> {
                JFXRenderer renderer = select.apply(area);
                renderer.setColorPalette(ColorPalettes.HEATMAP);
                return renderer;
            };
        }

        return selectRendererFactory(blur, highlight);
    }

    private Function<ChunkedArea, JFXRenderer> selectRendererFactory(
            boolean blur, boolean highlight) {

        if (blur)
            return (highlight) ? JFXRendererBlurCV::new : JFXRendererBlur::new;
        else
            return (highlight) ? JFXRendererCV::new : JFXRenderer::new;
    }

}