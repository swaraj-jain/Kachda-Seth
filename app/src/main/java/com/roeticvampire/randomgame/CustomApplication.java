package com.roeticvampire.randomgame;

import android.app.Application;
import android.database.SQLException;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class CustomApplication extends Application {
    private static final String ip="192.168.31.174";
    private static final String port="1433";
    private static final String username="test";
    private static final String password="test";
    private static final String sauce="net.sourceforge.jtds.jdbc.Driver";
    private static final String database="RandomGame";
    private static final String url="jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    public static Connection connection;




    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            Class.forName(sauce);
            connection= DriverManager.getConnection(url,username,password);

        }catch (SQLException | java.sql.SQLException e){e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
