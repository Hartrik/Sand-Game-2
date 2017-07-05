
package cz.hartrik.sg2.app.extension.io;

import cz.hartrik.sg2.brush.manage.BrushManager;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Spravuje seznam všech dostupných typů zdrojů.
 *
 * @version 2016-06-20
 * @author Patrik Harag
 */
public class ResourceTypeManager {

    private final ResourceType[] resourceTypes;

    public ResourceTypeManager(
            Supplier<BrushManager> bmSupplier, int defaultBrush) {

        this.resourceTypes = new ResourceType[] {
            new ResourceBrushTemplate(bmSupplier, defaultBrush),
            new ResourceEffectTemplate(bmSupplier, defaultBrush),
            new ResourceSerialized(),
            new ResourceHeatmap()
        };
    }

    /**
     * Vrátí všechny podporované typy zdrojů.
     *
     * @return kolekce typů zdrojů
     */
    public Collection<ResourceType> getAllSupported() {
        return Arrays.asList(resourceTypes);
    }

    /**
     * Vrátí typ zdroje podle identifikátoru.
     *
     * @param identifier identifikátor ({@link ResourceType#getIdentifier()})
     * @return ResourceType
     */
    public ResourceType findByIdentifier(String identifier) {
        return Arrays.stream(resourceTypes)
                .filter(rs -> rs.getIdentifier().equals(identifier))
                .findFirst()
                    .get();
    }

}