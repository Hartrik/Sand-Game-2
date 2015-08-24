package cz.hartrik.sg2.app.module.dialog.stats;

import java.util.Objects;

/**
 * Objekt uchovávající informace o jednom druhu elementu.
 * 
 * @version 2014-11-27
 * @author Patrik Harag
 */
public class ElementStats {

    // gettery jsou zde zbytečné...

    Class<?> clazz;
    int count;
    int instances;
    
    double countRate;
    double instancesRate;

    @Override
    public int hashCode() {
        return clazz.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        
        return Objects.equals(this.clazz, ((ElementStats) obj).clazz);
    }

}