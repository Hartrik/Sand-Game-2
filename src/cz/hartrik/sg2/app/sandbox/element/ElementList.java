
package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.common.Color;
import cz.hartrik.common.WebColors;
import cz.hartrik.common.random.Chance;
import cz.hartrik.common.random.RandomSupplier;
import cz.hartrik.common.random.RandomSuppliers;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.world.ArrayBuilderElement;
import cz.hartrik.sg2.world.BasicElement;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.*;
import cz.hartrik.sg2.world.element.gas.*;
import cz.hartrik.sg2.world.element.powder.*;
import cz.hartrik.sg2.world.element.fauna.*;
import cz.hartrik.sg2.world.element.flora.*;
import cz.hartrik.sg2.world.element.fluid.*;
import cz.hartrik.sg2.world.element.fluid.simplewater.*;
import cz.hartrik.sg2.world.element.solid.*;
import cz.hartrik.sg2.world.element.special.*;
import cz.hartrik.sg2.world.element.temperature.*;
import cz.hartrik.sg2.world.element.type.Organic;
import cz.hartrik.sg2.world.factory.*;
import java.util.function.Supplier;

/**
 * Seznam elementů použitých v základní kolekci.
 * Z důvodu přehlednosti nejsou některé řádky zalomeny.
 *
 * @version 2015-02-13
 * @author Patrik Harag
 */
public class ElementList {
    
    private static Color c(int r, int g, int b) { return new Color(r, g, b); }
    private static Supplier<Color> cSupp(Color... colors) { return RandomSuppliers.of(colors); }
    private static RatioChance rc(int value) { return new RatioChance(value); }
    
    //<editor-fold defaultstate="collapsed" desc="- Jednoduché elementy">
    
    // vzduch
    public static final Air AIR = BasicElement.AIR;
    public static final Air VOID = BasicElement.VOID;
    
    // stěna
    public static final Wall[] WALL_COL = {
        new ClassicWall(c(55, 55, 55)),
        new ClassicWall(c(57, 57, 57))
    };

    // pohlcovači...
    public static final BlackHole BLACK_HOLE = new BlackHole(Color.BLACK);
    public static final Eliminator ELIMINATOR = new Eliminator(Color.MAGENTA);
    public static final Sponge SPONGE = new Sponge(Color.GREEN);
    public static final DesiccatingPowder DESICCATING_SAND = new DesiccatingPowder(Color.ORANGE, 100);
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Písek, půda, kamení">
    
    public static final Sand[] SAND_COL = new ArrayBuilderElement<Sand>()
            .setFunction(c -> new Sand(c, 1500))
            .addColor(214, 212, 154, 4)
            .addColor(225, 217, 171, 4)
            .addColor(203, 201, 142, 4)
            .addColor(195, 194, 134, 2)
            .addColor(218, 211, 165, 2)
            .addColor(223, 232, 201)
            .addColor(186, 183, 128).build(Sand[]::new);
    
    public static final Soil[] SOIL_COL = new ArrayBuilderElement<Soil>()
            .setFunction(c -> new Soil(c, 1500))
            .addColor(142, 104,  72, 6)
            .addColor(114,  81,  58, 6)
            .addColor( 82,  64,  30, 3)
            .addColor(177, 133,  87, 3)
            .addColor(102, 102, 102).build(Soil[]::new);

