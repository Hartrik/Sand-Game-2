package cz.hartrik.sg2.brush.manage;

import java.util.function.Function;
import java.util.function.IntUnaryOperator;

/**
 * Správce štětců podporující zpětnou kompatibilitu ID štětců.
 *
 * @version 2015-03-13
 * @author Patrik Harag
 */
public class ObservedBrushManagerBC extends ObservedBrushManager
        implements BackwardCompatible {

    private final String latestVersion;
    private final Function<String, IntUnaryOperator> provider;

    public ObservedBrushManagerBC(
            String version,
            Function<String, IntUnaryOperator> provider) {

        this.latestVersion = version;
        this.provider = provider;
    }

    @Override
    public IntUnaryOperator getConvertor(String version){
        return provider.apply(version);
    }

    @Override
    public String getLatestVersion() {
        return latestVersion;
    }

}