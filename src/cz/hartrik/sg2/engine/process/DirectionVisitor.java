
package cz.hartrik.sg2.engine.process;

import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;

/**
 * Nástroje, které vykonávají určité akce podle zadaného směru z určitého bodu.
 * 
 * @see Direction
 * @see PointVisitor
 * @version 2014-05-15
 * @author Patrik Harag
 */
public class DirectionVisitor {
    
    private final ElementArea area;

    public DirectionVisitor(ElementArea area) {
        this.area = area;
    }
    
    // ------------------ TEST --------------------------------------
    
    @FunctionalInterface
    public static interface TestFunction {
        public boolean apply(Element element);
    }
    
    /**
     * Otestuje po směru hodinových ručiček všechny <b>hlavní</b>
     * ({@link Direction#isMain() isMain()}) směry.
     * Pokud jeden z nich vrátí <code>true</code>, tak se následující netestují.
     * 
     * @see #test(int, int, TestFunction, Direction)
     * @param x výchozí horizontální pozice
     * @param y výchozí vertikální pozice
     * @param function testovací funkce
     * @return <code>true</code>, pokud alespoň jeden test vrátí true, jinak
     *         <code>false</code>
     */
    public final boolean testAll(final int x, final int y,
            TestFunction function) {
        
        return test(x, y, function, Direction.UP)
            || test(x, y, function, Direction.RIGHT)
            || test(x, y, function, Direction.DOWN)
            || test(x, y, function, Direction.LEFT);
    }
    
    /**
     * Otestuje popořadě všechny směry. Pokud jeden z nich vrátí
     * <code>true</code>, tak se následující netestují.
     * 
     * @see #test(int, int, TestFunction, Direction)
     * @param x výchozí horizontální pozice
     * @param y výchozí vertikální pozice
     * @param function testovací funkce
     * @param directions směry od výchozí pozice
     * @return <code>true</code>, pokud alespoň jeden test vrátí true, jinak
     *         <code>false</code>
     */
    public final boolean test(final int x, final int y, TestFunction function,
            Direction... directions) {
        
        for (Direction dir : directions)
            if (test(x, y, function, dir)) return true;
        return false;
    }
    
    /**
     * Otestuje bod podle směru od výchozí pozice.
     * 
     * @param x výchozí horizontální pozice
     * @param y výchozí vertikální pozice
     * @param function testovací funkce
     * @param direction směr od výchozí pozice
     * @return <code>true</code>, pokud alespoň jeden test vrátí true, jinak
     *         <code>false</code>
     */
    public final boolean test(final int x, final int y, TestFunction function,
            Direction direction) {
        
        final int tx = x + direction.getX();
        final int ty = y + direction.getY();
        
        return area.valid(tx, ty) ? function.apply(area.get(tx, ty)) : false;
    }
    
    // ------------------ VISITOR --------------------------------------
    
    // ------------------- All ------------------- 
    
    @FunctionalInterface
    public static interface VisitorA1 {
        public void apply(Element element, int eX, int eY);
    }
    
    @FunctionalInterface
    public static interface VisitorA2 {
        public void apply(Element element);
    }
    
    /**
     * Aplikuje funkci po směru hodinových ručiček všechny <b>hlavní</b>
     * ({@link Direction#isMain() isMain()}) směry.
     * 
     * @see #visit(int, int, VisitorA1, Direction) 
     * @param x výchozí horizontální pozice
     * @param y výchozí vertikální pozice
     * @param function funkce
     */
    public final void visitAll(final int x, final int y, VisitorA1 function) {
        visit(x, y, function, Direction.UP);
        visit(x, y, function, Direction.RIGHT);
        visit(x, y, function, Direction.DOWN);
        visit(x, y, function, Direction.LEFT);
    }
    
    /**
     * @see #visitAll(int, int, VisitorA1)
     */
    public final void visitAll(final int x, final int y, VisitorA2 function) {
        visit(x, y, function, Direction.UP);
        visit(x, y, function, Direction.RIGHT);
        visit(x, y, function, Direction.DOWN);
        visit(x, y, function, Direction.LEFT);
    }
    
    /**
     * Postupně aplikuje funkci na všechny požadované strany.
     * 
     * @see #visit(int, int, VisitorA1, Direction) 
     * @param x výchozí horizontální pozice
     * @param y výchozí vertikální pozice
     * @param function funkce
     * @param directions směry od výchozí pozice
     */
    public final void visit(int x, int y, VisitorA1 function,
            Direction... directions) {
        
        for (Direction dir : directions) visit(x, y, function, dir);
    }
    
    /** 
     * @see #visit(int, int, VisitorA1, Direction...)
     */
    public final void visit(int x, int y, VisitorA2 function,
            Direction... directions) {
        
        for (Direction dir : directions) visit(x, y, function, dir);
    }
    
