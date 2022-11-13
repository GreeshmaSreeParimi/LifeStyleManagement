package com.example.lifestyle_management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class EditBreaksPage extends AppCompatDialogFragment {

    private static String DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static char TIME_FORMATTER = 'T';
    private EditBreaksPage.EditBreaksPageListener listener;
    private DatePickerDialog datePickerDialog;
    private String picked_am_pm;


    EditText editTitle;
    Button dateButton, timeButton;
    String timeToNotify;
    private int picked_day, picked_month, picked_year,picked_hour,picked_minute;

//    public static EditBreaksPage newInstance(String msg) {
//        EditBreaksPage fragment = new EditBreaksPage();
//
//        Bundle bundle = new Bundle();
//       // bundle.putString("", msg); // set msg here
//        fragment.setArguments(bundle);
//
//        return fragment;
//    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_break_dialog,null);
        builder.setView(dialogView).setTitle("Edit Information")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String breakTitle = break_title.getText().toString();
//                        listener.saveBreaksData(breakTitle, picked_year, picked_month, picked_day, picked_hour,
//                                picked_minute, picked_am_pm);

                        String title = editTitle.getText().toString().trim();                               //access the data from the input field
                        String date = dateButton.getText().toString().trim();                                 //access the date from the choose date button
                        String time = timeButton.getText().toString().trim();
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
                        listener.saveBreaksData(title,picked_year,picked_month,picked_day,picked_hour,picked_minute,picked_am_pm);
                    }

                });
        String Break_Name= getArguments().getString("Break_Name");
        String Break_date= getArguments().getString("Break_date");
        String Break_time= getArguments().getString("Break_time");



        editTitle=(EditText)dialogView.findViewById(R.id.editTitle) ;
        dateButton=(Button)dialogView.findViewById(R.id.btnDate);
        timeButton=(Button)dialogView.findViewById(R.id.btnTime);
        editTitle.setText(Break_Name);
        dateButton.setText(Break_date);
        timeButton.setText(Break_time);

        //selectDate(dialogView);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });
        //time_picker_btn = (Button) dialogView.findViewById(R.id.time_picker);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });


        return builder.create();

    }

    private void selectTime() {                                                                     //this method performs the time picker task
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateButton.setText(year + "-" + (month + 1) + "-" + day);//sets the selected date as test for button
                picked_year=year;
                picked_month=(month+1);
                picked_day=day;
            }

        }, year, month, day);
        datePickerDialog.show();
    }
    public String FormatTime(int hour, int minute) {                                                //this method converts the time into 12hr format and assigns am or pm
        String time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
            picked_minute=minute;
        } else {
            formattedMinute = "" + minute;
            picked_minute=minute;
        }
        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
            picked_am_pm="AM";
            picked_hour=12;

        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
            picked_am_pm="AM";
            picked_hour=hour;
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
            picked_am_pm="PM";
            picked_hour=hour;
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
            picked_am_pm="PM";
            picked_hour=temp;
        }

        return time;


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
            listener = (EditBreaksPage.EditBreaksPageListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Implement EditBreaksPageListener");
        }
    }

    public interface  EditBreaksPageListener {
        void saveBreaksData(String title,int year,int month,int day,int hour,int min,String am_pm);

    }
}




