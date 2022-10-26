
package cz.hartrik.common;

import java.io.Serializable;

/**
 * Klasická ARGB barva podporující serializaci. Jednotlivé složky barvy jsou
 * ukládány v datovém typu <code>byte</code>.
 * Předdefinované barvy odpovídají těm z {@link java.awt.Color}.
 * 
 * @version 2014-12-18
 * @author Patrik Harag
 */
public final class Color implements Serializable {
    
    private static final long serialVersionUID = 10411599111108_10_001L;
    
    public static final Color WHITE      = new Color(255, 255, 255);
    public static final Color LIGHT_GRAY = new Color(192, 192, 192);
    public static final Color GRAY       = new Color(128, 128, 128);
    public static final Color DARK_GRAY  = new Color( 64,  64,  64);
    public static final Color BLACK      = new Color(  0,   0,   0);
    public static final Color RED        = new Color(255,   0,   0);
    public static final Color PINK       = new Color(255, 175, 175);
    public static final Color ORANGE     = new Color(255, 200,   0);
    public static final Color YELLOW     = new Color(255, 255,   0);
    public static final Color GREEN      = new Color(  0, 255,   0);
    public static final Color MAGENTA    = new Color(255,   0, 255);
    public static final Color CYAN       = new Color(  0, 255, 255);
    public static final Color BLUE       = new Color(  0,   0, 255);
    
    private final byte r, g, b, a;
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktory">

    // --- byte ---
    
    public Color(byte r, byte g, byte b) {
        this(r, g, b, 0xFF);
    }
    
