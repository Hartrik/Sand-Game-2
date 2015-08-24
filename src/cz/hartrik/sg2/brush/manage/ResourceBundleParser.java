
package cz.hartrik.sg2.brush.manage;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Parsuje popisy elementů.
 * 
 * @version 2015-01-10
 * @author Patrik Harag
 */
public class ResourceBundleParser {
    
    private final ResourceBundle bundle;
    private List<BrushInfo> informations;

    public ResourceBundleParser(ResourceBundle bundle) {
        this.bundle = bundle;
    }
    
    public List<BrushInfo> parse() {
        informations = new ArrayList<>();
        
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String nextKey = keys.nextElement();
            parse(nextKey);
        }
        
        return informations;
    }
    
    protected void parse(final String key) {
        final int dIndex = key.indexOf('.');
        if (dIndex < 1 || (dIndex - 1) == key.length()) return;

        int id;
        try {
            id = Integer.parseInt(key.substring(0, dIndex));
        } catch (NumberFormatException e) { return; }
        
        final BrushInfo brushInfo = get(id);
        final String type = key.substring(dIndex + 1);
        final String text = bundle.getString(key);
        
        switch (type) {
            case "name": brushInfo.setName(text); break;
            case "labels": parseLabels(brushInfo, text); break;
            case "aliases": parseAliases(brushInfo, text); break;
            case "description": brushInfo.setDescription(text); break;
        }
    }
    
    private void parseAliases(BrushInfo brushInfo, String text) {
        String[] aliases = parseList(text);
        for (String alias : aliases)
            brushInfo.addAlias(alias);
    }
    
    private void parseLabels(BrushInfo brushInfo, String text) {
        String[] labels = parseList(text);
        for (String label : labels)
            brushInfo.addLabel(label);
    }
    
    private String[] parseList(String text) {
        String[] split = text.split(",");
        for (int i = 0; i < split.length; i++)
            split[i] = split[i].trim();
        
        return split;
    }
    
    protected BrushInfo get(int id) {
        for (BrushInfo description : informations)
            if (description.getId() == id) return description;
        
        BrushInfo description = new BrushInfo(id);
        informations.add(description);
        return description;
    }
    
}