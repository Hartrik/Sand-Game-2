
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.sg2.world.Element;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Rozhraní pro element, který může být produkován
 * {@link cz.hartrik.sg2.world.element.special.Source}.
 * 
 * @version 2014-05-07
 * @author Patrik Harag
 */
public interface Sourceable extends Element {
    
    /**
     * Odfiltruje z pole ostatní elementy a nechá pouze instance Sourceable.
     * 
     * @param elements elementy
     * @return filtrované pole (nové)
     */
    public static Sourceable[] filter(Element... elements) {
        return filter(Arrays.asList(elements));
    }
    
    /**
     * Odfiltruje z kolekce ostatní elementy a nechá pouze instance Sourceable.
     * 
     * @param elements elementy
     * @return filtrovaný seznam (nový)
     */
    public static Sourceable[] filter(Iterable<Element> elements) {
        List<Sourceable> filteredList = new ArrayList<>();
        for (Element element : elements)
            if (element instanceof Sourceable)
                filteredList.add((Sourceable) element);
        return filteredList.toArray(new Sourceable[0]);
    }
    
}
