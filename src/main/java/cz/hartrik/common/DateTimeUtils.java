package cz.hartrik.common;

import cz.hartrik.common.reflect.LibraryClass;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Poskytuje statické metody pro zjednodušení práce s datem a časem.
 * 
 * @version 2015-04-04
 * @author Patrik Harag
 */
@LibraryClass
public final class DateTimeUtils {

    private DateTimeUtils() { }
    
    /** Formát ve tvaru <code>yyyy-MM-dd HH-mm-ss</code> */
    public static final DateTimeFormatter TIMESTAMP_FORMATTER =
            new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral(" ")
                .appendPattern("HH-mm-ss").toFormatter();
    
    /**
     * Vrátí aktuální čas ve tvaru <code>yyyy-MM-dd HH-mm-ss</code>
     * 
     * @return formátovaný čas
     */
    public static String timestamp() {
        return TIMESTAMP_FORMATTER.format(ZonedDateTime.now());
    }
    
}