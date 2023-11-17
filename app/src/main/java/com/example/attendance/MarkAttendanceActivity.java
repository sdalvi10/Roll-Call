package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MarkAttendanceActivity extends AppCompatActivity{

    ListView listViewStudents;
    List<Student> students;
    List<Student> studentsWithAttendance;
    DatabaseReference databaseStudents;
    DatabaseReference databaseRecords;
    DatabaseReference databaseAttendance;

    DatePickerDialog datePickerDialog;

    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;

    ImageButton imageButtonSetDate;
    ImageButton imageButtonSetTime;
    TextView textViewDate;
    TextView textViewTime;

    Button buttonSave;

    private String initialTextDate;
    private String initialTextTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        setTitle("Mark Attendance");

        listViewStudents = findViewById(R.id.listViewStudents);
        buttonSave = findViewById(R.id.buttonSave);
        students = new ArrayList<>();
        studentsWithAttendance = new ArrayList<>();



        imageButtonSetTime = findViewById(R.id.imageButtonSetTime);
        imageButtonSetDate = findViewById(R.id.imageButtonSetDate);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewTime = (TextView) findViewById(R.id.textViewTime);

        initialTextDate = textViewDate.getText().toString();
        initialTextTime = textViewTime.getText().toString();

        Intent i = getIntent();
        final String user = i.getStringExtra(MySubjectActivity.USER);


        imageButtonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(MarkAttendanceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                textViewDate.setText(day + "-" + (month+1) + "-" + year);
                            }
                        },Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        imageButtonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(MarkAttendanceActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                            hourOfDay = hourOfDay - 12;
                        } else {
                            amPm = "AM";
                        }
                        textViewTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + " " + amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });


        Intent intent = getIntent();
        //final String user = intent.getStringExtra(SubjectCRUDActivity.USER);

        //******final String subjectId = intent.getStringExtra(SubjectCRUDActivity.SUBJECT_ID);

        String subjectId = intent.getStringExtra(SubjectListAdapter.SUBJECT_ID);

        final String subject = intent.getStringExtra(ProfileActivity.SUBJECT);
        final String year = intent.getStringExtra(ProfileActivity.YEAR);
        final String department = intent.getStringExtra(ProfileActivity.DEPARTMENT);
        final String shift = intent.getStringExtra(ProfileActivity.SHIFT);
        //final String date = intent.getStringExtra(ProfileActivity.DATE);
        //final String time = intent.getStringExtra(ProfileActivity.TIME);
       // String dateAndTime = date + " " + time;

        databaseStudents = FirebaseDatabase.getInstance().getReference("students").child(subjectId);
        databaseRecords = FirebaseDatabase.getInstance().getReference().child("lecture-records").child(subjectId);
        //changed
        databaseAttendance = FirebaseDatabase.getInstance().getReference().child("attendance-records").child(subjectId);


        databaseStudents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students.clear();
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    Student student = studentSnapshot.getValue(Student.class);
                    students.add(student);
                }
                StudentListAdapter adapter = new StudentListAdapter(MarkAttendanceActivity.this, students);
                listViewStudents.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*final String lectureId = databaseRecords.push().getKey();
        //final String lectureId = databaseAttendance.push().getKey();
        Lecture lecture = new Lecture(lectureId, "", "" );
        //databaseAttendance.child(lectureId).setValue(lecture);

        databaseRecords.child(lectureId).setValue(lecture);*/



        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(textViewDate.getText().toString() == initialTextDate || textViewTime.getText().toString() == initialTextTime){
                    Toast.makeText(MarkAttendanceActivity.this, "Please set date and time before saving", Toast.LENGTH_SHORT).show();
                }
                else{
                    final String lectureId = databaseRecords.push().getKey();
                    //final String lectureId = databaseAttendance.push().getKey();
                    Lecture lecture = new Lecture(lectureId, textViewDate.getText().toString(), textViewTime.getText().toString());
                    //databaseAttendance.child(lectureId).setValue(lecture);

                    databaseRecords.child(lectureId).setValue(lecture);



                    StudentListAdapter adapter = new StudentListAdapter(MarkAttendanceActivity.this, students);

                    for(int i = 1; i <= students.size(); i++){
                        databaseAttendance.child(lectureId).child(Integer.toString(i)).setValue(students.get(i-1));
                    }

                    students = adapter.getStudents();
                    for(int i = 0; i < students.size(); i++){
                        databaseAttendance.child(lectureId).child(Integer.toString(i+1)).child("studentPresence").setValue(students.get(i).getStudentPresence());
                    }

                    Toast.makeText(MarkAttendanceActivity.this, "Attendance has been saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MarkAttendanceActivity.this,MySubjectActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
