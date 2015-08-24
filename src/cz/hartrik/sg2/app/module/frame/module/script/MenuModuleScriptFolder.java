package cz.hartrik.sg2.app.module.frame.module.script;

import cz.hartrik.common.io.SimpleDirWatcher;
import cz.hartrik.common.Exceptions;
import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuModule;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @version 2015-03-21
 * @author Patrik Harag
 */
public class MenuModuleScriptFolder extends MenuModule<Frame, FrameController> {
    
    private final Path scriptFolder;
    
    private Menu menu;
    private List<MenuItem> modules;
    private JSExecuter executer;

    
    private final Image iconFolder;
    private final Image iconJS;
    
    public MenuModuleScriptFolder(String label, Path scriptFolder) {
        super(label);
        this.scriptFolder = scriptFolder;
        
        this.iconJS = Resources.image("icon - js.png", getClass());
        this.iconFolder = Resources.image("icon - folder.png", getClass());
    }

    @Override
    public void init(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        if (!Files.exists(scriptFolder)) return;
        
        this.executer = new JSExecuter(stage, controller);
        this.menu = new Menu(text);
        this.modules = modules(stage, controller, manager);
        
        menu.getItems().addAll(modules);
        updateMenu();
        controller.getMenuBar().getMenus().add(menu);
        
        new Thread(this::initWatchService).start();
    }
    
    private List<MenuItem> modules(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        List<MenuItem> items = new ArrayList<>();
        for (MenuSubmodule<Frame, FrameController> mod : submodules) {
            MenuItem[] menuItems = mod.init(stage, controller, manager);
            items.addAll(Arrays.asList(menuItems));
            items.add(new SeparatorMenuItem());
        }
        return items;
    }
    
    private void updateMenu() {
        menu.getItems().retainAll(modules);
        
        walk(scriptFolder, menu);
    }
    
    private void walk(Path folder, Menu menu) {
        Exceptions.uncheckedApply(Files::list, folder)
                .sorted(FileDictionaryComparator::comparePath)
                .forEach(path -> {

            if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                Menu submenu = new Menu(path.getFileName().toString());
                submenu.setGraphic(new ImageView(iconFolder));
                menu.getItems().add(submenu);

                walk(path, submenu);

            } else if (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) {
                MenuItem menuItem = createMenuItem(path);
                if (menuItem != null)
                    menu.getItems().add(menuItem);
            }
        });
    }
    
    private MenuItem createMenuItem(Path path) {
        final String fileName = path.getFileName().toString();
        
        if (fileName.endsWith(".js")) {
            MenuItem item = new MenuItem(fileName);
            item.setGraphic(new ImageView(iconJS));
            
            // bohužel nefunguje - vše je action event
            
//            item.addEventHandler(MouseEvent.ANY, (MouseEvent me) -> {
//                System.out.println("me = " + me);
//                
//                if (me.getButton() == MouseButton.SECONDARY) {
//                    me.consume();
//                    
//                    try {
//                        Desktop.getDesktop().edit(path.toFile());
//                    } catch (IOException ex) {
//                        System.out.println("ex = " + ex);
//                    }
//                }
//            });
            
            item.setOnAction((event) -> executer.eval(path));
            
            return item;
        }
        return null;
    }
    
    private void initWatchService() {
        Exceptions.unchecked(() -> {
            SimpleDirWatcher watchDir = new SimpleDirWatcher(scriptFolder, true);
            watchDir.addListener(() -> Platform.runLater(this::updateMenu));
            watchDir.processEvents();
        });
    }
    
}