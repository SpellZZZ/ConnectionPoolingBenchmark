package com.cp.connectionPool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import java.util.Properties;

public class HikariCP {

    @Getter
    private HikariDataSource dataSource;
    private HikariConfig config = new HikariConfig();


    public HikariCP(String url, String username, String password, int maxPoolSize, Properties properties) {
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(maxPoolSize);
        properties.forEach((key, value) -> config.addDataSourceProperty((String) key, value));

        dataSource = new HikariDataSource(config);
    }

}
