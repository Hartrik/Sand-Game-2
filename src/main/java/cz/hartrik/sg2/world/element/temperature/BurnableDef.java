
package cz.hartrik.sg2.world.element.temperature;

/**
 * Zjednodušuje implementaci {@link Flammable}.
 * 
 * @version 2014-05-16
 * @author Patrik Harag
 */
public interface BurnableDef extends Burnable {
    
    public FireSettings getFireSettings();

    @Override
    public default boolean getChanceToBurn() {
        return getFireSettings().getChanceToBurn().nextBoolean();
    }
    
    @Override
    public default int getFlammableNumber() {
        return getFireSettings().getFlammableNumber();
    }

    @Override
    public default int getDegreeOfFlammability() {
        return getFireSettings().getDegreeOfFlammability();
    }

    @Override
    public default boolean getChanceToFlareUp() {
        return getFireSettings().getChanceToFlareUp().nextBoolean();
    }
    
}