
package cz.hartrik.sg2.brush.manage;

import cz.hartrik.common.Checker;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.Metamorphic;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * <p> Správce štětců - spravuje štětce ve slovníku na základě jejich 
 * <code>id</code>. </p>
 * 
 * <p>
 * <b>Skryté štětce:</b> <br>
 * Při přidávání štětce do správce štětců je možné nastavit štětec jako skrytý.
 * Skryté štětce nejsou vraceny např. metodami <code>getProducer</code> nebo
 * <code>getBrushes</code>, k tomu slouží jejich obdoby
 * <code>getProducerAll</code> a <code>getBrushesAll</code>. Díky tomu je možné
 * štětce, které nejsou nastaveny jako skryté zobrazovat uživateli a ty skryté
 * používat pro vnitřní potřebu (např. k duplikaci elementů).
 * </p>
 * 
 * @version 2015-01-10
 * @author Patrik Harag
 * @param <T> typ štětce
 */
public class BrushManager<T extends Brush> {

    protected final Map<Integer, BrushItem<T>> map = new HashMap<>();
    
    // add
    
    public void addBrush(T brush) {
        addBrush(brush, false);
    }
    
    public void addBrush(T brush, boolean hidden) {
        addBrushItem(new BrushItem<>(brush, hidden));
    }
    
    public void addBrushItem(BrushItem<T> brushItem) {
        T brush        = Checker.requireNonNull(brushItem).getBrush();
        BrushInfo info = Checker.requireNonNull(brush).getInfo();
        int id         = Checker.requireNonNull(info).getId();
        
        if (map.containsKey(id))
            throw new RuntimeException("There is another brush with id=" + id);
        
        map.put(id, brushItem);
    }
    
    public void addBrushes(Collection<T> brushes) {
        addBrushes(brushes, false);
    }
    
    public void addBrushes(Collection<T> brushes, boolean hidden) {
        for (T brush : brushes) addBrush(brush, hidden);
    }
    
    public void addBrushItems(Collection<BrushItem<T>> brushes) {
        for (BrushItem<T> brushItem : brushes) addBrushItem(brushItem);
    }
    
    // get
    
    public T getBrush(int id) {
        BrushItem<T> item = map.get(id);
        return (item == null) ? null : item.getBrush();
    }
    
    public T getBrush(String name) {
        return getBrushesAll().stream()
                .filter(b -> b.getInfo().getName().equalsIgnoreCase(name))
                .findFirst().orElseThrow(() -> new NoSuchElementException(name));
    }
    
    public List<T> getProducers(Element element) {
        final Element normalized = normalize(element);
        return getBrushes((brush, hidden)
                -> !hidden && brush.produces(normalized));
    }

    public List<T> getProducersAll(Element element) {
        final Element normalized = normalize(element);
        return getBrushes((brush, hidden) -> brush.produces(normalized));
    }
    
    public T getProducer(Element element) { // důležitá rychlost
        final Element normalized = normalize(element);
        for (BrushItem<T> brushItem : map.values()) {
            if (brushItem.isHidden()) continue;
            T brush = brushItem.getBrush();
            if (brush.produces(normalized)) return brush;
        }
        return null;
    }
    
    public T getProducerAll(Element element) { // důležitá rychlost
        final Element normalized = normalize(element);
        for (BrushItem<T> brushItem : map.values()) {
            T brush = brushItem.getBrush();
            if (brush.produces(normalized)) return brush;
        }
        return null;
    }
    
    protected Element normalize(Element element) {
        return (element instanceof Metamorphic)
                ? ((Metamorphic) element).getBasicElement()
                : element;
    }
    
    public List<T> getBrushes() {
        final List<T> brushes = getBrushes((brush, hiden) -> !hiden);
        Collections.sort(brushes);
        return brushes;
    }
    
    public List<T> getBrushesAll() {
        List<T> list = new ArrayList<>();
        for (BrushItem<T> brushItem : map.values())
            list.add(brushItem.getBrush());
        
        Collections.sort(list);
        return list;
    }
    
    public List<T> getBrushes(BrushPredicate<T> filter) {
        List<T> list = new ArrayList<>();
        
        for (BrushItem<T> brushItem : map.values())
            if (filter.accept(brushItem.getBrush(), brushItem.isHidden()))
                list.add(brushItem.getBrush());
        
        return list;
    }
    
    // další zjišťovací metody
    
    public boolean contains(int id) {
        return map.containsKey(id);
    }
    
    public boolean contains(T brush) {
        return contains(brush.getInfo().getId());
    }
    
    public boolean isHidden(int id) {
        BrushItem<T> brushItem = map.get(id);
        return brushItem == null ? false : brushItem.isHidden();
    }
    
    public boolean isHidden(T brush) {
        return isHidden(brush.getInfo().getId());
    }
    
    // hidden
    
    public boolean setHidden(int id, boolean hidden) {
        BrushItem<T> item = map.get(id);
        if (item != null && item.isHidden() != hidden ) {
            item.setHidden(hidden);
            return true;
        }
        return false;
    }
    
}