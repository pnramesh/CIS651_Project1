package com.example.cis651_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class activity_score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        String tscore = null;
        Bundle scoreBundle = getIntent().getExtras();
        if(scoreBundle != null){
            tscore = scoreBundle.getString("TotalScore");
        }
        TextView tx = (TextView) findViewById(R.id.scoretxtView);
        tx.setText("Your Total Score: " + tscore);
    }

    public void goHome(View view){
        Intent home = new Intent(this,MainActivity.class);
        startActivity(home);
    }
}