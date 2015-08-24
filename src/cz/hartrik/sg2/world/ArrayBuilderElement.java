
package cz.hartrik.sg2.world;

import cz.hartrik.common.Color;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * Builder sloužící k vytvoření pole různě barevných elementů určitého typu.
 * 
 * @version 2014-12-09
 * @author Patrik Harag
 * @param <T> typ elementu
 */
public class ArrayBuilderElement<T extends Element> extends ArrayBuilder<T> {
    
    // set function
    
    @Override
    public ArrayBuilderElement<T> setFunction(Function<Color, T> function) {
        super.setFunction(function); return this;
    }
    
    // add color
    
    @Override
    public ArrayBuilderElement<T> addColor(int r, int g, int b) {
        super.addColor(r, g, b); return this;
    }
    
    @Override
    public ArrayBuilderElement<T> addColor(Color color) {
        super.addColor(color); return this;
    }
    
    @Override
    public ArrayBuilderElement<T> addColor(int r, int g, int b, int count) {
        super.addColor(r, g, b, count); return this;
    }
    
    @Override
    public ArrayBuilderElement<T> addColor(Color color, int count) {
        super.addColor(color, count); return this;
    }
    
    // build
    
    public Element[] build() {
        Element[] elements = new Element[countLength(colors)];
        fill(elements, colors, function);
        return elements;
    }
    
    public T[] build(IntFunction<T[]> arraySupplier) {
        T[] elements = arraySupplier.apply(countLength(colors));
        fill(elements, colors, function);
        return elements;
    }
    
}