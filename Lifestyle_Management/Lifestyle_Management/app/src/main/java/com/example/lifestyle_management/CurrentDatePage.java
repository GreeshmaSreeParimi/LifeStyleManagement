package com.example.lifestyle_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class CurrentDatePage extends AppCompatActivity {
    TodayTaskAdapter taskAdapter;
    ArrayList<Breaks_Storage_Model> Breaks_Storage_ModelArrayList;
    ArrayList<Task_Storage_Model> Task_Storage_ModelArrayList;
    RecyclerView taskRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_date_page);

        taskRV = findViewById(R.id.idTasks);
        Breaks_Storage_ModelArrayList = new ArrayList<Breaks_Storage_Model>();

        // Here, we have created new array list and added data to it
        SharedPreferences.Editor editor = getSharedPreferences("breaks_data", MODE_PRIVATE).edit();
        SharedPreferences sharedPreferences = getSharedPreferences("breaks_data", MODE_PRIVATE);
        String breaks_data = sharedPreferences.getString("breaks_list", null);
        Type type = new TypeToken<ArrayList<Breaks_Storage_Model>>() {

        }.getType();
        Gson gson = new Gson();
        Breaks_Storage_ModelArrayList = (breaks_data == null) ? Breaks_Storage_ModelArrayList : gson.fromJson(breaks_data, type);
        int size = Breaks_Storage_ModelArrayList.size();
        System.out.println(Breaks_Storage_ModelArrayList);
        String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
        Task_Storage_ModelArrayList = new ArrayList<Task_Storage_Model>();

        for(int i=0; i<size;i++)
        {
        if(Breaks_Storage_ModelArrayList.get(i).getBreak_date().equals(currentDate))
            {
                String task_name = Breaks_Storage_ModelArrayList.get(i).getBreak_name();
                String task_time = Breaks_Storage_ModelArrayList.get(i).getBreak_time();
                String task_date = Breaks_Storage_ModelArrayList.get(i).getBreak_date();
                Task_Storage_ModelArrayList.add(new Task_Storage_Model(task_name, task_time,task_date));
            }
        }
        // we are initializing our adapter class and passing our arraylist to it.
        taskAdapter = new TodayTaskAdapter(this, Task_Storage_ModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        taskRV.setLayoutManager(linearLayoutManager);
        taskRV.setAdapter(taskAdapter);

        if(Task_Storage_ModelArrayList == null)
        {
            Toast.makeText(getApplicationContext(), "No tasks for today", Toast.LENGTH_LONG).show();
        }
    }



}