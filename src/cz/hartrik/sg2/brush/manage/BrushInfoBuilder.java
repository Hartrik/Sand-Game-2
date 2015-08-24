package cz.hartrik.sg2.brush.manage;

/**
 * @version 2015-01-10
 * @author Patrik Harag
 */
public class BrushInfoBuilder {

    private final BrushInfo brushInfo;
    
    public BrushInfoBuilder(int id) {
        this.brushInfo = new BrushInfo(id);
    }
    
    public BrushInfoBuilder setName(String name) {
        brushInfo.setName(name);
        return this;
    }
    
    public BrushInfoBuilder setDescription(String description) {
        brushInfo.setDescription(description);
        return this;
    }
    
    public BrushInfoBuilder addAlias(String alias) {
        brushInfo.addAlias(alias);
        return this;
    }
    
    public BrushInfoBuilder addLabel(String label) {
        brushInfo.addLabel(label);
        return this;
    }
    
    public BrushInfo build() {
        return brushInfo;
    }
    
}