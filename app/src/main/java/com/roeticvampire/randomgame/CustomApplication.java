package com.roeticvampire.randomgame;

import android.app.Application;
import android.database.SQLException;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class CustomApplication extends Application {
    /*private static final String ip="192.168.31.174";
    private static final String port="1433";
    private static final String username="test";
    private static final String password="test";
    private static final String sauce="net.sourceforge.jtds.jdbc.Driver";
    private static final String database="RandomGame";
    private static final String url="jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
*/
    private static final String port="5432";
    private static final String username="yjkacfnreetjqm";
    private static final String password="bb18b02ce77ad393a85f5b13aeee502080bcd3c6439c48b26e524af9090f3a42";
    private static final String sauce="org.postgresql.Driver";
    private static final String database="d4ovjh8nl60821";
    private static final String url="jdbc:postgresql://ec2-3-234-22-132.compute-1.amazonaws.com:5432/d4ovjh8nl60821";
    //private static final String url="jdbc:postgres://yjkacfnreetjqm:bb18b02ce77ad393a85f5b13aeee502080bcd3c6439c48b26e524af9090f3a42@ec2-3-234-22-132.compute-1.amazonaws.com:5432/d4ovjh8nl60821";
    public static Connection connection;




    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            Class.forName(sauce);
            connection= DriverManager.getConnection(url,username,password);
            Log.d("Bro", "onCreate: We connected");
        }catch (SQLException | java.sql.SQLException e){e.printStackTrace();
            Log.d("Bro", "Issues #2"+e.toString());
        } catch (ClassNotFoundException e) {
            Log.d("Bro", "Issues");
            e.printStackTrace();
        }

    }

}
