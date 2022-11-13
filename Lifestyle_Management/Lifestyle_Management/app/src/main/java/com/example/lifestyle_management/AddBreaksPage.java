package com.example.lifestyle_management;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddBreaksPage extends AppCompatDialogFragment {

    private EditText break_title;
    private AddBreaksPageListener listener;
    private DatePickerDialog datePickerDialog;
    private Button date_picker_btn, time_picker_btn;

    private int picked_day, picked_month, picked_year,picked_hour,picked_minute;
    private String picked_am_pm;
    private View dialogView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.add_breaks_dialog,null);
        builder.setView(dialogView).setTitle("Add Information")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String breakTitle = break_title.getText().toString();
                        listener.saveBreaksData(breakTitle, picked_year, picked_month, picked_day, picked_hour,
                                picked_minute, picked_am_pm);
                    }
                });
        initDatePicker(dialogView);
        break_title = (EditText) dialogView.findViewById(R.id.break_title);
        date_picker_btn = (Button) dialogView.findViewById(R.id.date_picker);
        date_picker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });
        time_picker_btn = (Button) dialogView.findViewById(R.id.time_picker);
        time_picker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker(view);
            }
        });
        if(savedInstanceState != null){
            break_title.setText(savedInstanceState.getString("break_title", "Enter Break title"));
            date_picker_btn.setText(savedInstanceState.getString("break_date", "Select date"));
            time_picker_btn.setText(savedInstanceState.getString("break_time", "Select time"));
        }


        return builder.create();

    }

    private void openTimePicker(View dialogView) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(dialogView.getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        picked_hour= hour;
                        picked_minute = minute;
                        String time = picked_hour + ":" + picked_minute;
                        SimpleDateFormat hrs_24 = new SimpleDateFormat("hh:mm");

                        try {
                            Date date = hrs_24.parse(time);
                            SimpleDateFormat hrs_12 = new SimpleDateFormat("hh:mm aa");
                            time_picker_btn.setText(hrs_12.format(date));
                            String[] time_split1 = time_picker_btn.getText().toString().split(" ");
                            picked_am_pm = time_split1[1];
                            String[]  time_split2 = time_picker_btn.getText().toString().split(":");
                            picked_hour = Integer.valueOf(time_split2[0]);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },12,0,false);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(picked_hour,picked_minute);
        timePickerDialog.show();

    }

    private void openDatePicker() {
        datePickerDialog.show();
    }

    private void initDatePicker(View dialogView) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                picked_day = day;
                picked_month = month+1;
                picked_year = year;
                date_picker_btn.setText(month+1 + "/" + day + "/"+ year);

            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(dialogView.getContext(),style,dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("break_title", break_title.getText().toString());
        outState.putString("break_date", date_picker_btn.getText().toString());
        outState.putString("break_time", time_picker_btn.getText().toString());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddBreaksPageListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Implement AddBreaksPageListener");
        }
    }

    public interface  AddBreaksPageListener {
        void saveBreaksData(String break_title,int year,int month,int day,int hr,int min, String am_pm);

    }
}
