
package cz.hartrik.common;

import cz.hartrik.common.reflect.LibraryClass;

/**
 * Seznam pojmenovaných barev.
 * Podle <a href="http://en.wikipedia.org/wiki/Web_colors#X11_color_names">
 * wikipedie</a>.
 * 
 * @version 2014-06-15
 * @author Patrik Harag
 */
@LibraryClass
public final class WebColors {

    private WebColors() {}

    public static final Color PINK                   = new Color(255, 192, 203);
    public static final Color LIGHT_PINK             = new Color(255, 182, 193);
    public static final Color HOT_PINK               = new Color(255, 105, 180);
    public static final Color DEEP_PINK              = new Color(255,  20, 147);
    public static final Color PALE_VIOLET_RED        = new Color(219, 112, 147);
    public static final Color MEDIUM_VIOLET_RED      = new Color(199,  21, 133);

    public static final Color LIGHT_SALMON           = new Color(255, 160, 122);
    public static final Color SALMON                 = new Color(250, 128, 114);
    public static final Color DARK_SALMON            = new Color(233, 150, 122);
    public static final Color LIGHT_CORAL            = new Color(240, 128, 128);
    public static final Color INDIAN_RED             = new Color(205,  92,  92);
    public static final Color CRIMSON                = new Color(220,  20,  60);
    public static final Color FIRE_BRICK             = new Color(178,  34,  34);
    public static final Color DARK_RED               = new Color(139,   0,   0);
    public static final Color RED                    = Color.RED;

    public static final Color ORANGE_RED             = new Color(255,  69,   0);
    public static final Color TOMATO                 = new Color(255,  99,  71);
    public static final Color CORAL                  = new Color(255, 127,  80);
    public static final Color DARK_ORANGE            = new Color(255, 140,   0);
    public static final Color ORANGE                 = new Color(255, 165,   0);

    public static final Color YELLOW                 = Color.YELLOW;
    public static final Color LIGHT_YELLOW           = new Color(255, 255, 224);
    public static final Color LEMON_CHIFFON          = new Color(255, 250, 205);
    public static final Color LIGHT_GOLDENROD_YELLOW = new Color(250, 250, 210);
    public static final Color PAPAYA_WHIP            = new Color(255, 239, 213);
    public static final Color MOCCASIN               = new Color(255, 228, 181);
    public static final Color PEACH_PUFF             = new Color(255, 218, 185);
    public static final Color PALE_GOLDENROD         = new Color(238, 232, 170);
    public static final Color KHAKI                  = new Color(240, 230, 140);
    public static final Color DARK_KHAKI             = new Color(189, 183, 107);
    public static final Color GOLD                   = new Color(255, 215,   0);

    public static final Color CORNSILK               = new Color(255, 248, 220);
    public static final Color BLANCHED_ALMOND        = new Color(255, 235, 205);
    public static final Color BISQUE                 = new Color(255, 228, 196);
    public static final Color NAVAJO_WHITE           = new Color(255, 222, 173);
    public static final Color WHEAT                  = new Color(245, 222, 179);
    public static final Color BURLY_WOOD             = new Color(222, 184, 135);
    public static final Color TAN                    = new Color(210, 180, 140);
    public static final Color ROSY_BROWN             = new Color(188, 143, 143);
    public static final Color SANDY_BROWN            = new Color(244, 164,  96);
    public static final Color GOLDENROD              = new Color(218, 165,  32);
    public static final Color DARK_GOLDENROD         = new Color(184, 134,  11);
    public static final Color PERU                   = new Color(205, 133,  63);
    public static final Color CHOCOLATE              = new Color(210, 105,  30);
    public static final Color SADDLE_BROWN           = new Color(139,  69,  19);
    public static final Color SIENNA                 = new Color(160,  82,  45);
    public static final Color BROWN                  = new Color(165,  42,  42);
    public static final Color MAROON                 = new Color(128,   0,   0);

    public static final Color DARK_OLIVE_GREEN       = new Color( 85, 107,  47);
    public static final Color OLIVE                  = new Color(128, 128,   0);
    public static final Color OLIVE_DRAB             = new Color(107, 142,  35);
    public static final Color YELLOW_GREEN           = new Color(154, 205,  50);
    public static final Color LIME_GREEN             = new Color( 50, 205,  50);
    public static final Color LIME                   = new Color(  0, 255,   0);
    public static final Color LAWN_GREEN             = new Color(124, 252,   0);
    public static final Color CHARTREUSE             = new Color(127, 255,   0);
    public static final Color GREEN_YELLOW           = new Color(173, 255,  47);
    public static final Color SPRING_GREEN           = new Color(  0, 255, 127);
    public static final Color MEDIUM_SPRING_GREEN    = new Color(  0, 250, 154);
    public static final Color LIGHT_GREEN            = new Color(144, 238, 144);
    public static final Color PALE_GREEN             = new Color(152, 251, 152);
    public static final Color DARK_SEA_GREEN         = new Color(143, 188, 143);
    public static final Color MEDIUM_SEA_GREEN       = new Color( 60, 179, 113);
    public static final Color SEA_GREEN              = new Color( 46, 139,  87);
    public static final Color FOREST_GREEN           = new Color( 34, 139,  34);
    public static final Color GREEN                  = new Color(  0, 128,   0);
    public static final Color DARK_GREEN             = new Color(  0, 100,   0);

