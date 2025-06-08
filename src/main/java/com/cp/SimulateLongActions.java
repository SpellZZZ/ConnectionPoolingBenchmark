package com.cp;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulateLongActions {
    private final String SQL_QUERY = "SELECT CLOCK_TIMESTAMP() AS time, PG_SLEEP(5);";

    public long simulateThreads(DataSource dataSource, int repeat) {
        CountDownLatch latch = new CountDownLatch(repeat);

        for (int i = 0; i < repeat; i++) {
            new Thread(() -> {
                long start = System.nanoTime();
                try (Connection connection = dataSource.getConnection();
                     Statement statement = connection.createStatement();
                     ResultSet result = statement.executeQuery(SQL_QUERY)) {

                    result.next();
                    String time = result.getString("time");
                    long end = System.nanoTime();
                    printTime(Thread.currentThread().getName(), end, start);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                latch.countDown();
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return System.nanoTime();
    }

    public long simulateExecutors(DataSource dataSource, int repeat) {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < repeat; i++) {
            executor.submit(() -> {
                long start = System.nanoTime();
                try (Connection connection = dataSource.getConnection();
                     Statement statement = connection.createStatement();
                     ResultSet result = statement.executeQuery(SQL_QUERY)) {

                    result.next();
                    String time = result.getString("time");
                    long end = System.nanoTime();
                    printTime(Thread.currentThread().getName(), end, start);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return System.nanoTime();
    }

    public static void printTime(String name, long end, long start) {
        double time = ((end - start) * 1.0) / Main.NANO_SEC;
        String output = String.format("%s: query time finish: %f", name, time);
        System.out.println(output);
    }
}
