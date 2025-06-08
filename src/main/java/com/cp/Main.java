package com.cp;

import com.cp.connectionPool.BasicDataSourceCP;
import com.cp.connectionPool.C3p0CP;
import com.cp.connectionPool.HikariCP;

import javax.sql.DataSource;
import java.util.Properties;

public class Main {
    public static final long NANO_SEC = 1_000_000_000L;
    public static final int REPEAT_TIMES = 20;
    public static final int REST_TIME = 2000;
    public static final String URL = "jdbc:postgresql://localhost:5432/pg_custom_db";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";

    public static final String CACHE_PREP_STATEMENT = "cachePrepStmts";
    public static final String CACHE_PREP_STATEMENT_VAL = "true";

    public static final String PREOP_STMT_CACHESIZE = "prepStmtCacheSize";
    public static final String PREOP_STMT_CACHESIZE_VAL = "250";

    public static final String PREOP_STMT_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";
    public static final String PREOP_STMT_CACHE_SQL_LIMIT_VAL = "2048";


    public static void main(String[] args) throws InterruptedException {
        SimulateLongActions simulateLongActions = new SimulateLongActions();
        long programStart = System.nanoTime();

        Properties hikariProperties = new Properties();
        hikariProperties.setProperty(CACHE_PREP_STATEMENT, CACHE_PREP_STATEMENT_VAL);
        hikariProperties.setProperty(PREOP_STMT_CACHESIZE, PREOP_STMT_CACHESIZE_VAL);
        hikariProperties.setProperty(PREOP_STMT_CACHE_SQL_LIMIT, PREOP_STMT_CACHE_SQL_LIMIT_VAL);

        HikariCP hikariCP = new HikariCP(URL, USER, PASSWORD, REPEAT_TIMES, hikariProperties);
        C3p0CP c3p0CP = new C3p0CP(URL, USER, PASSWORD, REPEAT_TIMES);
        BasicDataSourceCP basicDataSourceCP = new BasicDataSourceCP(URL, USER, PASSWORD);


        DataSource customDataSource = new CustomDataSource(URL, USER, PASSWORD);
        DataSource hikariDataSource = hikariCP.getDataSource();
        DataSource c3p0DataSource = c3p0CP.getDataSource();
        DataSource basicDataSource = basicDataSourceCP.getDataSource();

        System.out.println("### " + REPEAT_TIMES + " actions with Threads ###");
        simulateThreads(simulateLongActions, customDataSource, "Custom");
        simulateThreads(simulateLongActions, hikariDataSource, "Hikari");
        simulateThreads(simulateLongActions, c3p0DataSource, "c3p0");
        simulateThreads(simulateLongActions, basicDataSource, "BasicDataSource");

        System.out.println("### " + REPEAT_TIMES + " actions with Executors ###");
        simulateExecutors(simulateLongActions, customDataSource, "Custom");
        simulateExecutors(simulateLongActions, hikariDataSource, "Hikari");
        simulateExecutors(simulateLongActions, c3p0DataSource, "c3p0");
        simulateExecutors(simulateLongActions, basicDataSource, "BasicDataSource");

        long programEnd = System.nanoTime();
        SimulateLongActions.printTime("Program", programEnd, programStart);
    }

    private static void simulateThreads(SimulateLongActions simulateLongActions,
                                        DataSource customDataSource, String name) throws InterruptedException {
        System.out.println("Testing " + name);
        simulateLongActions.simulateThreads(customDataSource, REPEAT_TIMES);
        Thread.sleep(REST_TIME);
    }

    private static void simulateExecutors(SimulateLongActions simulateLongActions,
                                          DataSource customDataSource, String name) throws InterruptedException {
        System.out.println("Testing " + name);
        simulateLongActions.simulateExecutors(customDataSource, REPEAT_TIMES);
        Thread.sleep(REST_TIME);
    }
}