
package cz.hartrik.sg2.app.module.frame.module.edit;

import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.Registerable;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.brush.jfx.JFXControls;
import cz.hartrik.sg2.world.Chunk;
import cz.hartrik.sg2.world.Element;

import static cz.hartrik.sg2.app.module.frame.module.edit.EditServices.*;

/**
 * @version 2015-03-07
 * @author Patrik Harag
 */
public class InsertServices implements Registerable {
    
    protected final FrameController controller;

    public InsertServices(FrameController controller) {
        this.controller = controller;
    }
    
    // pomocné metody
    
    protected void fill(Brush brush, Controls controls) {
        controller.getSyncTools().synchronize(
                area -> area.getTools().fill(brush, controls));
    }
    
    protected void boundWorld(Brush brush, Controls controls) {
        controller.getSyncTools().synchronize((area) -> {
            
            final int maxY = area.getHeight() - 1;
            final int maxX = area.getWidth() - 1;
            
            // horní a dolní okraj
            for (int x = 0; x < area.getWidth(); x++) {
                setAt(x, 0, brush, controls);
                setAt(x, maxY, brush, controls);
            }
            
            // po stranách
            for (int y = 0; y < maxY; y++) {
                setAt(0, y, brush, controls);
                setAt(maxX, y, brush, controls);
            }
            area.setAllChunks(true);
        });
    }
    
    protected void boundChunks(Brush brush, Controls controls) {
        controller.getSyncTools().synchronize((area) -> {
            
            // horizontální
            for (int x = 0; x < area.getWidth(); x++) {
                for (int y = 0; y < area.getHeight(); y += Chunk.CHUNK_SIZE) {
                    setAt(x, y, brush, controls);
                    setAt(x, y + Chunk.CHUNK_SIZE - 1, brush, controls);
                }
            }
            
            // vertiální
            for (int y = 0; y < area.getHeight(); y++) {
                for (int x = 0; x < area.getWidth(); x += Chunk.CHUNK_SIZE) {
                    setAt(x, y, brush, controls);
                    setAt(x + Chunk.CHUNK_SIZE - 1, y, brush, controls);
                }
            }
            
            area.setAllChunks(true);
        });
    }
    
    private void setAt(int x, int y, Brush brush, Controls controls) {
        Element element = brush.isAdvanced()
                ? brush.getElement(null, x, y, controller.getWorld(), controls)
                : brush.getElement();
        if (element != null) controller.getWorld().set(x, y, element);
    }
    
    // služby
    
    public void clear() {
        fill(controller.getBrushManager().getBrush(1), controller.getControls());
    }
    
    public void fill()  {
        fill(controller.getControls().getPrimaryBrush(), controller.getControls());
    }
    
    public void wBounds() {
        Brush brush = controller.getBrushManager().getBrush(202);
        boundWorld(brush, controller.getControls());
    }
    
    public void wBoundsCustom() {
        JFXControls controls = controller.getControls();
        boundWorld(controls.getPrimaryBrush(), controls);
    }
    
    public void cBounds() {
        Brush brush = controller.getBrushManager().getBrush(10);
        boundChunks(brush, controller.getControls());
    }
    
    public void cBoundsCustom() {
        JFXControls controls = controller.getControls();
        boundChunks(controls.getPrimaryBrush(), controls);
    }
    
    // registrační metoda
    
    @Override
    public void register(ServiceManager manager) {
        manager.register(SERVICE_EDIT_CLEAR, this::clear);
        manager.register(SERVICE_EDIT_FILL, this::fill);
        manager.register(SERVICE_EDIT_BOUNDS_BH, this::wBounds);
        manager.register(SERVICE_EDIT_BOUNDS, this::wBoundsCustom);
        manager.register(SERVICE_EDIT_BOUNDS_CHUNK_W, this::cBounds);
        manager.register(SERVICE_EDIT_BOUNDS_CHUNK, this::cBoundsCustom);
    }
    
}