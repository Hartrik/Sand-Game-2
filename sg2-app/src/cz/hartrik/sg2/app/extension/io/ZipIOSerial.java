
package cz.hartrik.sg2.app.extension.io;

import cz.hartrik.sg2.world.ElementArea;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Zprostředkuje ukládání a načítání plátna do souboru s příponou {@code sgs}.
 * Vnitřně se jedná o ZIP soubor. Celé plátno serializuje. <p>
 *
 * Dokáže načíst všechny typy resources.
 *
 * @version 2016-06-21
 * @author Patrik Harag
 * @param <T> vstup/výstup
 */
public class ZipIOSerial<T extends ElementArea> extends ZipIO<T> {

    public static final String FILE_DATA = "data.ser";

    public ZipIOSerial(ElementAreaProvider<T> areaProvider,
            ResourceTypeManager resourceTypeManager) {

        super(areaProvider, resourceTypeManager);
    }

    @Override
    public String getInfo() {
        return "Serializovaná data";
    }

    @Override
    public String getExtension() {
        return "sgs";
    }

    @Override
    public Map<String, ResourceType> getWriteResourceTypes() {
        Map<String, ResourceType> map = new LinkedHashMap<>();

        map.put(FILE_DATA,
                resourceTypeManager.findByIdentifier(ResourceSerialized.IDENTIFIER));

        return map;
    }

}