package cz.hartrik.sg2.tool;

import cz.hartrik.common.Pair;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.brush.Controls.Button;
import cz.hartrik.sg2.world.template.TemplateWPreview;

/**
 * Jednou vloží šablonu, poté nastaví předešlý nástroj.
 * 
 * @version 2014-12-27
 * @author Patrik Harag
 */
public class ToolPasteTemplateOnce extends ToolPasteTemplate {
    
    private final Tool previous;
    private final Controls controls;
    private final Controls.Button button;

    public ToolPasteTemplateOnce(TemplateWPreview template,
            Tool previous, Controls controls, Controls.Button button) {
        
        super(template);
        
        this.previous = previous;
        this.controls = controls;
        this.button = button;
    }
    
    @Override
    public void apply(int mX, int mY, BrushInserter<?> inserter) {
        super.apply(mX, mY, inserter);
        
        controls.setTool(button, previous);
    }

    public Tool getPreviousTool() {
        return previous;
    }
    
    // pomocné statické metody
    
    public static void init(TemplateWPreview template, Controls controls) {
        init(template, controls, Controls.Button.PRIMARY);
    }
    
    public static void init(TemplateWPreview template, Controls controls, Button button) {
        ToolPasteTemplateOnce tool = new ToolPasteTemplateOnce(
                template, controls.getTool(button), controls, button);
        
        controls.setTool(button, tool);
    }
    
    public static void initWithCancel(TemplateWPreview template, Controls controls) {
        final Pair<Tool, Tool> prv = unwrap(
                controls.getPrimaryTool(), controls.getSecondaryTool());
        
        final ToolPasteTemplateOnce tool = new ToolPasteTemplateOnce(
                template, prv.getFirst(), controls, Button.PRIMARY) {

            @Override
            public void apply(int mX, int mY, BrushInserter<?> inserter) {
                super.apply(mX, mY, inserter);
                controls.setSecondaryTool(prv.getSecond());
            }
        };
        
        final ToolCancel toolCancel = new ToolCancel(
                prv.getFirst(), prv.getSecond(), controls);
        
        controls.setPrimaryTool(tool);
        controls.setSecondaryTool(toolCancel);
    }
    
    private static Pair<Tool, Tool> unwrap(Tool prevP, Tool prevS) {
        if (prevP instanceof ToolPasteTemplateOnce) {
            if (prevS instanceof ToolCancel)
                return ((ToolCancel) prevS).getPreviousPair();

            ToolPasteTemplateOnce template = (ToolPasteTemplateOnce) prevP;
            return Pair.of(template.getPreviousTool(), prevS);
        }
        return Pair.of(prevP, prevS);
    }
    
}