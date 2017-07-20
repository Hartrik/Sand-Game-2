
package cz.hartrik.sg2.io.zip;

import cz.hartrik.sg2.io.AppInfo;
import cz.hartrik.sg2.io.ElementAreaProvider;
import cz.hartrik.sg2.io.zip.resource.ResourceSerialized;
import cz.hartrik.sg2.io.zip.resource.ResourceType;
import cz.hartrik.sg2.io.zip.resource.ResourceTypeManager;
import cz.hartrik.sg2.world.ElementArea;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Zprostředkuje ukládání a načítání plátna do souboru s příponou {@code sgs}.
 * Vnitřně se jedná o ZIP soubor. Celé plátno serializuje. <p>
 *
 * Dokáže načíst všechny typy resources.
 *
 * @version 2017-07-19
 * @author Patrik Harag
 * @param <T> vstup/výstup
 */
public class ZipIOSerial<T extends ElementArea> extends ZipIO<T> {

    public static final String FILE_DATA = "data.ser";

    public ZipIOSerial(AppInfo appInfo, ElementAreaProvider<T> areaProvider,
                       ResourceTypeManager resourceTypeManager) {

        super(appInfo, areaProvider, resourceTypeManager);
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