    public static final Stone[] STONE_COL = new ArrayBuilderElement<Stone>()
            .setFunction(c -> new Stone(c, 1500))
            .addColor(131, 131, 131, 3)
            .addColor(135, 135, 135, 3)
            .addColor(145, 145, 145, 3)
            .addColor(148, 148, 148, 3)
            .addColor(160, 160, 160, 3)
            .addColor(114, 114, 114)
            .addColor(193, 193, 193).build(Stone[]::new);
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Voda">
//    public static final SimpleWater WATER = new SimpleWaterC(cSupp(c(0, 0, 178), c(0, 0, 160)), 1000, Chance.OFTEN);
    public static final SimpleWater WATER = new SimpleWater(c(0, 0, 178), 1000, Chance.OFTEN);
    public static final SimpleSteam STEAM = new SimpleSteam(c(147, 182, 217), -100, new RatioChance(7), WATER, new RatioChance(1000));
    static { WATER.setVaporized(STEAM); }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sůl, slaná voda">
    public static final RandomSupplier<Color> SALT_COLORS = RandomSuppliers.of(
                c(214, 204, 179), c(229, 225, 213), c(219, 215, 203));
    
//    public static final SimpleWaterSalt WATER_SALT = new SimpleWaterSaltC(cSupp(c(0, 0, 100), c(0, 0, 105)), 1070, Chance.OFTEN);
    public static final SimpleWaterSalt WATER_SALT = new SimpleWaterSalt(c(0, 0, 100), 1070, Chance.OFTEN);
    static final ISingleInputFactory<Salt, EWaterSalt> WS_FACTORY = salt -> WATER_SALT;
    
    public static final Salt SALT_1 = new Salt(c(214, 204, 179), 1400, WS_FACTORY);
    public static final Salt SALT_2 = new Salt(c(229, 225, 213), 1400, WS_FACTORY);
    public static final Salt SALT_3 = new Salt(c(219, 215, 203), 1400, WS_FACTORY);
    public static final Salt[] SALT_COL = { SALT_1, SALT_2, SALT_3 };

    public static final Element[] WATER_SALT_COL = { // 21 : 3 == 7 : 1
        STEAM, STEAM, STEAM, STEAM, STEAM, STEAM, STEAM, STEAM, STEAM, STEAM,
        STEAM, STEAM, STEAM, STEAM, STEAM, STEAM, STEAM, STEAM, STEAM, STEAM, STEAM,
        SALT_1, SALT_2, SALT_3
    };
    
    static { WATER_SALT.setVaporized(WATER_SALT_COL); }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Tráva - obyčejná">
    static final Humus GRASS_DEAD_1 = new Humus(c(94, 94, 28), 1520, rc(800), SOIL_COL);
    static final Humus GRASS_DEAD_2 = new Humus(c(90, 94, 28), 1520, rc(800), SOIL_COL);
    static final Humus GRASS_DEAD_3 = new Humus(c(85, 94, 28), 1520, rc(800), SOIL_COL);
    public static final Element[] GRASS_DEAD_COL = { GRASS_DEAD_1, GRASS_DEAD_2, GRASS_DEAD_3, AIR };
    
    static final FireSettings FS = new FireSettings(rc(5), 500, 1200, rc(100));
    
    static final Grass GRASS_1 = new Grass(c(39, 87, 28), rc(400), 9, GRASS_DEAD_COL, FS);
    static final Grass GRASS_2 = new Grass(c( 0, 68,  0), rc(400), 7, GRASS_DEAD_COL, FS);
    static final Grass GRASS_3 = new Grass(c( 0, 60,  0), rc(400), 8, GRASS_DEAD_COL, FS);
    public static final Grass GRASS[] = { GRASS_1, GRASS_2, GRASS_3 };
    
    static final Seed SEED_1 = new Seed(c(160, 160, 0), 50, Chance.ALWAYS, GRASS_1);
    static final Seed SEED_2 = new Seed(c(160, 160, 0), 50, Chance.ALWAYS, GRASS_2);
    static final Seed SEED_3 = new Seed(c(160, 160, 0), 50, Chance.ALWAYS, GRASS_3);
    public static final Seed[] SEEDS = { SEED_1, SEED_2, SEED_3 };
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Tráva - divoká">
    static final WildGrass GRASS_WILD_1 = new WildGrass(c(44, 92, 33), rc(400), 8, GRASS_DEAD_COL, FS);
    static final WildGrass GRASS_WILD_2 = new WildGrass(c( 0, 72,  0), rc(400), 6, GRASS_DEAD_COL, FS);
    static final WildGrass GRASS_WILD_3 = new WildGrass(c( 0, 65,  0), rc(400), 7, GRASS_DEAD_COL, FS);
    public static final WildGrass[] WILD_GRASS = { GRASS_WILD_1, GRASS_WILD_2, GRASS_WILD_3 };
    
