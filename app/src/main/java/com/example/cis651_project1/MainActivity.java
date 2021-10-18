package com.example.cis651_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void acswitch(View v){

       String button_clicked;

       button_clicked=((Button)v).getText().toString();

       if(button_clicked.equals("Easy")){
           Intent easy = new Intent(this,easyActivity.class);
           startActivity(easy);
       }
        if(button_clicked.equals("Medium")){
            Intent med = new Intent(this,medActivity.class);
            startActivity(med);
        }
        if(button_clicked.equals("Hard")){
            Intent hard = new Intent(this,hardActivity.class);
            startActivity(hard);
        }
    }
}