package cz.hartrik.sg2.app.module.script;

import cz.hartrik.sg2.tool.Turtle;
import cz.hartrik.sg2.tool.TurtleImpl;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.tool.Draggable;
import cz.hartrik.sg2.world.ElementArea;

/**
 * @version 2015-03-27
 * @author Patrik Harag
 */
public class TurtleFactory {
    
    private final ElementArea area;
    private final Controls controls;
    
    public TurtleFactory(ElementArea area, Controls controls) {
        this.area = area;
        this.controls = controls;
    }
    
    public Turtle create(Draggable tool, Brush brush) {
        return new TurtleImpl(area, brush, tool).center();
    }
    
    public Turtle create(Draggable tool) {
        return create(tool, controls.getPrimaryBrush());
    }
    
}