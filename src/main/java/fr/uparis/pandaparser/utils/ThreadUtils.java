package fr.uparis.pandaparser.utils;

import lombok.extern.java.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Classe utilitaire pour la gestion des threads.
 */
@Log
public class ThreadUtils {

    private ThreadUtils() {
    }

    /**
     * Log future result
     *
     * @param future future
     * @throws ExecutionException   execution exception
     * @throws InterruptedException interrupted exception
     */
    public static void logFuture(final Future<String> future) throws ExecutionException, InterruptedException {
        log.info(future.get());
    }


    /**
     * Log future result
     *
     * @param futures futures
     * @throws ExecutionException   execution exception
     * @throws InterruptedException interrupted exception
     */
    public static void logAllFutures(final List<Future<String>> futures) throws ExecutionException, InterruptedException {
        for (Future<String> future : futures)
            logFuture(future);
    }

}
