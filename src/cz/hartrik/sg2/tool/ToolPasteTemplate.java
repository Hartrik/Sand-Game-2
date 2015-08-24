package cz.hartrik.sg2.tool;

import cz.hartrik.sg2.app.module.canvas.Cursor;
import cz.hartrik.sg2.app.module.canvas.Cursorable;
import cz.hartrik.sg2.app.module.canvas.ImageCursor;
import cz.hartrik.sg2.app.module.canvas.CanvasWithCursor;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.world.template.TemplateWPreview;

/**
 * Nástroj, který nanáší šablony.
 * 
 * @version 2015-01-11
 * @author Patrik Harag
 */
public class ToolPasteTemplate implements Cursorable {

    private final TemplateWPreview template;

    public ToolPasteTemplate(TemplateWPreview template) {
        this.template = template;
    }

    @Override
    public Cursor createCursor(CanvasWithCursor canvas) {
        return new ImageCursor(canvas, template.getImage());
    }

    @Override
    public void apply(int mX, int mY, BrushInserter<?> inserter) {
        int x = mX - template.getWidth() / 2;
        int y = mY - template.getHeight() / 2;
        
        template.insert(inserter, x, y);
    }

}