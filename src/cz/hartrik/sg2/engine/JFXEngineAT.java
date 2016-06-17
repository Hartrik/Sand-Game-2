package cz.hartrik.sg2.engine;

import cz.hartrik.sg2.world.World;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

/**
 * Typ enginu využívající k vykreslování {@link AnimationTimer}. Snímková
 * frekvence se nastaví automaticky podle typu systému (obvykle 60 FPS).
 * Snímkovou frekvenci je možné měnit, ale není to doporučeno.
 *
 * @version 2014-12-15
 * @author Patrik Harag
 * @param <P> typ procesoru
 */
public class JFXEngineAT<P extends Processor> extends JFXEngine<P> {

    public static final int FPS_AUTO = -1;

    private boolean rStopped;
    private long rLast;
    private int rSleep = FPS_AUTO;
    protected final AnimationTimer rAnimation;

    public JFXEngineAT(World world, P processor, JFXRenderer renderer,
            ImageView view) {

        super(world, processor, renderer, view);

        rAnimation = new RendererAnimation();
    }

    // renderer

    protected class RendererAnimation extends AnimationTimer {
        @Override
        public void handle(long now) {
            if (isRendererStopped()) return;

            if (rSleep != FPS_AUTO) {
                if ((now - rLast) < rSleep) return;
                rLast = now;
            }

            listener.rendererCycleStart();
            renderer.updateBuffer();

            mainWriter.setPixels(
                    0, 0, world.getWidth(), world.getHeight(),
                    renderer.getPixelFormat(), renderer.getBuffer(), 0,
                    (world.getWidth() * 4));

            rCounter.tick();
            listener.rendererCycleEnd();
        }
    }
    
    @Override
    public synchronized void rendererStart() {
        rStopped = false;
        rAnimation.start();
    }

    @Override
    public synchronized boolean isRendererStopped() {
        return rStopped;
    }

    @Override
    public synchronized void rendererStop() {
        rStopped = true;
        rAnimation.stop();
    }

    @Override @Deprecated
    public void setMaxFPS(int fps) {
        rSleep = (fps == 60 || fps == FPS_AUTO)
                ? FPS_AUTO
                : 1_000_000_000 / fps;
    }

}