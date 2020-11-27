package com.alaminkarno.sqlitedatbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameET,ageET;
    Button insertBTN;
    DatabaseHelp databaseHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameET = findViewById(R.id.nameET);
        ageET = findViewById(R.id.ageET);
        insertBTN = findViewById(R.id.insertBTN);

        databaseHelp = new DatabaseHelp(this);


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

                   long id = databaseHelp.insertData(name,age);

                    Toast.makeText(MainActivity.this, "Data insert & ID is: "+id, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void showData(View view) {
        startActivity(new Intent(MainActivity.this,ShowDataActivity.class));
    }
}