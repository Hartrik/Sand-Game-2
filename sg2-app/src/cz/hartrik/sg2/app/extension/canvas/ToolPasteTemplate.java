package cz.hartrik.sg2.app.extension.canvas;

import cz.hartrik.sg2.engine.JFXPlatform;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.world.template.Template;

/**
 * Nástroj, který nanáší šablony.
 * 
 * @version 2017-08-06
 * @author Patrik Harag
 */
public class ToolPasteTemplate implements Cursorable {

    private final Template template;

    public ToolPasteTemplate(Template template) {
        this.template = template;
    }

    @Override
    public Cursor createCursor(CanvasWithCursor canvas) {
        return new ImageCursor(canvas, JFXPlatform.asJFXImage(template.getImage()));
    }

    @Override
    public void apply(int mX, int mY, BrushInserter<?> inserter) {
        int x = mX - template.getWidth() / 2;
        int y = mY - template.getHeight() / 2;
        
        template.insert(inserter, x, y);
    }

}