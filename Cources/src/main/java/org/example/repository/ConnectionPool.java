package org.example.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private BlockingQueue<Connection> connectionBlockingQueue;
    private BlockingQueue<Connection> givenAwayConnectionQueue;
    private final String url;
    private final String user;
    private final String password;
    private final int poolSize;
    private static ConnectionPool instance;


    private ConnectionPool() throws IOException, SQLException {
        DBResources dbResources = DBResources.getInstance();
        dbResources.getResource();
        this.url = dbResources.getURL();
        this.user = dbResources.getUser();
        this.password = dbResources.getPASS();
        this.poolSize = dbResources.getPoolSize();
        initPoolData();
    }

    public void initPoolData() {
        try {
            givenAwayConnectionQueue = new ArrayBlockingQueue<>(poolSize);
            connectionBlockingQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionBlockingQueue.add(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not get init pool data", e);
        }
    }

    public Connection takeConnection() {
        Connection connection = null;
        try {
            connection = connectionBlockingQueue.take();

            // If tables are not created
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS teachers(" +
                    "id serial PRIMARY KEY, " +
                    "name TEXT NOT NULL UNIQUE, " +
                    "age INT NOT NULL)");

            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS courses(" +
                    "id serial PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "duration INT NOT NULL, " +
                    "price NUMERIC NOT NULL, " +
                    "teacher_id INT NOT NULL, " +
                    "FOREIGN KEY(teacher_id) REFERENCES teachers(id))");

            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS students(" +
                    "id serial PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "age INT NOT NULL)");

            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS subscriptions(" +
                    "id serial PRIMARY KEY, " +
                    "student_id INT NOT NULL, " +
                    "course_id INT NOT NULL, " +
                    "FOREIGN KEY(student_id) REFERENCES students(id), " +
                    "FOREIGN KEY(course_id) REFERENCES courses(id))");

            givenAwayConnectionQueue.add(connection);
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException("Can not create connection", e);
        }
        return connection;
    }

    public void givenAwayConnection(Connection connection, Statement statement, ResultSet resultSet) throws InterruptedException {
        try {
            resultSet.close();
            statement.close();
            givenAwayConnectionQueue.take();
            connectionBlockingQueue.add(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Statement or resultSet not closed", e);
        }
    }

    public void givenAwayConnection(Connection connection, Statement statement) throws InterruptedException {
        try {
            statement.close();
            givenAwayConnectionQueue.take();
            connectionBlockingQueue.add(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Statement or resultSet not closed", e);
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                instance = new ConnectionPool();
            } catch (IOException | SQLException e) {
                throw new RuntimeException("Can not get instance of connection", e);
            }
        }
        return instance;
    }
}
