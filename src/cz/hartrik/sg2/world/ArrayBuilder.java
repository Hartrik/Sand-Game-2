
package cz.hartrik.sg2.world;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Color;
import cz.hartrik.common.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Abstraktní builder sloužící k vytvoření pole/kolekce podle nastavených
 * barev.
 * 
 * @version 2014-08-19
 * @author Patrik Harag
 * @param <T> výstup
 */
public abstract class ArrayBuilder<T> {
    
    protected List<Pair<Color, Integer>> colors = new ArrayList<>();
    protected Function<Color, T> function = null;
    
    // set function
    
    public ArrayBuilder<T> setFunction(Function<Color, T> function) {
        this.function = function;
        
        return this;
    }
    
    // add color
    
    public ArrayBuilder<T> addColor(int r, int g, int b) {
        return addColor(new Color(r, g, b), 1);
    }
    
    public ArrayBuilder<T> addColor(Color color) {
        return addColor(color, 1);
    }
    
    public ArrayBuilder<T> addColor(int r, int g, int b, int count) {
        return addColor(new Color(r, g, b), count);
    }
    
    public ArrayBuilder<T> addColor(Color color, int count) {
        colors.add(Pair.of(
                Checker.requireNonNull(color),
                Checker.requireRange(count, 1, 100)));
        
        return this;
    }
    
    // pomocné metody
    
    protected void fill(Object[] array, Collection<Pair<Color, Integer>> colors,
            Function<Color, T> function) {
        
        int i = 0;
        for (Pair<Color, Integer> pair : colors) {
            T element = function.apply(pair.getFirst());
            
            for (int j = 0; j < pair.getSecond(); j++) {
                array[i++] = element;
            }
        }
    }
    
    protected int countLength(Collection<Pair<Color, Integer>> colors) {
        return colors.stream()
                .collect(Collectors.summingInt(s -> s.getSecond()));
        
//        int length = 0;
//        for (Pair<Color, Integer> pair : colors)
//            length += pair.getSecond();
//
//        return length;
    }
    
}