package cz.hartrik.sg2.io;

import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.io.zip.ZipIO;
import cz.hartrik.sg2.io.zip.ZipIOBrushTemplate;
import cz.hartrik.sg2.io.zip.ZipIOSerial;
import cz.hartrik.sg2.io.zip.resource.ResourceTypeManager;
import cz.hartrik.sg2.world.BasicElement;
import cz.hartrik.sg2.world.ModularWorld;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 *
 * @author Patrik Harag
 * @version 2017-08-05
 */
class TestUtils {

    static IOProvider<ModularWorld> createIoProvider() {
        BrushManager brushManager = new BrushManager(null);

        Supplier<BrushManager> bmSupplier = () -> brushManager;

        ElementAreaProvider<ModularWorld> areaProvider =
                (w, h, size) -> new ModularWorld(w, h, size, BasicElement.AIR);

        ResourceTypeManager rtManager = new ResourceTypeManager(bmSupplier, 0);

        AppInfo appInfo = new AppInfo("test", "0");
        ZipIO<ModularWorld> serZipIO = new ZipIOSerial<>(appInfo, areaProvider, rtManager);
        ZipIO<ModularWorld> tmpZipIO = new ZipIOBrushTemplate<>(appInfo, areaProvider, rtManager);

        return new BasicIOProvider<>(Arrays.asList(serZipIO, tmpZipIO));
    }

}
