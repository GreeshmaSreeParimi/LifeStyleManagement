package com.example.lifestyle_management;

public class Breaks_Storage_Model {

    private String break_name;
    private String break_time;
    private String break_date;

    // Constructor
    public Breaks_Storage_Model(String break_name, String break_time, String break_date) {
        this.break_name = break_name;
        this.break_time = break_time;
        this.break_date = break_date;
    }

    // Getter and Setter
    public String getBreak_name() {
        return break_name;
    }

    public void setBreak_name(String break_name) {
        this.break_name = break_name;
    }

    public String getBreak_time() {
        return break_time;
    }

    public void setBreak_time(int course_rating) {
        this.break_time = break_time;
    }
    public String getBreak_date() {
        return break_date;
    }
    public void setBreak_date(String break_date) {
        this.break_date = break_date;
    }
}