    public static final Color MEDIUM_AQUAMARINE      = new Color(102, 205, 170);
    public static final Color AQUA                   = Color.CYAN;
    public static final Color CYAN                   = Color.CYAN;
    public static final Color LIGHT_CYAN             = new Color(224, 255, 255);
    public static final Color PALE_TURQUOISE         = new Color(175, 238, 238);
    public static final Color AQUAMARINE             = new Color(127, 255, 212);
    public static final Color TURQUOISE              = new Color( 64, 224, 208);
    public static final Color MEDIUM_TURQUOISE       = new Color( 72, 209, 204);
    public static final Color DARK_TURQUOISE         = new Color(  0, 206, 209);
    public static final Color LIGHT_SEA_GREEN        = new Color( 32, 178, 170);
    public static final Color CADET_BLUE             = new Color( 95, 158, 160);
    public static final Color DARK_CYAN              = new Color(  0, 139, 139);
    public static final Color TEAL                   = new Color(  0, 128, 128);

    public static final Color LIGHT_STEEL_BLUE       = new Color(176, 196, 222);
    public static final Color POWDER_BLUE            = new Color(176, 224, 230);
    public static final Color LIGHT_BLUE             = new Color(173, 216, 230);
    public static final Color SKY_BLUE               = new Color(135, 206, 235);
    public static final Color LIGHT_SKY_BLUE         = new Color(135, 206, 250);
    public static final Color DEEP_SKY_BLUE          = new Color(  0, 191, 255);
    public static final Color DODGER_BLUE            = new Color( 30, 144, 255);
    public static final Color CORNFLOWER_BLUE        = new Color(100, 149, 237);
    public static final Color STEEL_BLUE             = new Color( 70, 130, 180);
    public static final Color ROYAL_BLUE             = new Color( 65, 105, 225);
    public static final Color BLUE                   = Color.BLUE;
    public static final Color MEDIUM_BLUE            = new Color(  0,   0, 205);
    public static final Color DARK_BLUE              = new Color(  0,   0, 139);
    public static final Color NAVY                   = new Color(  0,   0, 128);
    public static final Color MIDNIGHT_BLUE          = new Color( 25,  25, 112);

    public static final Color LAVENDER               = new Color(230, 230, 250);
    public static final Color THISTLE                = new Color(216, 191, 216);
    public static final Color PLUM                   = new Color(221, 160, 221);
    public static final Color VIOLET                 = new Color(238, 130, 238);
    public static final Color ORCHID                 = new Color(218, 112, 214);
    public static final Color FUCHSIA                = Color.MAGENTA;
    public static final Color MAGENTA                = Color.MAGENTA;
    public static final Color MEDIUM_ORCHID          = new Color(186,  85, 211);
    public static final Color MEDIUM_PURPLE          = new Color(147, 112, 219);
    public static final Color BLUE_VIOLET            = new Color(138,  43, 226);
    public static final Color DARK_VIOLET            = new Color(148,   0, 211);
    public static final Color DARK_ORCHID            = new Color(153,  50, 204);
    public static final Color DARK_MAGENTA           = new Color(139,   0, 139);
    public static final Color PURPLE                 = new Color(128,   0, 128);
    public static final Color INDIGO                 = new Color( 75,   0, 130);
    public static final Color DARK_SLATE_BLUE        = new Color( 72,  61, 139);
    public static final Color SLATE_BLUE             = new Color(106,  90, 205);
    public static final Color MEDIUM_SLATE_BLUE      = new Color(123, 104, 238);

    public static final Color WHITE                  = Color.WHITE;
    public static final Color SNOW                   = new Color(255, 250, 250);
    public static final Color HONEYDEW               = new Color(240, 255, 240);
    public static final Color MINT_CREAM             = new Color(245, 255, 250);
    public static final Color AZURE                  = new Color(240, 255, 255);
    public static final Color ALICE_BLUE             = new Color(240, 248, 255);
    public static final Color GHOST_WHITE            = new Color(248, 248, 255);
    public static final Color WHITE_SMOKE            = new Color(245, 245, 245);
    public static final Color SEASHELL               = new Color(255, 245, 238);
    public static final Color BEIGE                  = new Color(245, 245, 220);
    public static final Color OLD_LACE               = new Color(253, 245, 230);
    public static final Color FLORAL_WHITE           = new Color(255, 250, 240);
    public static final Color IVORY                  = new Color(255, 255, 240);
    public static final Color ANTIQUE_WHITE          = new Color(250, 235, 215);
    public static final Color LINEN                  = new Color(250, 240, 230);
    public static final Color LAVENDER_BLUSH         = new Color(255, 240, 245);
    public static final Color MISTY_ROSE             = new Color(255, 228, 225);

    public static final Color GAINSBORO              = new Color(220, 220, 220);
    public static final Color LIGHT_GRAY             = new Color(211, 211, 211);
    public static final Color SILVER                 = new Color(192, 192, 192);
    public static final Color DARK_GRAY              = new Color(169, 169, 169);
    public static final Color GRAY                   = new Color(128, 128, 128);
    public static final Color DIM_GRAY               = new Color(105, 105, 105);
    public static final Color LIGHT_SLATE_GRAY       = new Color(119, 136, 153);
    public static final Color SLATE_GRAY             = new Color(112, 128, 144);
    public static final Color DARK_SLATE_GRAY        = new Color( 47,  79,  79);
    public static final Color BLACK                  = Color.BLACK;

}