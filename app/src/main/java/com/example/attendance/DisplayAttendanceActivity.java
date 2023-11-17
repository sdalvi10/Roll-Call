package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayAttendanceActivity extends AppCompatActivity {

    ListView listViewAttendance;
    DatabaseReference databaseAttendance;
    List<Student> students;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_attendance);

        setTitle("Attendance Record");

        listViewAttendance = (ListView) findViewById(R.id.displayAttendance_listViewStudents);

        final Intent intent = getIntent();

        students = new ArrayList<>();

        //String lectureId = DisplayLecturesActivity.getLectureId();

        String subjectId = intent.getStringExtra(LectureListAdapter.SUBJECT_ID);
        String lectureId = intent.getStringExtra(LectureListAdapter.LECTURE_ID);

        databaseAttendance = FirebaseDatabase.getInstance().getReference("attendance-records").child(subjectId).child(lectureId);


        databaseAttendance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students.clear();
                for(DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                    Student student = studentSnapshot.getValue(Student.class);
                    students.add(student);
                }
                AttendanceListAdapter adapter = new AttendanceListAdapter(DisplayAttendanceActivity.this, students);
                listViewAttendance.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
