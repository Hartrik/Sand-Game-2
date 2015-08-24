
package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.common.Color;
import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.brush.ABrushBase;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.BrushEffect;
import cz.hartrik.sg2.brush.SourceableBrushDef;
import cz.hartrik.sg2.brush.jfx.BrushCollectionBuilder;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.fluid.SaltDry;
import cz.hartrik.sg2.world.element.fluid.SaltWater;
import cz.hartrik.sg2.world.element.fluid.Steam;
import cz.hartrik.sg2.world.element.type.*;
import java.util.function.Supplier;
import javafx.scene.image.Image;

/**
 * Dočasné a testovací štětce.
 * 
 * @version 2015-02-15
 * @author Patrik Harag
 */
public class CollectionTest {
    
    static final String URL_WIP = path("Icon - WIP.png");
    static final String URL_WATER = path("Icon - water.png");
    
    private static String path(String fileName) {
        return Resources.absolutePath(fileName, CollectionTest.class);
    }
    
    public static void createBrushes(BrushCollectionBuilder collectionBuilder,
            BrushManager<Brush> manager) {
        
//        collectionBuilder
//                .add(new JFXBrushCustomSourceable(
//                        collectionBuilder.load(72), new Image(URL_WATER),
//                        (e) -> new Water(new Color(0, 0, 178), 1000)) {
//                            @Override
//                            public boolean isProducer(Element element) {
//                                return element instanceof Water || element instanceof Steam;
//                            }
//                        }
//                );
        
        collectionBuilder
                .add(new BrushEffect(
                            collectionBuilder.load(320),
                            e -> (e instanceof Destructible) ? ((Destructible) e).destroy() : null),
                        URL_WIP)
        
                .add(new BrushEffect(
                            collectionBuilder.load(321),
                            e -> (e instanceof Glueable) ? ((Glueable) e).glue() : null),
                        URL_WIP)
        ;

        collectionBuilder
                .add(new WaterBrush(
                        new BrushInfo(1000, "* Voda II"),
                        () -> new SaltWater(new Color(0, 0, 178), 1000)) {
                            
                            @Override
                            public boolean isProducer(Element element) {
                                return element instanceof SaltWater
                                    || element instanceof Steam;
                            }
                        }, new Image(URL_WIP))
                
                .add(new WaterBrush(
                        new BrushInfo(1001, "* Voda II - slaná"),
                        () -> {
                            SaltWater s = new SaltWater(new Color(0, 0, 178), 1000);
                            s.setSalt(4000);
                            return s;
                            
                        }), new Image(URL_WIP)
                )
                
                .add(new WaterBrush(
                        new BrushInfo(1002, "* Sůl"),
                        () -> new SaltDry(ElementList.SALT_COLORS.get(), 2000)) {

                            @Override
                            public boolean isProducer(Element element) {
                                return element instanceof SaltDry;
                            }
                        }, new Image(URL_WIP)
                )
        ;
    }
    
    private static class WaterBrush extends ABrushBase implements SourceableBrushDef {

        private final Supplier<Element> supplier;
        
        public WaterBrush(BrushInfo brushInfo, Supplier<Element> supplier) {
            super(brushInfo);
            this.supplier = supplier;
        }
        
        @Override
        public Element getElement(Element current) {
            return supplier.get();
        }
        
    }
    
}