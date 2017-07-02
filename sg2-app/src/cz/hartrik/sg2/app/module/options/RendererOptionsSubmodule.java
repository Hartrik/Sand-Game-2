package cz.hartrik.sg2.app.module.options;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.FrameController;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.engine.render.*;
import cz.hartrik.sg2.world.ChunkedArea;
import java.util.function.Function;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * Modul pro nastavení vykreslování.
 *
 * @version 2016-07-03
 * @author Patrik Harag
 */
public class RendererOptionsSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {
        FrameController controller = app.getController();

        CheckMenuItem itemBlur = new CheckMenuItem("Motion blur");
        itemBlur.setAccelerator(KeyCombination.valueOf("f5"));

        CheckMenuItem itemHC = new CheckMenuItem("Zvýraznit aktivní chunky");
        itemHC.setAccelerator(KeyCombination.valueOf("f6"));

        CheckMenuItem itemHeatmap = new CheckMenuItem("Heatmap");
        itemHeatmap.setAccelerator(KeyCombination.valueOf("f7"));

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