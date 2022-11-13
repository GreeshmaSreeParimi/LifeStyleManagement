package com.example.lifestyle_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class BreaksPage extends AppCompatActivity implements AddBreaksPage.AddBreaksPageListener, EditBreaksPage.EditBreaksPageListener {
    private FloatingActionButton b1;
    BreakAdapter breakAdapter;
    ArrayList<Breaks_Storage_Model> Breaks_Storage_ModelArrayList;
    RecyclerView breakRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breaks_page);

        breakRV = findViewById(R.id.idRVBreaks);
        breakRV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editDialog();
            }
        });


        // Here, we have created new array list and added data to it
        Breaks_Storage_ModelArrayList = new ArrayList<Breaks_Storage_Model>();
        Breaks_Storage_ModelArrayList.add(new Breaks_Storage_Model("Work Break", "2:30 pm","12/13/2022"));
        Breaks_Storage_ModelArrayList.add(new Breaks_Storage_Model("Gym Break", "6:30 am","11/13/2022"));
        Breaks_Storage_ModelArrayList.add(new Breaks_Storage_Model("Water Break", "11:30 am","10/30/2022"));

        SharedPreferences.Editor editor = getSharedPreferences("breaks_data",MODE_PRIVATE).edit();
        SharedPreferences sharedPreferences = getSharedPreferences("breaks_data",MODE_PRIVATE);
        String breaks_data = sharedPreferences.getString("breaks_list",null);
        Type type = new TypeToken<ArrayList<Breaks_Storage_Model>>(){

        }.getType();
        Gson gson = new Gson();
        Breaks_Storage_ModelArrayList = (breaks_data == null) ? Breaks_Storage_ModelArrayList : gson.fromJson(breaks_data,type);

        String breaks_list = gson.toJson(Breaks_Storage_ModelArrayList);
        editor.putString("breaks_list",breaks_list);
        editor.apply();

        b1 = (FloatingActionButton) findViewById(R.id.fab);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });

//        @Override
//        public void onClick(View view) {
//            Intent intent=new Intent(getApplicationContext(),EditBreaksPage.class);
//            startActivity(intent);
//        }
//    });

        // we are initializing our adapter class and passing our arraylist to it.
        breakAdapter = new BreakAdapter(this, Breaks_Storage_ModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        breakRV.setLayoutManager(linearLayoutManager);
        breakRV.setAdapter(breakAdapter);
    }


    private void openAddDialog() {
        AddBreaksPage addBreaksPage = new AddBreaksPage();
        addBreaksPage.show(getSupportFragmentManager(), "Add breaks");
    }

    private void editDialog(){
        EditBreaksPage editBreaksPage = new EditBreaksPage();
        editBreaksPage.show(getSupportFragmentManager(),"Edit breaks");

    }

    @Override
    public void saveBreaksData(String break_title,int year,int month,int day,int hour,int min,String am_pm) {
        // This data will be received from add breaks dialog

        String date = month + "-" +day + "-" +year;
        String time = hour + ":" + min + " " +am_pm;

        // ArrayList<Breaks_Storage_Model> Breaks_Storage_ModelArrayList;
        SharedPreferences.Editor editor = getSharedPreferences("breaks_data",MODE_PRIVATE).edit();
        SharedPreferences sharedPreferences = getSharedPreferences("breaks_data",MODE_PRIVATE);
        String breaks_data = sharedPreferences.getString("breaks_list",null);
        Type type = new TypeToken<ArrayList<Breaks_Storage_Model>>(){

        }.getType();
        Gson gson = new Gson();
        Breaks_Storage_ModelArrayList = (breaks_data == null) ? Breaks_Storage_ModelArrayList : gson.fromJson(breaks_data,type);
        Breaks_Storage_ModelArrayList.add(new Breaks_Storage_Model(break_title, time,date));

        System.out.println(Breaks_Storage_ModelArrayList.size() + " this is the count of breaks ");

        String breaks_list = gson.toJson(Breaks_Storage_ModelArrayList);
        editor.putString("breaks_list",breaks_list);
        editor.apply();

        breakAdapter = new BreakAdapter(this, Breaks_Storage_ModelArrayList);
        breakRV.setAdapter(breakAdapter);

    }
}