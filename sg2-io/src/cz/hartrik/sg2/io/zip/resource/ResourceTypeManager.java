
package cz.hartrik.sg2.io.zip.resource;

import cz.hartrik.sg2.brush.manage.BrushManager;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Spravuje seznam všech dostupných typů zdrojů.
 *
 * @version 2017-07-29
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
     * Vrátí typ zdroje podle identifikátoru.
     *
     * @param identifier identifikátor ({@link ResourceType#getIdentifier()})
     * @return Optional
     */
    public Optional<ResourceType> findByIdentifier(String identifier) {
        String normalized = normalizeIdentifier(identifier);

        return Arrays.stream(resourceTypes)
                .filter(rs -> rs.getIdentifier().equals(normalized))
                .findFirst();
    }

    private String normalizeIdentifier(String identifier) {
        return identifier.toUpperCase().replace(' ', '_');
    }

}