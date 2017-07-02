package cz.hartrik.sg2.app;

import cz.hartrik.common.reflect.TODO;
import cz.hartrik.sg2.app.service.ServiceManager;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.engine.EngineSyncTools;
import cz.hartrik.sg2.engine.JFXEngine;
import cz.hartrik.sg2.engine.Processor;
import cz.hartrik.sg2.world.ModularWorld;

/**
 *
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
@TODO("Převést sem funkcionalitu FrameController")
public class Application {

    private final Frame frame;
    private final FrameController controller;
    private final ServiceManager serviceManager;

    public Application(Frame frame, FrameController controller) {
        this.frame = frame;
        this.controller = controller;
        this.serviceManager = new ServiceManager(this);
    }

    public Frame getStage() {
        return frame;
    }

    public FrameController getController() {
        return controller;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public Controls getControls() {
        return controller.getControls();
    }

    public BrushManager getBrushManager() {
        return controller.getBrushManager();
    }

    public EngineSyncTools<ModularWorld> getSyncTools()  {
        return controller.getSyncTools();
    }

    public void setUpCanvas(ModularWorld world) {
        controller.setUpCanvas(world);
    }

    public void setUpCanvas(int w, int h) {
        controller.setUpCanvas(w, h);
    }

    public ModularWorld getWorld() {
        return controller.getWorld();
    }

    public JFXEngine<? extends Processor> getEngine() {
        return controller.getEngine();
    }

}