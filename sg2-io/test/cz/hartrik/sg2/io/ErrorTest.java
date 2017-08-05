package cz.hartrik.sg2.io;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 *
 *
 * @author Patrik Harag
 * @version 2017-08-05
 */
@RunWith(Parameterized.class)
public class ErrorTest {

    private static Path dir = Paths.get("test-resources");
    private static IOProvider<?> ioProvider = TestUtils.createIoProvider();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "error - empty file.sgb", IOException.class },

                { "error - chunk size not match.sgb", ParseException.class },
                { "error - missing canvas size.sgb", ParseException.class },
                { "error - missing chunk size.sgb", ParseException.class },
                { "error - negative canvas width.sgb", ParseException.class },
        });
    }

    private final Path file;
    private final Class<?> expectedException;

    public ErrorTest(String name, Class<?> exceptionClass) {
        this.file = dir.resolve(name);
        this.expectedException = exceptionClass;
    }

    @Test
    public void test() throws IOException {
        FileTypeIOProvider<?> provider = ioProvider.getProvider(file).get();

        Exception exception = null;
        try {
            provider.load(file);
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, instanceOf(expectedException));
    }

}
