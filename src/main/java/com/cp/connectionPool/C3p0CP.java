package com.cp.connectionPool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Getter;

public class C3p0CP {
    @Getter
    private final ComboPooledDataSource dataSource = new ComboPooledDataSource();

    public C3p0CP(String url, String username, String password, int maxPoolSize) {
        dataSource.setJdbcUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setMaxPoolSize(maxPoolSize);
    }
}