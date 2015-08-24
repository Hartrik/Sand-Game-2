package cz.hartrik.sg2.world.element;

import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.flora.Plant;
import cz.hartrik.sg2.world.element.fluid.EWater;
import cz.hartrik.sg2.world.element.fluid.Fluid;
import cz.hartrik.sg2.world.element.gas.Gas;
import cz.hartrik.sg2.world.element.powder.Powder;
import cz.hartrik.sg2.world.element.type.Metamorphic;
import java.util.Optional;

/**
 * Poskytuje statické metody pro práci s elementy.
 * 
 * @since 2.02
 * @version 2015-03-21
 * @author Patrik Harag
 */
public class Elements {
    
    // zjišťovací metody
    
    public static boolean isSolid(Element element) {
        return element instanceof SolidElement;
    }
    
    public static boolean isNonSolid(Element element) {
        return element instanceof NonSolidElement;
    }
    
    public static boolean isPowder(Element element) {
        return element instanceof Powder;
    }
    
    public static boolean isFluid(Element element) {
        return element instanceof Fluid;
    }
    
    public static boolean isWater(Element element) {
        return element instanceof EWater;
    }
    
    public static boolean isGas(Element element) {
        return element instanceof Gas;
    }
    
    public static boolean isPlant(Element element) {
        return element instanceof Plant;
    }
    
    public static boolean isAir(Element element) {
        return element instanceof Air;
    }
    
    // další
    
    public static Optional<Element> getBaseElement(Element element) {
        return (element instanceof Metamorphic)
            ? Optional.of(((Metamorphic) element).getBasicElement())
            : Optional.empty();
    }
    
}