package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.common.Counter;
import cz.hartrik.sg2.app.sandbox.element.ElementList;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import java.io.Serializable;
import java.util.function.IntFunction;

/**
 * @version 2014-12-31
 * @author Patrik Harag
 */
public class SaltWater extends Water implements ESalty {

    private static final long serialVersionUID = 83715083867368_02_082L;
    
    private int salt = 0_500;

    public SaltWater(Color color, int density) {
        super(color, density);
    }

    public SaltWater(Color color, int density, int temperature) {
        super(color, density, temperature);
    }

    @Override
    protected void vapor(int x, int y, Tools tools, World world) {
        Counter counter = new Counter(salt);
        
        tools.getDirectionVisitor().visitWhileAll(x, y, (e) -> {
            if (e instanceof ESalty) {
                final ESalty salty = ((ESalty) e);
                
                final int s1 = counter.getValue();
                final int s2 = salty.getSalt();
                final int toFill = 100_000 - s2;
                
                if (toFill >= s1) {
                    salty.setSalt(s2 + s1);
                    return false;
                } else {
                    counter.decrease(toFill);
                    salty.setSalt(100_000);
                    return true;
                }
            }
            return true;
        });
        
        world.setAndChange(x, y,
            new Steam(STEAM_COLOR, -101, STEAM_CHANCE, getTemperature(),
                (IntFunction<Water> & Serializable)
                (t) -> {
                    SaltWater w = new SaltWater(getBaseColor(), getDensity(), t);
                    w.setSalt(0);
                    return w;
                }
            )
        );
    }
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (salt >= 55_000) {
            SaltDry saltDry = new SaltDry(ElementList.SALT_COLORS.get(), 2000);
            saltDry.setSalt(salt);
            world.setAndChange(x, y, saltDry);
        } else {
            super.doAction(x, y, tools, world);
        }
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
    public void setSalt(int salt) {
        this.salt = salt;
    }

    @Override
    public int getSalt() {
        return salt;
    }

}