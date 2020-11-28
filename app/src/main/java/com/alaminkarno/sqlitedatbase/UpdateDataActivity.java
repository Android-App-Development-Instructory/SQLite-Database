package com.alaminkarno.sqlitedatbase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDataActivity extends AppCompatActivity {

    EditText nameET,ageET,searchET;
    DatabaseHelp databaseHelp;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        nameET = findViewById(R.id.nameET);
        ageET = findViewById(R.id.ageET);
        searchET = findViewById(R.id.searchET);
        databaseHelp = new DatabaseHelp(this);

    }

    public void search(View view) {
        ID = searchET.getText().toString();

        if(ID.isEmpty()){
            Toast.makeText(this, "Enter ID for Search", Toast.LENGTH_SHORT).show();
        }
        else {
            Cursor cursor = databaseHelp.searchData(Integer.parseInt(ID));

            while (cursor.moveToNext()){

                String name = cursor.getString(cursor.getColumnIndex(databaseHelp.COL_NAME));
                String age = cursor.getString(cursor.getColumnIndex(databaseHelp.COL_AGE));

                nameET.setText(name);
                ageET.setText(age);
            }

        }
    }

    public void updateData(View view) {

        String name = nameET.getText().toString();
        String age = ageET.getText().toString();

        if(name.isEmpty() || age.isEmpty()){
            Toast.makeText(this, "Please Search First", Toast.LENGTH_SHORT).show();
        }
        else {
           boolean check = databaseHelp.updateData(Integer.parseInt(ID),name,age);

           if(check){
               Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
           }
           else {
               Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
           }
        }
    }

    public void deleteData(View view) {
        String name = nameET.getText().toString();
        String age = ageET.getText().toString();

        if(name.isEmpty() || age.isEmpty()){
            Toast.makeText(this, "Please Search First", Toast.LENGTH_SHORT).show();
        }
        else {
            int check = databaseHelp.deleteData(Integer.parseInt(ID));

            if(check>0){
                Toast.makeText(this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                nameET.setText("");
                ageET.setText("");
            }
            else {
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}