    static final Seed SEED_WILD_1 = new Seed(c(178, 178, 0), 50, Chance.ALWAYS, GRASS_WILD_1);
    static final Seed SEED_WILD_2 = new Seed(c(178, 178, 0), 50, Chance.ALWAYS, GRASS_WILD_2);
    static final Seed SEED_WILD_3 = new Seed(c(178, 178, 0), 50, Chance.ALWAYS, GRASS_WILD_3);
    public static final Seed[] WILD_SEEDS = { SEED_WILD_1, SEED_WILD_2, SEED_WILD_3 };
    
    static {
        GRASS_WILD_1.setGrass(WILD_GRASS);
        GRASS_WILD_2.setGrass(WILD_GRASS);
        GRASS_WILD_3.setGrass(WILD_GRASS);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Tráva - rákosí">
    static final Grass REED_1 = new Grass(c( 83, 146, 35), rc(400), 15, new Element[] { GRASS_DEAD_1, AIR}, FS); // světlé
    static final Grass REED_2 = new Grass(c(131, 160,  2), rc(400), 10, new Element[] { GRASS_DEAD_2, AIR}, FS); // žluté
    static final Grass REED_3 = new Grass(c( 31,  93,  3), rc(400), 14, new Element[] { GRASS_DEAD_3, AIR}, FS); // tmavší
    static final Grass REED_4 = new Grass(c( 75, 129,  7), rc(400), 13, new Element[] { GRASS_DEAD_3, AIR}, FS); // světlé
    public static final Grass REED[] = { REED_1, REED_2, REED_3, REED_4 };
    
    static final Seed REED_SEED_1 = new Seed(c(143, 104, 62), 50, Chance.ALWAYS, REED_1);
    static final Seed REED_SEED_2 = new Seed(c(143, 104, 62), 50, Chance.ALWAYS, REED_2);
    static final Seed REED_SEED_3 = new Seed(c(143, 104, 62), 50, Chance.ALWAYS, REED_3);
    static final Seed REED_SEED_4 = new Seed(c(143, 104, 62), 50, Chance.ALWAYS, REED_4);
    public static final Seed[] REED_SEEDS = { REED_SEED_1, REED_SEED_2, REED_SEED_3, REED_SEED_4 };
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Cement">
    public static final Concrete CONCRETE_1 = new Concrete(c(67, 67, 67));
    public static final Concrete CONCRETE_2 = new Concrete(c(70, 70, 70));
    
    public static final CementWetPowder  WET_CEMENT_1_5 = new CementWetPowder( c( 80,  80,  80), 1180, new RatioChance(2000), CONCRETE_1);
    public static final CementWetPowder  WET_CEMENT_1_4 = new CementWetPowder( c( 90,  90,  90), 1160, new RatioChance(2000), WET_CEMENT_1_5);
    public static final CementWetFluid WET_CEMENT_1_3 = new CementWetFluid(c(100, 100, 100), 1140, new RatioChance(1000), WET_CEMENT_1_4);
    public static final CementWetFluid WET_CEMENT_1_2 = new CementWetFluid(c(110, 110, 110), 1120, new RatioChance(1000), WET_CEMENT_1_3);
    public static final CementWetFluid WET_CEMENT_1_1 = new CementWetFluid(c(120, 120, 120), 1095, new RatioChance(1000), WET_CEMENT_1_2);
    
    public static final CementWetPowder  WET_CEMENT_2_5 = new CementWetPowder( c( 83,  83,  83), 1180, new RatioChance(2000), CONCRETE_2);
    public static final CementWetPowder  WET_CEMENT_2_4 = new CementWetPowder( c( 93,  93,  93), 1160, new RatioChance(2000), WET_CEMENT_2_5);
    public static final CementWetFluid WET_CEMENT_2_3 = new CementWetFluid(c(103, 103, 103), 1140, new RatioChance(1000), WET_CEMENT_2_4);
    public static final CementWetFluid WET_CEMENT_2_2 = new CementWetFluid(c(113, 113, 113), 1120, new RatioChance(1000), WET_CEMENT_2_3);
    public static final CementWetFluid WET_CEMENT_2_1 = new CementWetFluid(c(123, 123, 123), 1095, new RatioChance(1000), WET_CEMENT_2_2);
    
    public static final Cement CEMENT_1 = new Cement(Color.LIGHT_GRAY, 1040, WET_CEMENT_1_1);
    public static final Cement CEMENT_2 = new Cement(Color.LIGHT_GRAY, 1040, WET_CEMENT_2_1);
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Oheň">
    public static final Fire FIRE_1 = new Fire(Color.RED, 3000);
    public static final Fire FIRE_2 = new Fire(Color.RED, 600);
    public static final Fire[] FIRE_COL = { FIRE_1, FIRE_1, FIRE_2 };
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Další hořlaviny atd.">
    public static final Color[] COAL_COLORS = {
        new Color(31, 31, 31), new Color(46, 44, 41),
        new Color(13, 13, 13), new Color(17, 17, 15)
    };
    
    public static final PowderMidF[] THERMITE_1_COL = new ArrayBuilderElement<PowderMidF>() {{
        FireSettings fs = new FireSettings(Chance.ALWAYS, 200, 3000, Chance.SOMETIMES);
        function = c -> new PowderMidF(c, 1200, fs);
    }}
            .addColor(new Color(68, 42, 41), 5)
            .addColor(new Color(50, 28, 27), 3)
            .addColor(new Color(83, 55, 55))
            .addColor(new Color(47, 28, 28)).build(PowderMidF[]::new);
    
    
    public static final PowderMidF[] THERMITE_2_COL = new ArrayBuilderElement<PowderMidF>() {{
        FireSettings fs = new FireSettings(Chance.ALWAYS, 200, 3000, rc(500));
        function = c -> new PowderMidF(c, 1200, fs);
    }}
            .addColor(new Color(137,  86,  89), 6)
            .addColor(new Color(125,  70,  72), 2)
            .addColor(new Color(147,  93,  96), 2)
            .addColor(new Color(138,  84,  86), 2)
            .addColor(new Color(118,  72,  75), 2)
            .addColor(new Color(101,  61,  65))
            .addColor(new Color(151, 104, 106)).build(PowderMidF[]::new);

    public static final Element OIL = new FluidWaterF(new Color(208, 163, 16), 900, new FireSettings(Chance.ALWAYS, 210, 2800, Chance.SOMETIMES));
    
    public static final Element NATURAL_GAS = new GasF(new Color(214, 214, 214),  -95, new RatioChance(5),
                new FireSettings(Chance.ALWAYS, 75, 3000, new RatioChance(10)));
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Bakterie">
    
    static final Bacteria bacteria = new Bacteria(new Color(140, 173, 128).changeAlpha(0.5), 100);
    static final Bacteria bacteriaA = new AggressiveBacteria(WebColors.ORANGE_RED.changeAlpha(0.5), 100);
        
    public static final ISingleInputFactory<Element, ? extends Element> BACTERIA_FACT = 
            e -> (e instanceof Organic) ? new InfectedSolid<>(e, bacteria) : null;
    
    public static final ISingleInputFactory<Element, ? extends Element> BACTERIA_A_FACT = 
            e -> (e instanceof Organic) ? new InfectedSolid<>(e, bacteriaA) : null;
    
    //</editor-fold>
    
}