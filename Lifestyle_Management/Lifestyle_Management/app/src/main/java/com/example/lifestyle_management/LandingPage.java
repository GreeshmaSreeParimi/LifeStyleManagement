package com.example.lifestyle_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LandingPage extends AppCompatActivity {
    CardView date_card, break_card, routine_card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        date_card =  findViewById(R.id.date_card);
        break_card = findViewById(R.id.breaks_card);

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


    }
    public void openCurrentDatePage(){
        Intent intent = new Intent(this,CurrentDatePage.class);
        startActivity(intent);
    }
    public void openBreaksPage(){
        Intent intent = new Intent(this,BreaksPage.class);
        startActivity(intent);
    }
}