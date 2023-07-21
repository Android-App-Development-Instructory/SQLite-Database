package com.alaminkarno.sqlitedatbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ShowDataActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UserAdapter adapter;
    List<User> userList;
    DatabaseHelper databaseHelper;

    final static int STORAGE_REQUEST_CODE = 1;


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

    private void exportCSV() {

        File csvFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath()+"/"+"SQLiteDatabase");

        if(!csvFolder.exists()){
          csvFolder.mkdir();
        }


        /// String csvFileName = UUID.randomUUID().toString() + "_database_backup.csv";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

        String csvFileName = "database_backup_"+simpleDateFormat.format(new Date())+".csv";

        String csvPath = csvFolder.toString()+"/"+csvFileName;

        try {
            FileWriter fileWriter = new FileWriter(csvPath);

            for(int i = 0; i < userList.size(); i++){

                fileWriter.append(""+userList.get(i).getId());
                fileWriter.append(",");

                fileWriter.append(""+userList.get(i).getName());
                fileWriter.append(",");

                fileWriter.append(""+userList.get(i).getAge());
                fileWriter.append("\n");

            }

            fileWriter.flush();
            fileWriter.close();

            Toast.makeText(this, "Backup Completed To: "+csvPath, Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkStoragePermission(){
     return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_REQUEST_CODE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_backup){

            if(checkStoragePermission()){
                exportCSV();
            }else{
                requestStoragePermission();
            }

        }else if(id == R.id.menu_restore){
            Toast.makeText(this, "Restore", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == STORAGE_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Export CSV
                exportCSV();
            }
            else{
                Toast.makeText(this, "Storage Permission Needed!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}