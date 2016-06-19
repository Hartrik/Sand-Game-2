package cz.hartrik.sg2.app.module.io;

import cz.hartrik.sg2.world.ElementArea;
import java.util.*;
import java.util.function.BiFunction;

/**
 * Zprostředkuje ukládání a načítání plátna do souboru s příponou {@code sgb}.
 * Vnitřně se jedná o ZIP soubor. Elementy uloží podle jejich ID do obrázku,
 * podobně i teplotu. <p>
 *
 * Dokáže načíst všechny typy resources.
 *
 * @version 2016-06-19
 * @author Patrik Harag
 * @param <T>
 */
public class ZipIOBrushTemplate <T extends ElementArea> extends ZipIO<T> {

    public ZipIOBrushTemplate(BiFunction<Integer, Integer, T> areaSupplier,
            ResourceTypeManager resourceTypeManager) {

        super(areaSupplier, resourceTypeManager);
    }

    @Override
    public String getInfo() {
        return "Šablona";
    }

    @Override
    public String getExtension() {
        return "sgb";
    }

    @Override
    public Map<String, ResourceType> getWriteResourceTypes() {
        Map<String, ResourceType> map = new LinkedHashMap<>();

        String id1 = ResourceBrushTemplate.IDENTIFIER;
        map.put("template.png", resourceTypeManager.findByIdentifier(id1));

        String id2 = ResourceHeatmap.IDENTIFIER;
        map.put("heatmap.png", resourceTypeManager.findByIdentifier(id2));
        
        return map;
    }

}