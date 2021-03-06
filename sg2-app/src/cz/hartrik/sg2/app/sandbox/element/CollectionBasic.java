
package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.common.Color;
import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.brush.BrushEffect;
import cz.hartrik.sg2.brush.build.BrushCollectionBuilder;
import cz.hartrik.sg2.random.RandomSuppliers;
import cz.hartrik.sg2.world.element.fluid.Napalm;
import cz.hartrik.sg2.world.element.powder.*;
import cz.hartrik.sg2.world.element.solid.*;
import cz.hartrik.sg2.world.element.special.*;
import cz.hartrik.sg2.world.element.temperature.*;

import static cz.hartrik.sg2.app.sandbox.element.ElementList.*;

/**
 * Základní kolekce štětců.
 *
 * @version 2016-07-02
 * @author Patrik Harag
 */
public class CollectionBasic {

    static final String URL_WOOD = file("texture - wooden planks.png");
    static final String URL_FIRE = file("icon - hazard - F.png");
    static final String URL_IRON = file("texture - iron.png");
    static final String URL_ROCK = file("texture - dark rock.png");
    static final String URL_WATER = file("icon - water.png");
    static final String URL_FILTER = file("texture - filter.png");
    static final String URL_FABRIC = file("texture - fabric.png");
    static final String URL_SPONGE = file("texture - sponge.png");
    static final String URL_CARBON = file("texture - carbon.png");
    static final String URL_BACTERIA = file("icon - bacteria.png");
    static final String URL_BACTERIA_A = file("icon - bacteria A.png");
    static final String URL_REF_Metal = file("texture - iron alloy.png");
    static final String URL_BRICK_WALL = file("texture - brick.png");
    static final String URL_STONE_WALL = file("texture - stone wall.jpg");

    private static String file(String fileName) {
        return Resources.absolutePath(fileName, CollectionBasic.class);
    }

    public static void createBrushes(BrushCollectionBuilder collectionBuilder) {

        final FireSettings woodFS = new FireSettings(50, 600, 2800, 1500);
        final FireSettings wickFS = new FireSettings(1, 0, 1200, 10);

        collectionBuilder
            // vzduch (0 - 9)
            .addRnd(0).setElements().hidden().build()
            .addRnd(1, AIR)
            .addRnd(2)
                .setElements(VOID)
                .hidden().build()

            // pevné (10 - 39)
            .addRnd(10)
                .setElements(WALL_COL)
                .setProducer(e -> e instanceof ClassicWall).build()
            .addRnd(11)
                .setElements(CONCRETE_1, CONCRETE_2)
                .setProducer(e -> e instanceof Concrete)
                .hidden().build()
            .addRnd(12)
                .setElements(CEMENT_1, CEMENT_2)
                .setProducer(e -> e instanceof Cement
                               || e instanceof Concrete).build()
            .addRnd(13)
                .setElements(WET_CEMENT_1_1, WET_CEMENT_2_1)
                .setProducer(e -> e instanceof CementWetPowder
                               || e instanceof CementWetFluid).build()
            .addTex(14)
                .setTexture(URL_ROCK)
                .setMFactory(Rock::new)
                .setProducer(e -> e instanceof Rock).build()
            .addTex(15)
                .setTexture(URL_STONE_WALL)
                .setMFactory(StoneWall::new)
                .setProducer(e -> e instanceof StoneWall).build()
            .addTex(16)
                .setTexture(URL_BRICK_WALL)
                .setMFactory(BrickWall::new)
                .setProducer(e -> e instanceof BrickWall).build()
            .addTex(20)
                .setTexture(URL_IRON)
                .setMFactory(Iron::new)
                .setProducer(e -> e instanceof Iron).build()
//            .addTex(21)
//                .setTexture(URL_IRON)
//                .setSFactory(c -> new Iron(c, 1800)).build()
            .addTex(22)
                .setTexture(URL_REF_Metal)
                .setMFactory(RefractoryMetal::new)
                .setProducer(e -> e instanceof RefractoryMetal).build()
            .addTex(23)
                .setTexture(URL_CARBON)
                .setMFactory(Carbon::new)
                .setProducer(e -> e instanceof Carbon).build()
            .addTex(30)
                .setTexture(URL_WOOD)
                .setMFactory(c -> new Wood(c, woodFS))
                .setProducer(e -> e instanceof Wood).build()
            .addTex(35)
                .setTexture(URL_FABRIC)
                .setMFactory(c -> new Wick(c, wickFS))
                .setProducer(e -> e instanceof Wick).build()

            // písky (40 - 69)
            .addRnd(40, SAND_COL)
            .addRnd(41, SOIL_COL)
            .addRnd(42, STONE_COL)
            .addRnd(43, SALT_COL)

            // kapaliny (70 - 99)
            .addRnd(70, WATER)
            .addRnd(71, WATER_SALT)

            // fauna, flora (100 - 129)
            .addHidden(new GrassBrush(collectionBuilder.load(100),
                    x -> 100 + x * x * 7, GRASS))

            .addHidden(new GrassBrush(collectionBuilder.load(101),
                    x -> 100 + x * x * 8, WILD_GRASS))

            .addHidden(new GrassBrush(collectionBuilder.load(102),
                    x -> 100 + x * x * x * 3, REED))

            .add(new SeedBrush(collectionBuilder.load(110), GRASS,
                    x -> 100 + x * x * 7, SEEDS))

            .add(new SeedBrush(collectionBuilder.load(111), WILD_GRASS,
                    x -> 100 + x * x * 8, WILD_SEEDS))

            .add(new SeedBrush(collectionBuilder.load(112), REED,
                    x -> 100 + x * x * x * 3, REED_SEEDS))

            .add(new BrushEffect(collectionBuilder.load(120), BACTERIA_FACT),
                    URL_BACTERIA)

            .add(new BrushEffect(collectionBuilder.load(121), BACTERIA_A_FACT),
                    URL_BACTERIA_A)

            // hořlaviny (130 - )
            .addRnd(130)
                .setElements(FIRE_COL)
                .setProducer(e -> e instanceof Fire)
                .setImage(URL_FIRE).build()
            .addRnd(131, new Heater(Color.ORANGE, 500))
            .addRnd(132, new Heater(Color.BLUE, 0))
            .addRnd(133)
                .setElements(COAL_COL)
                .setProducer(e -> e instanceof Coal).build()
            .addRnd(134, THERMITE_1_COL)
            .addRnd(135, THERMITE_2_COL)
            .addRnd(140, OIL)
            .addRnd(141, new Napalm(new Color(179, 102, 26), 950))
            .addRnd(150, NATURAL_GAS)

            // speciální (200 - 299)
            .addRnd(200, new Duplicator(Color.CYAN))
            .addRnd(201, new Eliminator(Color.MAGENTA))
            .addRnd(202, new BlackHole(Color.BLACK))
            .addTex(203)
                .setTexture(URL_SPONGE)
                .setMFactory(Sponge::new)
                .setProducer(e -> e instanceof Sponge).build()
            .addRnd(204, new DesiccatingPowder(Color.ORANGE, 100))

            .addTex(210)
                .setTexture(URL_FILTER)
                .setMFactory(FilterEmpty::new)
                .setProducer(e -> e instanceof Filter).build()

            .addRnd(220)
                .setElements(new PortalIn(RandomSuppliers.of(
                        new Color(14, 156, 202),
                        new Color( 8, 140, 183)))).build()
            .addRnd(221)
                .setElements(new PortalOut(RandomSuppliers.of(
                        new Color(179,  77, 26),
                        new Color(204, 102, 51)))).build()
        ;
    }

}