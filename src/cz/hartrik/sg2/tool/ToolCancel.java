package cz.hartrik.sg2.tool;

import cz.hartrik.common.Pair;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.brush.Controls;

/**
 * Při prvním zavolání metody {@link Tool#apply(int, int, Inserter) apply}
 * nastaví předchozí nástroje.
 * 
 * @version 2014-12-27
 * @author Patrik Harag
 */
public class ToolCancel implements Tool {
    
    private final Tool previousPrimary;
    private final Tool previousSecondary;
    private final Controls controls;

    public ToolCancel(Tool previousPrimary, Tool previousSecondary,
            Controls controls) {

        this.previousPrimary = previousPrimary;
        this.previousSecondary = previousSecondary;
        
        this.controls = controls;
    }
    
    @Override
    public void apply(int mX, int mY, BrushInserter<?> inserter) {
        controls.setPrimaryTool(previousPrimary);
        controls.setSecondaryTool(previousSecondary);
    }

    protected Pair<Tool, Tool> getPreviousPair() {
        return Pair.of(previousPrimary, previousSecondary);
    }
    
}