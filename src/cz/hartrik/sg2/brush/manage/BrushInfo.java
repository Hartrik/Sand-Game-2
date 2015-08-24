
package cz.hartrik.sg2.brush.manage;

import cz.hartrik.common.Checker;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Popis štětce.
 * 
 * @version 2015-01-10
 * @author Patrik Harag
 */
public class BrushInfo {
    
    private final int id;
    private String name;
    private String description;
    private final Set<String> labels;
    private final Set<String> aliases;

    public BrushInfo(int id) {
        this(id, "");
    }
    
    public BrushInfo(int id, String name) {
        this(id, name, "");
    }
    
    public BrushInfo(int id, String name, String description) {
        this.id = id;
        this.name = Checker.requireNonNull(name);
        this.description = Checker.requireNonNull(description);
        this.labels = new TreeSet<>();
        this.aliases = new TreeSet<>();
    }

    // --- přístupové metody
    
    // id
    
    public int getId() {
        return id;
    }

    // name
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Checker.requireNonNull(name);
    }
    
    // description
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Checker.requireNonNull(description);
    }
    
    // labels
    
    public void addLabel(String label) {
        labels.add(label);
    }
    
    public boolean containsLabel(String label) {
        return labels.contains(label);
    }
    
    public Set<String> getLabels() {
       return Collections.unmodifiableSet(labels);
    }
    
    // aliases
    
    public void addAlias(String alias) {
        aliases.add(alias);
    }
    
    public boolean containsAlias(String alias) {
        return aliases.contains(alias);
    }
    
    public Set<String> getAliases() {
       return Collections.unmodifiableSet(aliases);
    }
    
}