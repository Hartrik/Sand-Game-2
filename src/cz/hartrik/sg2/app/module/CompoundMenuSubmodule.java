package cz.hartrik.sg2.app.module;

import cz.hartrik.sg2.app.Application;
import java.util.Arrays;
import javafx.scene.control.MenuItem;

/**
 * Obalí několik <i>menu modulů</i> do jednoho, tím vznikne jeden modul.
 * Hlavní důvod ke sloučení několika <i>menu modulů</i> do jednoho je ten,
 * že mezi položkami v menu jednotlivých modulů nebudou oddělovače.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class CompoundMenuSubmodule
        implements MenuSubmodule, ApplicationCompoundModule {

    private final MenuSubmodule[] submodules;

    @SafeVarargs
    public CompoundMenuSubmodule(MenuSubmodule... submodules) {
        this.submodules = submodules;
    }

    @Override
    public MenuItem[] createMenuItems(Application application) {
        return Arrays.stream(submodules)
                .flatMap(m -> Arrays.stream(m.createMenuItems(application)))
                .toArray(MenuItem[]::new);
    }

    @Override
    public Object[] getSubModules() {
        return submodules;
    }

}