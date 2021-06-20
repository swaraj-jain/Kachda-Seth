package com.roeticvampire.randomgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    RecyclerView leaderbdRecycler;
    LDB_Adapter adapter;
    Connection connection;
    List<LDB_User> scoresList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        leaderbdRecycler=findViewById(R.id.leaderbdRecycler);
        connection=CustomApplication.connection;
        scoresList=new ArrayList<>();
        Statement statement= null;



        try {
            statement = connection.createStatement();
            ResultSet resultSet= statement.executeQuery("select * from RandomPrimaryTable ORDER BY SCORE DESC;");
            while(resultSet.next()){
                String name=resultSet.getString(2);
                int score=resultSet.getInt(3);
                String profileImg=resultSet.getString(4);
                LDB_User temp= new LDB_User(name,profileImg,score);

                scoresList.add(temp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //we now have Ig all our members in the list

        leaderbdRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LDB_Adapter(scoresList, this );
        leaderbdRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        leaderbdRecycler.setAdapter(adapter);










    }
}