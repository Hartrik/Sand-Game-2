package cz.hartrik.sg2.io;

/**
 * Objekt uchovávající informace o aplikaci.
 *
 * @author Patrik Harag
 * @version 2017-07-19
 */
public class AppInfo {

    private final String name;
    private final String version;

    public AppInfo(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
