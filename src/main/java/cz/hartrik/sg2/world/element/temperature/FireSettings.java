
package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.common.Checker;
import cz.hartrik.sg2.random.Chance;
import cz.hartrik.sg2.random.RatioChance;
import java.io.Serializable;

/**
 * Přepravka pro nastavení hořlavosti.
 * 
 * @see Flammable
 * @see Burnable
 * 
 * @version 2014-05-08
 * @author Patrik Harag
 */
public final class FireSettings implements Serializable {
    
    private static final long serialVersionUID = 83715083867368_99_001L;

    private final Chance chanceToFlareUp;
    private final int degreeOfFlammability;
    private final int flammableNumber;
    private final Chance chanceToBurn;
    
    public FireSettings(int chanceToFlareUp, int degreeOfFlammability,
            int flammableNumber, int chanceToBurn) {
        
        this(new RatioChance(chanceToFlareUp), degreeOfFlammability,
                flammableNumber, new RatioChance(chanceToBurn));
    }
    
    public FireSettings(Chance chanceToFlareUp, int degreeOfFlammability,
            int flammableNumber, Chance chanceToBurn) {
        
        this.chanceToFlareUp = Checker.requireNonNull(chanceToFlareUp);
        this.degreeOfFlammability = degreeOfFlammability;
        this.flammableNumber = flammableNumber;
        this.chanceToBurn = Checker.requireNonNull(chanceToBurn);
    }

    public Chance getChanceToBurn() {
        return chanceToBurn;
    }

    public Chance getChanceToFlareUp() {
        return chanceToFlareUp;
    }

    public int getDegreeOfFlammability() {
        return degreeOfFlammability;
    }

    public int getFlammableNumber() {
        return flammableNumber;
    }
    
}