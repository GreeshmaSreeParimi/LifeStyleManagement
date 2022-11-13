package com.example.lifestyle_management;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lifestyle_management.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditBreaksPage extends AppCompatActivity {

    private static String DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static char TIME_FORMATTER = 'T';

    TextView addBreak,editTitle;
    Button dateButton, timeButton,submitButton;
    String timeToNotify;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_break_dialog);
        addBreak = findViewById(R.id.add_break);
        dateButton = (Button) findViewById(R.id.btnDate);
        timeButton = (Button) findViewById(R.id.btnTime);
        submitButton = (Button) findViewById(R.id.btnSubmit);

        Intent i = getIntent();

        String value1= i.getStringExtra("BreakName");
        editTitle = findViewById(R.id.editTitle);
        editTitle.setText(value1);

//        String value2= i.getStringExtra("EXTRA_KEY_2"));

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString().trim();                               //access the data from the input field
                String date = dateButton.getText().toString().trim();                                 //access the date from the choose date button
                String time = timeButton.getText().toString().trim();

                //access the time from the choose time button
                if (title.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter text", Toast.LENGTH_SHORT).show();   //shows the toast if input field is empty
                }
                else if(date.equals("date") && time.equals("time")){
                    //shows toast if date and time are not selected
                    Toast.makeText(getApplicationContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
                }
                else if(time.equals("time") && !date.equals("date")){
                    Toast.makeText(getApplicationContext(), "Please select time", Toast.LENGTH_SHORT).show();

                }
                else if(!time.equals("time") && date.equals("date")){
                    Toast.makeText(getApplicationContext(), "Please select date", Toast.LENGTH_SHORT).show();

                }
                else {
                    setAlarm(title, date, time);
                    Toast.makeText(getApplicationContext(), "Alarm set for selected date and time", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    private void selectTime() {                                                                     //this method performs the time picker task
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeToNotify = i + ":" + i1 + ":00";                                                        //temp variable to store the time to set alarm
                timeButton.setText(FormatTime(i, i1));                                               //sets the button text as selected time
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }
    private void selectDate() {                                                                     //this method performs the date picker task
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateButton.setText(year + "-" + (month + 1) + "-" + day);                             //sets the selected date as test for button
            }
        }, year, month, day);
        datePickerDialog.show();
    }
    public String FormatTime(int hour, int minute) {                                                //this method converts the time into 12hr format and assigns am or pm
        String time = "";
        String formattedMinute;
        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }
        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }
        return time;


    }
    String getDateInFormat(String date) {
        return date + TIME_FORMATTER + timeToNotify;
    }


    private void setAlarm(String text, String date, String time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);                   //assigning alarm manager object to set alarm
        Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
        intent.putExtra("event", text);                                                       //sending data to alarm class to create channel and notification
        intent.putExtra("time", date);
        intent.putExtra("date", time);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
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
        Intent intentBack = new Intent(getApplicationContext(), BreaksPage.class); //this intent will be called once the setting alarm is complete
        Toast.makeText(getApplicationContext(), "Alarm", Toast.LENGTH_SHORT).show();
        intentBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentBack);                                                                  //navigates from adding reminder activity to mainactivity
    }
    //navigates from adding reminder activity to mainactivity
}



