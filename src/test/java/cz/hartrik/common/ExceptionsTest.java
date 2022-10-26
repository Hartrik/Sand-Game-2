package cz.hartrik.common;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @version 2015-08-13
 * @author Patrik Harag
 */
public class ExceptionsTest {

    private static Void throwIO() throws IOException {
        throw new IOException();
    }

    // THROW

    // -- unchecked

    @Test(expected = UncheckedIOException.class)
    public void throw_UncheckedRunnable() {
        Exceptions.uncheckedRunnable(ExceptionsTest::throwIO).run();
    }

    @Test(expected = UncheckedIOException.class)
    public void throw_UncheckedSupplier() {
        Exceptions.uncheckedSupplier(ExceptionsTest::throwIO).get();
    }

    @Test(expected = UncheckedIOException.class)
    public void throw_UncheckedConsumer() {
        Exceptions.uncheckedConsumer(p -> throwIO()).accept(null);
    }

    @Test(expected = UncheckedIOException.class)
    public void throw_UncheckedFunction() {
        Exceptions.uncheckedFunction((p) -> throwIO()).apply(null);
    }

    // -- silent

    @Test()
    public void throw_SilentRunnable() {
        Exceptions.silentRunnable(ExceptionsTest::throwIO).run();
    }

    @Test()
    public void throw_SilentSupplier() {
        Exceptions.silentSupplier(ExceptionsTest::throwIO).get();
    }

    @Test()
    public void throw_SilentConsumer() {
        Exceptions.silentConsumer(p -> throwIO()).accept(null);
    }

    @Test()
    public void throw_SilentFunction() {
        Exceptions.silentFunction((p) -> throwIO()).apply(null);
    }

    // PASS

    @Test()
    public void testPass() {
        assertThat(Exceptions.pass(() -> ExceptionsTest.throwIO()), is(false));
        assertThat(Exceptions.pass(() -> { }), is(true));
    }

    // CALL

    @Test()
    public void testCall() {
        assertThat(Exceptions.call(() -> 1).get(), is(1));
        assertThat(Exceptions.call(() -> 1/0).orElse(-1), is(-1));

        assertThat(Exceptions.call((n) -> 1/n, 0).orElse(-1), is(-1));
    }

}