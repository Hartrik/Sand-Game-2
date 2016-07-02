package cz.hartrik.sg2.app.module.io;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.EffectBrush;
import cz.hartrik.sg2.brush.Wrapper;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * ID štětců uložená v png obrázku, ukládají se pouze efektové štětce
 * ({@link EffectBrush}). <p>
 *
 * Pro správné fungování musí být tato šablona načtena až po načtení
 * standardní šablony (efektové štětce musí být aplikovány přes ni).
 *
 * @version 2016-06-20
 * @author Patrik Harag
 */
public class ResourceEffectTemplate extends ResourceBrushTemplate {

    public static final String IDENTIFIER = "effect template";

    public ResourceEffectTemplate(
            Supplier<BrushManager> supplier, int defID) {

        super(supplier, defID);
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    private List<Brush> cache;

    @Override
    protected BufferedImage createImage(ElementArea area, BrushManager bm) {
        cache = bm.getBrushesAll().stream()
                .filter(b -> Wrapper.isInstance(b, EffectBrush.class))
                .collect(Collectors.toList());

        BufferedImage image = super.createImage(area, bm);

        cache.clear();

        return image;
    }

    @Override
    protected int getID(Element element, BrushManager brushManager, int def) {
        int id = cache.stream()
                .filter(b -> b.produces(element))
                .findFirst()
                    .map(b -> b.getInfo().getId())
                    .orElse(def);

        return id;
    }

}