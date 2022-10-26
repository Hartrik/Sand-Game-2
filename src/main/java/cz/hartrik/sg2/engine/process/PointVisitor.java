
package cz.hartrik.sg2.engine.process;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;

/**
 * Objekt obsahuje metody pro procházení bodů v poli. Body jsou procházeny vždy
 * popořadě. U každého bodu se kontroluje, zda pozice existuje, pokud ne, je
 * přeskočen.
 * <p>
 * Existují 2 druhy metod - <code>visit…</code> a <code>visitWhile…</code>.
 * <br> Metody "visit" pouze projdou všechny body.
 * <br> Metody "visitWhile" prochází body dokud metoda <code>apply…</code>
 * vrací <code>true</code>. Pozor! metoda apply vrací true i pokud bod v poli
 * neexistuje. Metody "visitWhile" poté vrací boolean podle toho, zda bylo
 * procházení zastaveno metodou <code>apply…</code>.
 * 
 * @version 2014-03-30
 * @author Patrik Harag
 */
public class PointVisitor {
    
    private final ElementArea area;

    public PointVisitor(ElementArea area) {
        this.area = area;
    }
    
    // ------------------------- ALL -------------------------
    
    // ALL - ? - xy
    
    @FunctionalInterface
    public static interface VisitorA1 {
        public void apply(Element element, int eX, int eY);
    }
    
    // ALL - xy - xy
    
    public final void visit(VisitorA1 function, int... xy) {
        if (xy.length % 2 != 0)
            throw new IllegalArgumentException("Odd number of arguments");
        
        for (int i = 0; i < xy.length; i++) visit(function, xy[i], xy[++i]);
    }
    
    public final void visit(VisitorA1 function, int x, int y) {
        if (area.valid(x, y))
            function.apply(area.get(x, y), x, y);
    }
    
    // ALL - Point - xy
    
    public final void visit(VisitorA1 function, Point... points) {
        for (Point point : points)
            visit(function, point.getX(), point.getY());
    }
    
    public final void visit(VisitorA1 function, Point point) {
        visit(function, point.getX(), point.getY());
    }
    
    // ALL - Point - Point
    
    @FunctionalInterface
    public static interface VisitorA2 {
        public void apply(Element element, Point point);
    }
    
    public final void visit(VisitorA2 function, Point... points) {
        for (Point point : points)
            visit(function, point);
    }
    
    public final void visit(VisitorA2 function, Point point) {
        if (area.valid(point.getX(), point.getY()))
            function.apply(area.get(point.getX(), point.getY()), point);
    }
    
    // ------------------------- WHILE -------------------------
    
    // WHILE - ? - xy
    
    @FunctionalInterface
    public static interface VisitorB1 {
        public boolean apply(Element element, int eX, int eY);
    }
    
    // WHILE - xy - xy
    
    public final boolean visitWhile(VisitorB1 function, int... xy) {
        if (xy.length % 2 != 0)
            throw new IllegalArgumentException("Odd number of arguments");
        
        for (int i = 0; i < xy.length; i++)
            if (!visitWhile(function, xy[i], xy[++i])) return false;
        
        return true;
    }
    
    public final boolean visitWhile(VisitorB1 function, int x, int y){
        return area.valid(x, y)
                ? function.apply(area.get(x, y), x, y)
                : true;
    }
    
    // WHILE - Point - xy
    
    public final boolean visitWhile(VisitorB1 function, Point... points) {
        for (Point point : points)
            if (!visitWhile(function, point.getX(), point.getY())) return false;
        
        return true;
    }
    
    public final boolean visitWhile(VisitorB1 function, Point point) {
        return visitWhile(function, point.getX(), point.getY());
    }
    
    // WHILE - Point - Point
    
    @FunctionalInterface
    public static interface VisitorB2 {
        public boolean apply(Element element, Point point);
    }
    
    public final boolean visitWhile(VisitorB2 function, Point... points) {
        for (Point point : points)
            if (!visitWhile(function, point)) return false;
        
        return true;
    }
    
    public final boolean visitWhile(VisitorB2 function, Point point){
        return area.valid(point.getX(), point.getY())
                ? function.apply(area.get(point.getX(), point.getY()), point)
                : true;
    }
    
}