    /**
     * Aplikuje funkci na bod ležící určitým směrem od výchozí pozice.
     * Tedy pokud taková pozice existuje.
     * 
     * @param x výchozí horizontální pozice
     * @param y výchozí vertikální pozice
     * @param function funkce
     * @param direction směry od výchozí pozice
     */
    public final void visit(int x, int y, VisitorA1 function,
            Direction direction) {
        
        final int tx = x + direction.getX();
        final int ty = y + direction.getY();
        
        if (area.valid(tx, ty))
            function.apply(area.get(tx, ty), tx, ty);
    }
    
    /**
     * @see #visit(int, int, VisitorA1, Direction) 
     */
    public final void visit(int x, int y, VisitorA2 function,
            Direction direction) {
        
        final int tx = x + direction.getX();
        final int ty = y + direction.getY();
        
        if (area.valid(tx, ty)) function.apply(area.get(tx, ty));
    }
    
    // ------------------- While ------------------- 
    
    @FunctionalInterface
    public static interface VisitorB1 {
        public boolean apply(Element element, int eX, int eY);
    }
    
    @FunctionalInterface
    public static interface VisitorB2 {
        public boolean apply(Element element);
    }
    
    /**
     * Aplikuje funkci po směru hodinových ručiček všechny <b>hlavní</b>
     * ({@link Direction#isMain() isMain()}) směry. Přestane po prvním volání
     * funkce, které vrátí <code>false</code>.
     * 
     * @see #visitWhile(int, int, VisitorB1, Direction) 
     * @param x výchozí horizontální pozice
     * @param y výchozí vertikální pozice
     * @param function funkce
     * @return <code>false</code> pokud některé volání funkce vrátilo false
     */
    public final boolean visitWhileAll(final int x, final int y,
            VisitorB1 function) {
        
        if (visitWhile(x, y, function, Direction.UP))
            if (visitWhile(x, y, function, Direction.RIGHT))
                if (visitWhile(x, y, function, Direction.DOWN))
                    if (visitWhile(x, y, function, Direction.LEFT)) return true;
        return false;
    }
    
    /**
     * @see #visitWhileAll(int, int, VisitorB1) 
     */
    public final boolean visitWhileAll(final int x, final int y,
            VisitorB2 function) {
        
        if (visitWhile(x, y, function, Direction.UP))
            if (visitWhile(x, y, function, Direction.RIGHT))
                if (visitWhile(x, y, function, Direction.DOWN))
                    if (visitWhile(x, y, function, Direction.LEFT)) return true;
        return false;
    }
    
    /**
     * Postupně aplikuje funkci na všechny požadované strany. Přestane po prvním
     * volání funkce, které vrátí <code>false</code>.
     * 
     * @see #visitWhile(int, int, VisitorB1, Direction) 
     * @param x výchozí horizontální pozice
     * @param y výchozí vertikální pozice
     * @param function funkce
     * @param directions směry od výchozí pozice
     * @return <code>false</code> pokud některé volání funkce vrátilo false
     */
    public final boolean visitWhile(int x, int y, VisitorB1 function,
            Direction... directions){
        
        for (Direction dir : directions)
            if (!visitWhile(x, y, function, dir)) return false;
        
        return true;
    }
    
    /**
     * @see #visitWhile(int, int, VisitorB1, Direction...) 
     */
    public final boolean visitWhile(int x, int y, VisitorB2 function,
            Direction... directions){
        
        for (Direction dir : directions)
            if (!visitWhile(x, y, function, dir)) return false;
        
        return true;
    }
    
    /**
     * Aplikuje funkci na bod ležící určitým směrem od výchozí pozice.
     * Tedy pokud taková pozice existuje.
     * 
     * @param x výchozí horizontální pozice
     * @param y výchozí vertikální pozice
     * @param function funkce
     * @param direction směry od výchozí pozice
     * @return <code>false</code> pokud některé volání funkce vrátilo false
     */
    public final boolean visitWhile(int x, int y, VisitorB1 function,
            Direction direction){
        
        final int tx = x + direction.getX();
        final int ty = y + direction.getY();
        
        return area.valid(tx, ty)
                ? function.apply(area.get(tx, ty), tx, ty)
                : true;
    }
    
    /**
     * @see #visitWhile(int, int, VisitorB1, Direction) 
     */
    public final boolean visitWhile(int x, int y, VisitorB2 function,
            Direction direction){
        
        final int tx = x + direction.getX();
        final int ty = y + direction.getY();
        
        return area.valid(tx, ty) ? function.apply(area.get(tx, ty)) : true;
    }
    
}