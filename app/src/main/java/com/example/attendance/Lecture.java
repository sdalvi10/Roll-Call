package com.example.attendance;

public class Lecture {
    private String lectureId;
    private String subject;
    private String year;
    private String department;
    private String shift;
    private String date;
    private String time;

    public Lecture() {

    }

    public Lecture(String lectureId, String date, String time) {
        this.lectureId = lectureId;
//        this.subject = subject;
//        this.year = year;
//        this.department = department;
//        this.shift = shift;
        this.date = date;
        this.time = time;
    }

    public String getLectureId() {
        return lectureId;
    }

//    public String getSubject() {
//        return subject;
//    }
//
//    public String getYear() {
//        return year;
//    }
//
//    public String getDepartment() {
//        return department;
//    }
//
//    public String getShift() {
//        return shift;
//}

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
