
package cz.hartrik.sg2.brush;

import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;

/**
 * Rozhraní štětce, prostřednictvím kterého se na plátno vkládají elementy.
 * Může fungovat i tak, že pouze ovlivní stávající elementy.
 * 
 * <p>
 * Některé štětce ke svém plné funkčnosti potřebují další parametry - pozici,
 * pole elementů nebo nastavení. Takové štětce vrací metodou
 * {@link #isAdvanced() isAdvanced()} <code>true</code>.
 * 
 * <p>
 * Je zároveň funkčním rozhraním - <b>jako lambda výraz ho je ale vhodné
 * používat jen pro testování nebo scriptování</b>.
 * 
 * @version 2015-01-09
 * @author Patrik Harag
 */
@FunctionalInterface
public interface Brush extends Comparable<Brush> {
    
    public static final Brush EMPTY_BRUSH = new Brush() {
        @Override
        public BrushInfo getInfo() {
            return new BrushInfo(0, "Empty brush");
        }

        @Override
        public Element getElement(Element current) {
            return null;
        }

        @Override
        public boolean isProducer(Element element) {
            return false;
        }
    };
    
    /**
     * Vrátí popis štětce.
     * <p>
     * Ve výchozí implementaci id odpovídá
     * {@link System#identityHashCode(Object)} a název je <i>Anonymous brush</i>.
     * 
     * @return popis
     */
    public default BrushInfo getInfo() {
        return new BrushInfo(System.identityHashCode(this), "Anonymous brush");
    }
    
    /**
     * Volá metodu {@link #getElement(Element) getElement(current)} s hodnotou
     * <code>null</code>.
     * 
     * @return element
     */
    public default Element getElement() {
        return getElement(null);
    }
    
    /**
     * Vrátí element. Může to být i <code>null</code> - pokud nemá dojít k
     * překreslení současného elementu nebo je štětec
     * {@link #isAdvanced() isAdvanced()} a ke správnému fungování vyžaduje
     * další parametry.
     * 
     * @param current současný element na pozici, může být <code>null</code>
     * @return element
     */
    public Element getElement(Element current);
    
    /**
     * Vrací elementy na základě nějaké složitější logiky. Parametry mohou být
     * <code>null</code>, a stejně tak návratová hodnota. Souvisí s metodou
     * {@link #isAdvanced() isAdvanced()}.
     * <p>
     * Ve výchozí implementaci volá metodu
     * {@link #getElement(Element) getElement(Element)}.
     * 
     * @param current současný element na pozici
     * @param x budoucí horizontální pozice elementu,
     *          pokud není známa, tak <code>-1</code>
     * @param y budoucí vertikální pozice elementu,
     *          pokud není známa, tak <code>-1</code>
     * @param area pole elementů
     * @param controls nastavení myši
     * @return element
     */
    public default Element getElement(Element current, int x, int y,
            ElementArea area, Controls controls) {
        
        return getElement(current);
    }
    
    /**
     * Značí, zda štětec využívá přetíženou metodu <code>getElement(...)</code>
     * s dodatečnými parametry.
     * <p>
     * Ve výchozí implementaci varací <code>false</code>.
     * 
     * @return <code>boolean</code>
     */
    public default boolean isAdvanced() {
        return false;
    }
    
    /**
     * Slouží k identifikaci štětce, který produkuje určitý element.
     * <p>
     * Ve výchozí implementaci varací <code>false</code>.
     * 
     * @param element element k otestování
     * @return <code>boolean</code> - produkuje tento element
     */
    public default boolean isProducer(Element element) {
        return false;
    }

    @Override
    public default int compareTo(Brush o) {
        return getInfo().getId() < o.getInfo().getId() ? -1 :
                getInfo().getId() == o.getInfo().getId() ? 0 : 1;
    }
    
}