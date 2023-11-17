package com.example.attendance;

public class MySubject {

    private String subjectId;
    private String subjectName;
    private String year;
    private String department;
    private String shift;
    private String rollNoRange;

    public MySubject(){

    }

    public MySubject(String subjectId, String subjectName, String year, String department, String shift, String rollNoRange) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.year = year;
        this.department = department;
        this.shift = shift;
        this.rollNoRange = rollNoRange;
    }


    public String getSubjectId() {
        return subjectId;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public String getYear() {
        return year;
    }

    public String getDepartment() {
        return department;
    }

    public String getShift() {
        return shift;
    }

    public String getRollNoRange() {
        return rollNoRange;
    }
}
