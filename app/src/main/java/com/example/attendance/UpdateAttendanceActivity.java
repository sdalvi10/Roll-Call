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

public class UpdateAttendanceActivity extends AppCompatActivity {



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
        setContentView(R.layout.activity_update_attendance);

        setTitle("Mark Attendance");

        listViewStudents = findViewById(R.id.updateAttendance_listViewStudents);
        buttonSave = findViewById(R.id.updateAttendance_buttonSave);
        students = new ArrayList<>();
        //studentsWithAttendance = new ArrayList<>();

        // int flg = 0;


        imageButtonSetTime = findViewById(R.id.updateAttendance_imageButtonSetTime);
        imageButtonSetDate = findViewById(R.id.updateAttendance_imageButtonSetDate);
        textViewDate = (TextView) findViewById(R.id.updateAttendance_textViewDate);
        textViewTime = (TextView) findViewById(R.id.updateAttendance_textViewTime);

        Intent i = getIntent();
        //final String user = i.getStringExtra(MySubjectActivity.USER);

        initialTextDate = textViewDate.getText().toString();
        initialTextTime = textViewTime.getText().toString();

        imageButtonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(UpdateAttendanceActivity.this,
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
                timePickerDialog = new TimePickerDialog(UpdateAttendanceActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        //Intent intent = getIntent();
        //final String user = intent.getStringExtra(SubjectCRUDActivity.USER);
        final String subjectId = i.getStringExtra(LectureListAdapter.SUBJECT_ID);
        /*final String subject = intent.getStringExtra(ProfileActivity.SUBJECT);
        final String year = intent.getStringExtra(ProfileActivity.YEAR);
        final String department = intent.getStringExtra(ProfileActivity.DEPARTMENT);
        final String shift = intent.getStringExtra(ProfileActivity.SHIFT);
        final String date = intent.getStringExtra(ProfileActivity.DATE);
        final String time = intent.getStringExtra(ProfileActivity.TIME);
        String dateAndTime = date + " " + time;*/
        
        final String lectureId = i.getStringExtra(LectureListAdapter.LECTURE_ID);


        databaseStudents = FirebaseDatabase.getInstance().getReference("students").child(subjectId);



        databaseRecords = FirebaseDatabase.getInstance().getReference("lecture-records").child(subjectId).child(lectureId);

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
                StudentListAdapter adapter = new StudentListAdapter(UpdateAttendanceActivity.this, students);
                listViewStudents.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(textViewDate.getText().toString() == initialTextDate || textViewTime.getText().toString() == initialTextTime){
                    Toast.makeText(UpdateAttendanceActivity.this, "Please set date and time before saving", Toast.LENGTH_SHORT).show();
                }
                else{

                    databaseRecords.child("date").setValue(textViewDate.getText().toString());
                    databaseRecords.child("time").setValue(textViewTime.getText().toString());

                    StudentListAdapter adapter = new StudentListAdapter(UpdateAttendanceActivity.this, students);

                    for(int i = 1; i <= students.size(); i++){
                        databaseAttendance.child(lectureId).child(Integer.toString(i)).setValue(students.get(i-1));
                    }

                    students = adapter.getStudents();
                    for(int i = 0; i < students.size(); i++){
                        databaseAttendance.child(lectureId).child(Integer.toString(i+1)).child("studentPresence").setValue(students.get(i).getStudentPresence());
                    }

                    Toast.makeText(UpdateAttendanceActivity.this, "Attendance has been updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateAttendanceActivity.this,MySubjectActivity.class);
                    startActivity(intent);
                }



            }
        });

    }

}
