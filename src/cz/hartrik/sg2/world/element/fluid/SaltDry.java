package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.powder.PowderMidTC;

/**
 * @version 2014-12-31
 * @author Patrik Harag
 */
public class SaltDry extends PowderMidTC implements ESalty {

    private static final long serialVersionUID = 83715083867368_02_083L;

    private int salt = 100_000;

    public SaltDry(Color color, int density) {
        super(color, density);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (salt < 55_000) {
            SaltWater saltWater = new SaltWater(new Color(0, 0, 178), 1000);
            saltWater.setSalt(salt);
            world.setAndChange(x, y, saltWater);
        } else
            super.doAction(x, y, tools, world);
    }
    
    @Override
    public void doParallel(int x, int y, Tools tools, World world) {
        if (tools.randomInt(2) != 0) return;
        
        tools.getDirectionVisitor().visit(x, y, (element, eX, eY) -> {
            
            if (element instanceof ESalty) {
                final ESalty salty = ((ESalty) element);
                
                final int s1 = getSalt();
                final int s2 = salty.getSalt();
                
                if (Math.abs(s1 - s2) > 1) {
                    final int ave = (s1 + s2) / 2;
                    
                    setSalt(ave);
                    world.getChunkAt(x, y).change();
                    
                    salty.setSalt(ave);
                    world.getChunkAt(eX, eY).change();
                }
            }
        }, tools.randomDirection());
    }

    @Override
    public float getConductiveIndex() {
        return 0.3f;
    }

    @Override
    public void setSalt(int salt) {
        this.salt = salt;
    }

    @Override
    public int getSalt() {
        return salt;
    }

    @Override
    public Color getColor() {
        if (getSalt() < 95_000)
            return Color.GRAY;
        else
            return super.getColor();
    }
    
}