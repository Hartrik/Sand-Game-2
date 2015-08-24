
package cz.hartrik.sg2.brush.manage;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Načte data o štětcích a poté je vrací na základě id.
 * 
 * @version 2014-05-07
 * @author Patrik Harag
 */
public class BrushInfoLoader {
    
    protected final List<BrushInfo> informations;
    
    public BrushInfoLoader(String resource) {
        ResourceBundle bundle = ResourceBundle.getBundle(resource);
        informations = new ResourceBundleParser(bundle).parse();
    }
    
    public BrushInfo get(int id) {
        for (BrushInfo info : informations)
            if (info.getId() == id) return info;
        
        return new BrushInfo(id, "");
    }
    
}