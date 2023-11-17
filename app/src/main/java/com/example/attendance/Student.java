package com.example.attendance;

public class Student {
    private String studentRollNo;
    private String studentPresence;

    public Student(){

    }

    public Student(String studentRollNo) {
        this.studentRollNo = studentRollNo;
    }

    public Student(String studentRollNo, String studentPresence) {
        this.studentRollNo = studentRollNo;
        this.studentPresence = studentPresence;
    }

    public String getStudentRollNo() {
        return studentRollNo;
    }


    public String getStudentPresence(){
        return studentPresence;
    }

    public void setStudentPresence(String studentPresence) {
        this.studentPresence = studentPresence;
    }
}