    public Color(byte r, byte g, byte b, byte a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    // --- int ---
    
    public Color(int r, int g, int b) {
        this(r, g, b, 0xFF);
    }
    
    public Color(int r, int g, int b, int a) {
        this.r = (byte) r;
        this.g = (byte) g;
        this.b = (byte) b;
        this.a = (byte) a;
    }
    
    // --- float ---
    
    public Color(float r, float g, float b) {
        this(r, g, b, 1f);
    }
    
    public Color(float r, float g, float b, float a) {
        this.r = (byte) (r * 0xFF + 0.5);
        this.g = (byte) (g * 0xFF + 0.5);
        this.b = (byte) (b * 0xFF + 0.5);
        this.a = (byte) (a * 0xFF + 0.5);
    }
    
    // --- double ---
    
    public Color(double r, double g, double b) {
        this(r, g, b, 1d);
    }
    
    public Color(double r, double g, double b, double a) {
        this.r = (byte) (r * 0xFF + 0.5);
        this.g = (byte) (g * 0xFF + 0.5);
        this.b = (byte) (b * 0xFF + 0.5);
        this.a = (byte) (a * 0xFF + 0.5);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Tovární metody">
    
    public static Color createARGB(int argb) {
        return new Color(
                (argb >> 16) & 0xFF,
                (argb >>  8) & 0xFF,
                 argb        & 0xFF,
                (argb >> 24) & 0xff);
    }
    
    public static Color createRGB(int rgb) {
        return createARGB(0xFF000000 | rgb);
    }
    
    public static Color createGray(int gray) {
        return new Color(gray, gray, gray);
    }
    
    public static Color createGray(int gray, int alpha) {
        return new Color(gray, gray, gray, alpha);
    }
    
    public static Color createGray(double gray) {
        byte bGray = (byte) (gray * 0xFF + 0.5);
        return new Color(bGray, bGray, bGray);
    }
    
    public static Color createGray(double gray, double alpha) {
        byte bGray  = (byte) (gray  * 0xFF + 0.5);
        byte bAlpha = (byte) (alpha * 0xFF + 0.5);
        return new Color(bGray, bGray, bGray, bAlpha);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gettery">
    public int getARGB() {
        return  ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF));
    }
    
    public int getRGBA() {
        return  ((r & 0xFF) << 24) |
                ((g & 0xFF) << 16) |
                ((b & 0xFF) << 8)  |
                ((a & 0xFF));
    }
    
    public int getRed()   { return r & 0xFF; }
    public int getGreen() { return g & 0xFF; }
    public int getBlue()  { return b & 0xFF; }
    public int getAlpha() { return a & 0xFF; }
    
    public byte getByteRed()   { return r; }
    public byte getByteGreen() { return g; }
    public byte getByteBlue()  { return b; }
    public byte getByteAlpha() { return a; }
    
    public float getFloatRed()   { return (r & 0xFF) / 255.f; }
    public float getFloatGreen() { return (g & 0xFF) / 255.f; }
    public float getFloatBlue()  { return (b & 0xFF) / 255.f; }
    public float getFloatAlpha() { return (a & 0xFF) / 255.f; }
    
    public double getDoubleRed()   { return (r & 0xFF) / 255.f; }
    public double getDoubleGreen() { return (g & 0xFF) / 255.f; }
    public double getDoubleBlue()  { return (b & 0xFF) / 255.f; }
    public double getDoubleAlpha() { return (a & 0xFF) / 255.f; }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Úpravy">
    public Color changeAlpha(byte alpha) {
        return new Color(r, g, b, alpha);
    }
    
    public Color changeAlpha(int alpha) {
        return new Color(r, g, b, (byte) alpha);
    }
    
    public Color changeAlpha(float alpha) {
        return new Color(r, g, b, (byte) (alpha * 0xFF + 0.5));
    }
    
    public Color changeAlpha(double alpha) {
        return new Color(r, g, b, (byte) (alpha * 0xFF + 0.5));
    }
    
    public Color blend(Color foreground) {
        // outputRed = (foregroundRed * foregroundAlpha)
        //           + (backgroundRed * (1.0 - foregroundAlpha));
        
        final float fAlpha = foreground.getFloatAlpha();
        final float fAlphaReverse = 1.0f - fAlpha;
        
        int nR = (int) ((foreground.getRed() * fAlpha)
                + (getRed() * fAlphaReverse));
        int nG = (int) ((foreground.getGreen() * fAlpha)
                + (getGreen() * fAlphaReverse));
        int nB = (int) ((foreground.getBlue() * fAlpha)
                + (getBlue() * fAlphaReverse));
        
        return new Color(nR, nG, nB);
    }
    
    public Color grayscale() {
        double gray = 0.21 * getDoubleRed()
                + 0.71 * getDoubleGreen()
                + 0.07 * getDoubleBlue();
        
        return new Color(gray, gray, gray, getDoubleAlpha());
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metody objektu">
    /**
     * Vrací <i>hash code</i> barvy odpovídající metodě
     * {@link #getARGB() getARGB()}.
     * 
     * @return hash code
     */
    @Override
    public int hashCode() {
        return getARGB();
    }
    
    @Override
    public boolean equals(Object object) {
        return (object != null) && (object instanceof Color)
                && equals((Color) object);
    }
    
    public boolean equals(Color color) {
        return color.getARGB() == getARGB();
    }
    
    /**
     * Vrací textovou reprezentaci instance ve formátu
     * <code>rgba([r], [g], [b], [a])</code>.
     * 
     * @return textová reprezentace instance
     */
    @Override
    public String toString() {
        return new StringBuilder("rgba(")
                .append(getRed()).append(", ")
                .append(getGreen()).append(", ")
                .append(getBlue()).append(", ")
                .append(getAlpha()).append(")")
                .toString();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Převodní metody">
    public java.awt.Color toAWTColor() {
        return new java.awt.Color(getARGB(), true);
    }
    
    public static Color fromAWTColor(java.awt.Color color) {
        return Color.createARGB(color.getRGB());
    }
    
    public javafx.scene.paint.Color toJavaFXColor() {
        return javafx.scene.paint.Color.color(
                getDoubleRed(),
                getDoubleGreen(),
                getDoubleBlue(),
                getDoubleAlpha()
        );
    }
    
    public static Color fromJavaFXColor(javafx.scene.paint.Color color) {
        return new Color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                color.getOpacity()
        );
    }
    //</editor-fold>
    
}