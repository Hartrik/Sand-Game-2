package cz.hartrik.sg2.engine;

import cz.hartrik.common.Checker;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadFactory, která dává vláknům názvy podle určeného formátu.
 *
 * @version 2016-06-25
 * @author Patrik Harag
 */
public class ThreadFactoryName implements ThreadFactory {

    private final String nameFormat;
    private final AtomicInteger i = new AtomicInteger();

    /**
     * Vytvoří novou instanci.
     *
     * @param nameFormat formát názvu, je předáván jeden parametr - pořadové
     *                   číslo tohoto vlákna
     */
    public ThreadFactoryName(String nameFormat) {
        this.nameFormat = Checker.requireNonNull(nameFormat);
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, String.format(nameFormat, i.incrementAndGet()));
    }

}