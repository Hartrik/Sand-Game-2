
package cz.hartrik.sg2.brush.manage;

import cz.hartrik.common.Checker;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.Metamorphic;
import java.util.*;

/**
 * <p> Správce štětců - spravuje štětce ve slovníku na základě jejich
 * <code>id</code>. </p>
 *
 * <p> Štětec s id=0 by měl být "nulový" štětec - nenanáší žádné elementy,
 * vrací vždy {@code null}. </p>
 *
 * <p>
 * <b>Skryté štětce:</b> <br>
 * Při přidávání štětce do správce štětců je možné nastavit štětec jako skrytý.
 * Skryté štětce nejsou vraceny např. metodami <code>getProducer</code> nebo
 * <code>getBrushes</code>, k tomu slouží jejich obdoby
 * <code>getProducerAll</code> a <code>getBrushesAll</code>. Díky tomu je možné
 * štětce, které nejsou nastaveny jako skryté zobrazovat uživateli a ty skryté
 * používat pro vnitřní potřebu (např. k duplikaci elementů, ukládání...).
 * </p>
 *
 * @version 2016-06-20
 * @author Patrik Harag
 */
public class BrushManager {

    protected final Map<Integer, BrushItem> map = new HashMap<>();

    // add

    public void addBrush(Brush brush) {
        addBrush(brush, false);
    }

    public void addBrush(Brush brush, boolean hidden) {
        addBrushItem(new BrushItem(brush, hidden));
    }

    public void addBrushItem(BrushItem brushItem) {
        Brush brush    = Checker.requireNonNull(brushItem).getBrush();
        BrushInfo info = Checker.requireNonNull(brush).getInfo();
        int id         = Checker.requireNonNull(info).getId();

        if (map.containsKey(id))
            throw new RuntimeException("There is another brush with id=" + id);

        map.put(id, brushItem);
    }

    public void addBrushes(Collection<Brush> brushes) {
        addBrushes(brushes, false);
    }

    public void addBrushes(Collection<Brush> brushes, boolean hidden) {
        for (Brush brush : brushes) addBrush(brush, hidden);
    }

    public void addBrushItems(Collection<BrushItem> brushes) {
        for (BrushItem brushItem : brushes) addBrushItem(brushItem);
    }

    // get

    public Brush getBrush(int id) {
        BrushItem item = map.get(id);
        return (item == null) ? null : item.getBrush();
    }

    public Brush getBrush(String name) {
        return getBrushesAll().stream()
                .filter(b -> b.getInfo().getName().equalsIgnoreCase(name))
                .findFirst().orElseThrow(() -> new NoSuchElementException(name));
    }

    public List<Brush> getProducers(Element element) {
        final Element normalized = normalize(element);
        return getBrushes((brush, hidden)
                -> !hidden && brush.produces(normalized));
    }

    public List<Brush> getProducersAll(Element element) {
        final Element normalized = normalize(element);
        return getBrushes((brush, hidden) -> brush.produces(normalized));
    }

    public Brush getProducer(Element element) { // důležitá rychlost
        final Element normalized = normalize(element);
        for (BrushItem brushItem : map.values()) {
            if (brushItem.isHidden()) continue;
            Brush brush = brushItem.getBrush();
            if (brush.produces(normalized)) return brush;
        }
        return null;
    }

    public Brush getProducerAll(Element element) { // důležitá rychlost
        final Element normalized = normalize(element);
        for (BrushItem brushItem : map.values()) {
            Brush brush = brushItem.getBrush();
            if (brush.produces(normalized)) return brush;
        }
        return null;
    }

    protected Element normalize(Element element) {
        return (element instanceof Metamorphic)
                ? ((Metamorphic) element).getBasicElement()
                : element;
    }

    public List<Brush> getBrushes() {
        final List<Brush> brushes = getBrushes((brush, hiden) -> !hiden);
        Collections.sort(brushes);
        return brushes;
    }

    public List<Brush> getBrushesAll() {
        List<Brush> list = new ArrayList<>();
        for (BrushItem brushItem : map.values())
            list.add(brushItem.getBrush());

        Collections.sort(list);
        return list;
    }

    public List<Brush> getBrushes(BrushPredicate<Brush> filter) {
        List<Brush> list = new ArrayList<>();

        for (BrushItem brushItem : map.values())
            if (filter.accept(brushItem.getBrush(), brushItem.isHidden()))
                list.add(brushItem.getBrush());

        return list;
    }

    // další zjišťovací metody

    public boolean contains(int id) {
        return map.containsKey(id);
    }

    public boolean contains(Brush brush) {
        return contains(brush.getInfo().getId());
    }

    public boolean isHidden(int id) {
        BrushItem brushItem = map.get(id);
        return brushItem == null ? false : brushItem.isHidden();
    }

    public boolean isHidden(Brush brush) {
        return isHidden(brush.getInfo().getId());
    }

    // hidden

    public boolean setHidden(int id, boolean hidden) {
        BrushItem item = map.get(id);
        if (item != null && item.isHidden() != hidden ) {
            item.setHidden(hidden);
            return true;
        }
        return false;
    }

}