package com.alaminkarno.sqlitedatbase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameET,ageET,searchET;
    Button insertBTN;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameET = findViewById(R.id.nameET);
        ageET = findViewById(R.id.ageET);
        searchET = findViewById(R.id.searchET);
        insertBTN = findViewById(R.id.insertBTN);

        databaseHelper = new DatabaseHelper(this);


        insertBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameET.getText().toString();
                String age = ageET.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
                else if(age.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter your age", Toast.LENGTH_SHORT).show();
                }
                else {

                   long id = databaseHelper.insertData(name,age);

                    Toast.makeText(MainActivity.this, "Data insert & ID is: "+id, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void showData(View view) {
        startActivity(new Intent(MainActivity.this,ShowDataActivity.class));
    }

    public void search(View view) {

        String ID = searchET.getText().toString();

        if(ID.isEmpty()){
            Toast.makeText(this, "Enter ID for Search", Toast.LENGTH_SHORT).show();
        }
        else {
            Cursor cursor = databaseHelper.searchData(Integer.parseInt(ID));

            while (cursor.moveToNext()){

                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME));
                String age = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_AGE));

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Search Result for ID: "+ID);
                builder.setMessage("Name: "+name+"\nAge: "+age);
                builder.setCancelable(false);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                // Toast.makeText(this, "Name: "+name+" Age: "+age, Toast.LENGTH_SHORT).show();
        }

        }

    }

    public void updateData(View view) {

        startActivity(new Intent(MainActivity.this,UpdateDataActivity.class));
    }
}