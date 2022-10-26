package cz.hartrik.common;

/**
 * Jednoduché počítadlo. Může posloužit ve spojení s lambda výrazy, uvnitř
 * kterých není možné změnit hodnotu vnějšího lokálního primitivního typu.
 * Výchozí hodnota počítadla je <code>0</code>.
 * 
 * @version 2014-12-18
 * @author Patrik Harag
 */
public final class Counter {
    
    private volatile int value;
    
    /**
     * Vytvoří nové počítadlo s počáteční hodnotou.
     * 
     * @param value počáteční hodnota počítadla
     */
    public Counter(int value) {
        this.value = value;
    }

    /**
     * Vytvoří nové počítadlo s počáteční hodnotou nastavenou na nulu.
     */
    public Counter() {
        this(0);
    }

    /**
     * Navýší počítadlo o 1.
     * 
     * @return hodnota počítadla po navýšení
     */
    public int increase() {
        return ++value;
    }
    
    /**
     * Navýší počítadlo o libovolné celé číslo.
     * 
     * @param increment přírůstek
     * @return hodnota počítadla po navýšení
     */
    public int increase(int increment) {
        return (value += increment);
    }
    
    /**
     * Sníží počítadlo o 1.
     * 
     * @return hodnota počítadla po snížení
     */
    public int decrease() {
        return --value;
    }
    
    /**
     * Sníží počítadlo o libovolné celé číslo.
     * 
     * @param decrement přírůstek
     * @return hodnota počítadla po snížení
     */
    public int decrease(int decrement) {
        return (value -= decrement);
    }
    
    /**
     * Vrátí aktuální hodnotu a nastaví počítadlo na 0.
     * 
     * @return hodnota před resetem
     */
    public int reset() {
        final int returnValue = value;
        value = 0;
        return returnValue;
    }
    
    /**
     * Vrátí aktuální hodnotu počítadla.
     * 
     * @return aktuální hodnota
     */
    public int getValue() {
        return value;
    }
    
}