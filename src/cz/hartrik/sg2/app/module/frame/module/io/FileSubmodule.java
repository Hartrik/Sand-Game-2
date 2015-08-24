
package cz.hartrik.sg2.app.module.frame.module.io;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.io.IOManager;
import cz.hartrik.sg2.world.ModularWorld;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.stage.Window;

import static cz.hartrik.sg2.app.module.frame.module.io.IOServices.*;

/**
 * <b> Raději ještě před zobrazením okna je nutné nechat vytvořit
 * nový soubor! </b>
 * 
 * @version 2015-02-14
 * @author Patrik Harag
 */
public class FileSubmodule extends MenuSubmodule<Frame, FrameController> {
    
    private final IOManager<ModularWorld, Window> ioManager;
    
    public FileSubmodule(boolean register,
            IOManager<ModularWorld, Window> ioManager) {
        
        super(register);
        this.ioManager = ioManager;
    }

    @Override
    public void register(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        new FileServices(ioManager, controller).register(manager);
    }
    
    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        ioManager.setContext(stage);
        
        MenuItem iNew = new MenuItem("Nový", img("icon - new file.png"));
        iNew.setOnAction((e) -> manager.run(SERVICE_FILE_NEW));
        iNew.setAccelerator(KeyCombination.valueOf("ctrl+n"));
        
        MenuItem iOpen = new MenuItem("Otevřít", img("icon - open.png"));
        iOpen.setOnAction((e) -> manager.run(SERVICE_FILE_OPEN));
        iOpen.setAccelerator(KeyCombination.valueOf("ctrl+o"));
        
        MenuItem iSave = new MenuItem("Uložit", img("icon - save.png"));
        iSave.setOnAction((e) -> manager.run(SERVICE_FILE_SAVE));
        iSave.setAccelerator(KeyCombination.valueOf("ctrl+s"));
        
        MenuItem iSaveAs = new MenuItem("Uložit jako", img("icon - save.png"));
        iSaveAs.setOnAction((e) -> manager.run(SERVICE_FILE_SAVE_AS));
        iSaveAs.setAccelerator(KeyCombination.valueOf("ctrl+shift+s"));
        
        return new MenuItem[] { iNew, iOpen, iSave, iSaveAs };
    }
    
    private ImageView img(String fileName) {
        return new ImageView(Resources.image(fileName, getClass()));
    }
    
}