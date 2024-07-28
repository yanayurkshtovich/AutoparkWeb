package infrastructure.databaseServices.impl;

import infrastructure.core.annotations.InitMethod;
import infrastructure.core.annotations.Property;
import infrastructure.databaseServices.ConnectionFactory;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactoryImpl implements ConnectionFactory {
    @Property("url")
    private String url;
    @Property("username")
    private String username;
    @Property("password")
    private String password;
    private Connection connection;

    public ConnectionFactoryImpl() {}

    @SneakyThrows
    @InitMethod
    public void initConnection() {
        this.connection = DriverManager.getConnection(this.url, this.username, this.password);
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        return this.connection;
    }
}
