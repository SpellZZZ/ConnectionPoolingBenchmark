package com.cp.connectionPool;

import lombok.Getter;
import org.apache.commons.dbcp2.BasicDataSource;

public class BasicDataSourceCP {
    @Getter
    private final BasicDataSource dataSource = new BasicDataSource();

    public BasicDataSourceCP(String url, String username, String password) {
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }
}
