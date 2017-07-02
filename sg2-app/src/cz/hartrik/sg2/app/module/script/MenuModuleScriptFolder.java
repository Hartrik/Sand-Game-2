package cz.hartrik.sg2.app.module.script;

import cz.hartrik.common.Exceptions;
import cz.hartrik.common.io.Resources;
import cz.hartrik.common.io.SimpleDirWatcher;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.MenuModule;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.engine.ThreadFactoryName;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Modul přidá menu se scripty. Obsahuje aktualizovaný seznam scriptů z vybrané
 * složky. Stále je však možné přidávat sub-moduly pro další položky.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class MenuModuleScriptFolder extends MenuModule {

    private static final ThreadFactory THREAD_FACTORY
            = new ThreadFactoryName("SG2 - watch service");

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
    public void init(Application app) {
        if (!Files.exists(scriptFolder)) return;

        this.executer = new JSExecuter(app);
        this.menu = new Menu(text);
        this.modules = modules(app);

        menu.getItems().addAll(modules);
        updateMenu();
        app.getController().getMenuBar().getMenus().add(menu);

        Thread thread = THREAD_FACTORY.newThread(this::initWatchService);
        thread.start();
    }

    private List<MenuItem> modules(Application application) {
        List<MenuItem> items = new ArrayList<>();
        for (MenuSubmodule mod : submodules) {
            MenuItem[] menuItems = mod.createMenuItems(application);
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