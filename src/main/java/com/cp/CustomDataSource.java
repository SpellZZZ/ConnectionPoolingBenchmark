package com.cp;

import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class CustomDataSource implements DataSource {
    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = getConnection(user, password);
            return connection;
        }
        if (connection.isClosed()) {
            connection = getConnection(user, password);
            return connection;
        }
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        if (connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        return connection;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
