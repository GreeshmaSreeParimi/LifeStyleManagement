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
    private static String DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static char TIME_FORMATTER = 'T';
    String timeToNotify;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_breaks_dialog,null);
        builder.setView(dialogView).setTitle("Add Information")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = break_title.getText().toString().trim();                               //access the data from the input field
                        String date = date_picker_btn.getText().toString().trim();                                 //access the date from the choose date button
                        String time = time_picker_btn.getText().toString().trim();
                        if (title.isEmpty()) {
                            Toast.makeText(getContext(), "Please Enter text", Toast.LENGTH_SHORT).show();   //shows the toast if input field is empty
                        }
                        else if(date.equals("date") && time.equals("time")){
                            //shows toast if date and time are not selected
                            Toast.makeText(getContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
                        }
                        else if(time.equals("time") && !date.equals("date")){
                            Toast.makeText(getContext(), "Please select time", Toast.LENGTH_SHORT).show();

                        }
                        else if(!time.equals("time") && date.equals("date")){
                            Toast.makeText(getContext(), "Please select date", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            setAlarm(getContext(),title, date, time);
                            Toast.makeText(getContext(), "Alarm set for selected date and time", Toast.LENGTH_SHORT).show();

                        }
                        listener.saveBreaksData(title,picked_year,picked_month,picked_day,picked_minute,picked_hour,picked_am_pm);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("break_title", break_title.getText().toString());
        outState.putString("break_date", date_picker_btn.getText().toString());
        outState.putString("break_time", time_picker_btn.getText().toString());
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
                            timeToNotify = picked_hour + ":" + picked_minute + ":00";

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
                date_picker_btn.setText(year + "-" + (month+1) + "-"+ day);

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

    String getDateInFormat(String date) {
        return date + TIME_FORMATTER + timeToNotify;
    }

    private void setAlarm(Context context ,String text, String date, String time) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);                   //assigning alarm manager object to set alarm
        Intent intent = new Intent(getContext(), AlarmBroadcast.class);
        intent.putExtra("event", text);                                                       //sending data to alarm class to create channel and notification
        intent.putExtra("time", date);
        intent.putExtra("date", time);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        String dateandtime = getDateInFormat(date);
        //String testDate = "2022-10-29"+'T'+timeToNotify+":00";
        System.out.println(dateandtime);
        DateFormat formatter = new SimpleDateFormat(DATA_FORMAT);

        try {
            Date date1 = formatter.parse(dateandtime);
            System.out.println(date1.getTime());
            am.setExact(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intentBack = new Intent(getContext(), BreaksPage.class); //this intent will be called once the setting alarm is complete
        Toast.makeText(getContext(), "Alarm", Toast.LENGTH_SHORT).show();
        intentBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentBack);                                                                  //navigates from adding reminder activity to mainactivity
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
