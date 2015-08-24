package cz.hartrik.sg2.app.module.frame.module;

import java.util.Arrays;
import javafx.scene.control.MenuItem;

/**
 * Obalí několik <i>menu modulů</i> do jednoho, tím vznikne jeden modul.
 * Hlavní důvod ke sloučení několika <i>menu modulů</i> do jednoho je ten,
 * že mezi položkami v menu jednotlivých modulů nebudou oddělovače.
 * 
 * @version 2014-11-29
 * @author Patrik Harag
 * @param <T> stage
 * @param <U> controller
 */
public class CompoundMenuSubmodule<T, U> extends MenuSubmodule<T, U> {
    
    private final MenuSubmodule<T, U>[] submodules;

    @SafeVarargs
    public CompoundMenuSubmodule(MenuSubmodule<T, U>... submodules) {
        super(true);
        this.submodules = submodules;
    }

    @Override
    public MenuItem[] createMenuItems(T stage, U controller, ServiceManager manager) {
        return Arrays.stream(submodules)
                .flatMap(submodule -> Arrays.stream(
                        submodule.createMenuItems(stage, controller, manager)))
                .toArray(MenuItem[]::new);
    }

    @Override
    public void register(T stage, U controller, ServiceManager manager) {
        for (MenuSubmodule<T, U> submodule : submodules)
            if (submodule.register)
                submodule.register(stage, controller, manager);
    }
    
}