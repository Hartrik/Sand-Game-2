
package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.sandbox.element.BrushTemperatureChanger.FuncAdvanced;
import cz.hartrik.sg2.brush.jfx.BrushCollectionBuilder;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluenced;
import java.util.function.Supplier;
import javafx.scene.image.Image;

/**
 * Štětce pro práci s teplotou.
 * 
 * @version 2015-04-04
 * @author Patrik Harag
 */
public class CollectionThermalTools {
    
    static final String URL_TEMP_UP   = path("Icon - temperature up.png");
    static final String URL_TEMP_DOWN = path("Icon - temperature down.png");
    
    private static String path(String fileName) {
        return Resources.absolutePath(fileName, CollectionThermalTools.class);
    }
    
    public static void createBrushes(BrushCollectionBuilder collectionBuilder,
            Supplier<Tools> toolSupplier) {
        
        final Image tempUp   = new Image(URL_TEMP_UP);
        final Image tempDown = new Image(URL_TEMP_DOWN);
        
        collectionBuilder
                .add(new BrushTemperatureChanger(
                        collectionBuilder.load(300),
                        fSetTemperature(200, toolSupplier)), tempUp)
                
                .add(new BrushTemperatureChanger(
                        collectionBuilder.load(301),
                        fSetTemperature(500, toolSupplier)), tempUp)
                
                .add(new BrushTemperatureChanger(
                        collectionBuilder.load(302),
                        fSetTemperature(1000, toolSupplier)), tempUp)
                
               .add(new BrushTemperatureChanger(
                        collectionBuilder.load(303),
                        fSetTemperature(1500, toolSupplier)), tempUp)
                
                .add(new BrushTemperatureChanger(
                        collectionBuilder.load(304),
                        fSetTemperature(2000, toolSupplier)), tempUp)
                
                .add(new BrushTemperatureChanger(
                        collectionBuilder.load(305),
                        fSetTemperature(3000, toolSupplier)), tempUp)
                
                .add(new BrushTemperatureChanger(
                        collectionBuilder.load(310),
                        (element) -> element.setTemperature(20)), tempDown)
            ;
    }
    
    private static FuncAdvanced fSetTemperature(int temp, Supplier<Tools> supplier) {
        return (ThermalInfluenced element, int x, int y, ElementArea area) -> 
                supplier.get().getFireTools().affect(x, y, element, temp, false);
    }
    
}