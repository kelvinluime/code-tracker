package com.kinmanlui.database;

import com.kinmanlui.main.Resource;
import com.kinmanlui.main.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public enum TestBase {
    INSTANCE;

    private Connection conn = null;

    public Connection connect() {
        if(conn == null) {
            try {
                String url = "jdbc:sqlite:" + Resource.DATABASE_PATH + "tests.db";
                conn = DriverManager.getConnection(url);
                System.out.println("Connection to SQLite has been established.");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return conn;
    }

    public void insert(String className, String testName) {
        connect();
        String insertStatement = "INSERT INTO tests(ClassName, TestName) VALUES(?,?)";
        try (PreparedStatement s2 = conn.prepareStatement(insertStatement)) {

            s2.setString(1, className);
            s2.setString(2, testName);
            s2.executeUpdate();

            System.out.println("New data has been inserted successfully.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void remove(String testName) {
        connect();
        String sql = "DELETE FROM tests WHERE TestName = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, testName);
            pstmt.executeUpdate();
            System.out.println("Test " + testName + " has been removed.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public List<Test> get() {
        connect();
        String sql = "SELECT ClassName, TestName FROM tests";
        List<Test>  list = new LinkedList<>();

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

                while(rs.next()) {
                    try {
                        // Get runnable objects from their class names, so that tester can execute these runnable objects
                        Class<?> cs = Class.forName(Resource.CODE_PATH_WITH_DOTS + rs.getString("ClassName"));
                        Constructor<?> cr = cs.getConstructor();
                        Runnable runnable = Runnable.class.cast(cr.newInstance());

                        Test test = new Test(runnable, rs.getString("ClassName") + ".java", rs.getString("TestName"));
                        list.add(test);
                    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException| IllegalAccessException | InvocationTargetException e) {
                        System.err.println("Unable to get class: " + e.getMessage());
                    }
                }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public void update(String className, String testName) {
        connect();
        String sql = "UPDATE tests SET ClassName = ? , "
                + "TestName = ? ";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, className);
            pstmt.setString(2, testName);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    public void close() {
        try {
            conn.close();
            conn = null;
            System.out.println("Connection to SQLite has been closed.");
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

}
