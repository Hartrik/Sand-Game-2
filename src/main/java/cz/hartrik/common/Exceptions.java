package cz.hartrik.common;

import cz.hartrik.common.reflect.LibraryClass;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Obsahuje statické metody pro práci s výjimkami.
 * Kvůli lambda výrazům nejsou metody přetížené. <p>
 *
 * <b>Změna v pojmenování:</b> <br />
 * <code>uncheckedRun -> unchecked</code> <br />
 * <code>silentRun -> silent</code>
 *
 * @version 2015-08-13
 * @author Patrik Harag
 */
@LibraryClass
public final class Exceptions {

    private Exceptions() { }

    // RŮZNÉ UTIL METODY

    /**
     * Zabalí kontrolovanou výjimku jako výjimku nekontrolovanou.
     * Pokud je to možné, výjimku pouze přetypuje.
     *
     * @param exception kontrolovaná výjimka
     * @return nekontrolovaná výjimka
     */
    public static RuntimeException wrapException(Throwable exception) {
        if (exception instanceof IOException)
            return new UncheckedIOException((IOException) exception);
        else if (exception instanceof RuntimeException)
            return (RuntimeException) exception;
        else
            return new RuntimeException(exception);
    }

    // UNCHECKED

    @FunctionalInterface
    public static interface ThrowableRunnable extends Runnable {
        public void runChecked() throws Throwable;

        @Override
        public default void run() {
            try {
                runChecked();
            } catch (Throwable ex) {
                throw wrapException(ex);
            }
        }
    }

    @FunctionalInterface
    public static interface ThrowableSupplier<T> extends Supplier<T> {
        public T getChecked() throws Throwable;

        @Override
        public default T get() {
            try {
                return getChecked();
            } catch (Throwable ex) {
                throw wrapException(ex);
            }
        }
    }

    @FunctionalInterface
    public static interface ThrowableConsumer<T> extends Consumer<T> {
        public void acceptChecked(T parameter) throws Throwable;

        @Override
        public default void accept(T parameter) {
            try {
                acceptChecked(parameter);
            } catch (Throwable ex) {
                throw wrapException(ex);
            }
        }
    }

    @FunctionalInterface
    public static interface ThrowableFunction<T, R> extends Function<T, R> {
        public R applyChecked(T parameter) throws Throwable;

        @Override
        public default R apply(T parameter) {
            try {
                return applyChecked(parameter);
            } catch (Throwable ex) {
                throw wrapException(ex);
            }
        }
    }

    // SILENT

    @FunctionalInterface
    public static interface SilentRunnable extends Runnable {
        public void runChecked() throws Throwable;

        @Override
        public default void run() {
            try {
                runChecked();
            } catch (Throwable ex) { }
        }
    }

    @FunctionalInterface
    public static interface SilentSupplier<T> extends Supplier<T> {
        public T getChecked() throws Throwable;

        @Override
        public default T get() {
            try {
                return getChecked();
            } catch (Throwable ex) {
                return null;
            }
        }
    }

    @FunctionalInterface
    public static interface SilentConsumer<T> extends Consumer<T> {
        public void acceptChecked(T parameter) throws Throwable;

        @Override
        public default void accept(T parameter) {
            try {
                acceptChecked(parameter);
            } catch (Throwable ex) { }
        }
    }

    @FunctionalInterface
    public static interface SilentFunction<T, R> extends Function<T, R> {
        public R applyChecked(T parameter) throws Throwable;

        @Override
        public default R apply(T parameter) {
            try {
                return applyChecked(parameter);
            } catch (Throwable ex) {
                return null;
            }
        }
    }

    // tvorba unchecked funkcí

    public static Runnable uncheckedRunnable(ThrowableRunnable runnable) {
        return runnable;
    }

    public static <T> Consumer<T> uncheckedConsumer(ThrowableConsumer<T> consumer) {
        return consumer;
    }

    public static <T> Supplier<T> uncheckedSupplier(ThrowableSupplier<T> supplier) {
        return supplier;
    }

    public static <T, R> Function<T, R> uncheckedFunction(
            ThrowableFunction<T, R> function) {

        return function;
    }

    // tvorba a vyhodnocení unchecked funkcí

    public static void unchecked(ThrowableRunnable runnable) {
        runnable.run();
    }

    public static <T> T uncheckedGet(ThrowableSupplier<T> supplier) {
        return supplier.get();
    }

    public static <T> void uncheckedAccept(ThrowableConsumer<T> consumer, T p) {
        consumer.accept(p);
    }

    public static <T, R> R uncheckedApply(ThrowableFunction<T, R> function, T p) {
        return function.apply(p);
    }

    // tvorba silent funkcí

    public static Runnable silentRunnable(SilentRunnable runnable) {
        return runnable;
    }

    public static <T> Consumer<T> silentConsumer(SilentConsumer<T> consumer) {
        return consumer;
    }

    public static <T> Supplier<T> silentSupplier(SilentSupplier<T> supplier) {
        return supplier;
    }

    public static <T, R> Function<T, R> silentFunction(SilentFunction<T, R> function) {
        return function;
    }

    // tvorba a vyhodnocení silent funkcí

    public static void silent(SilentRunnable runnable) {
        runnable.run();
    }

    public static <T> void silentAccept(SilentConsumer<T> consumer, T p) {
        consumer.accept(p);
    }

    public static <T> T silentGet(SilentSupplier<T> supplier) {
        return supplier.get();
    }

    public static <T, R> R silentApply(SilentFunction<T, R> function, T p) {
        return function.apply(p);
    }

    // OSTATNÍ

    /**
     * Vyhodnotí funkci a vrátí {@code true}, pokud nebude vyhozena výjimka.
     *
     * @param runnable {@code Runnable}
     * @return {@code boolean}
     */
    public static boolean pass(ThrowableRunnable runnable) {
        try {
            runnable.runChecked();
        } catch (Throwable ex) {
            return false;
        }
        return true;
    }

    /**
     * Zavolá funkci s parametrem a vrátí výsledek jako {@link Optional}.
     * Pokud funkce vrátí {@code null} nebo dojde k výjimce, {@code Optional}
     * bude prázdný. <p>
     *
     * <b>Ukázka:</b> <p>
     * {@code Exceptions.call(n -> 1/n, 0).orElse(-1); } <br />
     * {@code >>> -1}
     *
     * @param <T> typ parametru
     * @param <R> typ, který vrací funkce
     * @param function funkce
     * @param p parametr funkce
     * @return {@code Optional}
     */
    public static <T, R> Optional<R> call(SilentFunction<T, R> function, T p) {
        return Optional.ofNullable(function.apply(p));
    }

    /**
     * Zavolá funkci a vrátí výsledek jako {@link Optional}. Pokud funkce vrátí
     * {@code null} nebo dojde k výjimce, {@code Optional} bude prázdný. <p>
     *
     * <b>Ukázka:</b> <p>
     * {@code Exceptions.call(() -> 1/0).orElse(0); } <br />
     * {@code >>> 0}
     *
     * @param <T> typ, který vrací {@code Supplier}
     * @param supplier dodavatel
     * @return {@code Optional}
     */
    public static <T> Optional<T> call(SilentSupplier<T> supplier) {
        return Optional.ofNullable(supplier.get());
    }

}