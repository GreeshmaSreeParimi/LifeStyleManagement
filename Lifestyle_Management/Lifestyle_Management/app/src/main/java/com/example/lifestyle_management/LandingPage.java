package com.example.lifestyle_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LandingPage extends AppCompatActivity {
    CardView date_card, break_card, routine_card;
    Button log_out;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        date_card =  findViewById(R.id.date_card);
        break_card = findViewById(R.id.breaks_card);
        log_out = findViewById(R.id.logOut);

        DatabaseHelper databasehelper = new DatabaseHelper(LandingPage.this);
        boolean doesTableExist = databasehelper.doesTableExist("BREAKS_TABLE");
        if(!doesTableExist){
            databasehelper.createBreaksTable();
            databasehelper.addBreak("Work Break", "2022-12-1","2:30 pm", 121,1);
            databasehelper.addBreak("Gym Break", "2022-12-9","6:30 am", 162,0);
            databasehelper.addBreak("Water Break", "2022-11-30","11:30 am", 741,1);
       }

        date_card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               openCurrentDatePage();

            }
        });
        break_card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              openBreaksPage();

            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sp = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("logged", true); // set it to true when the user is logged out
                editor.putString("Email" , null);
                editor.apply();
                startActivity(new Intent(LandingPage.this , MainActivity.class));
            }
        });
    }
    public void openCurrentDatePage(){
        Intent intent = new Intent(this,CurrentDatePage.class);
        startActivity(intent);
    }
    public void openBreaksPage(){
        Intent intent = new Intent(this,BreaksPage.class);
        startActivity(intent);
    }
    
   @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        if (sharedPreferences.getBoolean("logged", true)) {
            finish();
        }
        else
        {
            moveTaskToBack(true);
        }

    }
}
