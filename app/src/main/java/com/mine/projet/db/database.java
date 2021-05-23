package com.mine.projet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    private Connection connection;

    // private final String host = "ssprojectinstance.csv2nbvvgbcb.us-east-2.rds.amazonaws.com"  // For Amazon Postgresql
    private final String host = "remotemysql.com";  // For Google Cloud Postgresql
    private final String database = "wWCxRJNb4L";
    private final int port = 3306;
    private final String user = "wWCxRJNb4L";
    private final String pass = "AYs3L2KBs5";
    private String url = "jdbc:mysql://remotemysql.com:3306/wWCxRJNb4L";
    private boolean status;

    public database() {
        connect();
        //this.disconnect();
        System.out.println("connection status:" + status);
    }

    private void connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    System.out.println("connected:" + status);
                } catch (Exception e) {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    public Connection getExtraConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection(url, user, pass);
        return c;
    }

}
