package cz.hartrik.sg2.tool;

import cz.hartrik.sg2.brush.Brush;
import java.util.function.Consumer;

/**
 * Rozhraní pro "želvu".
 * 
 * @version 2015-03-18
 * @author Patrik Harag
 */
public interface Turtle {
    
    public static final double DEFAULT_ANGLE = 0.;
    public static final double DEFAULT_ANGLE_CHANGE = 90.;

    // --- relativní ---
    
    public default Turtle left() {
        return left(DEFAULT_ANGLE_CHANGE);
    }
    
    public default Turtle left(double angle) {
        return setAngle(getAngle() - angle);
    }

    public default Turtle right() {
        return right(DEFAULT_ANGLE_CHANGE);
    }

    public default Turtle right(double angle) {
        return setAngle(getAngle() + angle);
    }

    public default Turtle reverse() {
        return setAngle(getAngle() + 180);
    }
    
    public Turtle move(double length);
    
    public Turtle draw(double length);

    public Turtle point();

    // --- absolutní ---
    
    public Turtle setX(double x);

    public Turtle setY(double y);
    
    public Turtle center();
    
    public Turtle setAngle(double a);

    public Turtle setBrush(Brush brush);

    public Turtle setTool(Draggable tool);
    
    // --- speciální ---
    
    public Turtle repeat(int i);
    
    public Turtle end();
    
    public Turtle run(Consumer<Turtle> procedure);
    
    // --- gettery ---
    
    public double getAngle();

    public double getX();

    public double getY();
    
}