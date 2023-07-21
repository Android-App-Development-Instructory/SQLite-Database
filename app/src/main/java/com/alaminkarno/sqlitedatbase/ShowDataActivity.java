package com.alaminkarno.sqlitedatbase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UserAdapter adapter;
    List<User> userList;
    DatabaseHelper databaseHelper;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.dataRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        adapter = new UserAdapter(userList);
        recyclerView.setAdapter(adapter);

        Cursor cursor = databaseHelper.showData();

        while (cursor.moveToNext()){

            int ID = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME));
            String age = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_AGE));

            userList.add(new User(ID,name,age));
            adapter.notifyDataSetChanged();
        }

    }
}