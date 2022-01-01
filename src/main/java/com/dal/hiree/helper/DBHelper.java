package com.dal.hiree.helper;

import java.sql.*;

public class DBHelper {
    private static Connection connection;
    private static Statement statement;
    private static DBHelper dbHelper;
    private final String DB_HOST = "database-1.c4kwymqrvbgi.us-east-1.rds.amazonaws.com";
    //private final String DB_HOST = "localhost";
    private final String DEFAULT_MYSQL_USERNAME = "admin";
    private final String DEFAULT_MYSQL_PASSWORD = "admin123";
    private final String DEFAULT_MYSQL_DATABASE = "hireedb";
    String database;
    String user;
    String password;
    String connUrl;
    String url = "jdbc:mysql://%s:3306/%s?useSSL=false&allowPublicKeyRetrieval=true";

    private DBHelper() {
        this.database = DEFAULT_MYSQL_DATABASE;
        this.user = DEFAULT_MYSQL_USERNAME;
        this.password = DEFAULT_MYSQL_PASSWORD;
        connUrl = String.format(url, DB_HOST, this.database);
    }

    public static DBHelper getInstance() throws SQLException, ClassNotFoundException {
        if (dbHelper == null) {
            dbHelper = new DBHelper();
            dbHelper.initialize();
        }
        return dbHelper;
    }

    private void initialize() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connUrl, user, password);
        }
        if (statement == null) {
            statement = connection.createStatement();
        }
    }

    public void close() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public void executeCreateOrUpdateQuery(String query) throws SQLException {
        if (connection == null) {
            throw new RuntimeException("Please call initialize method in DBHelper before calling this method.");
        }
        statement.executeUpdate(query);
    }

    public ResultSet executeSelectQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBHelper dbHelper = DBHelper.getInstance();
    }
}
