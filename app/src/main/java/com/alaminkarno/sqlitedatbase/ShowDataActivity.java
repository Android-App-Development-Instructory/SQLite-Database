package com.alaminkarno.sqlitedatbase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UserAdapter adapter;
    List<User> userList;
    DatabaseHelp databaseHelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        databaseHelp = new DatabaseHelp(this);
        recyclerView = findViewById(R.id.dataRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        adapter = new UserAdapter(userList);
        recyclerView.setAdapter(adapter);

        Cursor cursor = databaseHelp.showData();

        while (cursor.moveToNext()){

            int ID = cursor.getInt(cursor.getColumnIndex(databaseHelp.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(databaseHelp.COL_NAME));
            String age = cursor.getString(cursor.getColumnIndex(databaseHelp.COL_AGE));

            userList.add(new User(ID,name,age));
            adapter.notifyDataSetChanged();
        }

    }
}