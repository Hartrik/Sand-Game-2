
package cz.hartrik.sg2.world.factory;

import cz.hartrik.sg2.world.Element;

/**
 * Továrna ukládající instance do mapy, která podporuje lambda výrazy.
 * 
 * @version 2014-04-15
 * @author Patrik Harag
 * @param <E> element, který je "továrnou" produkován
 * @param <I> vstup potřebný k vytvoření instance
 */
public class FuncMapFactory<I, E extends Element> extends MapFactory<I, E> {

    private static final long serialVersionUID = 83715083867368_10_003L;
    
    protected final FactoryFunction<I, E> function;

    public FuncMapFactory(FactoryFunction<I, E> function) {
        this.function = function;
    }
    
    @Override
    protected E createInstance(I input) {
        return function.apply(input);
    }
    
}