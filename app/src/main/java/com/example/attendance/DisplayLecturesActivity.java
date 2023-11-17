package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayLecturesActivity extends AppCompatActivity {

    ListView listViewLectures;
    List<Lecture> lectures;
    DatabaseReference databaseReference;

    public static final String SUBJECT_ID = "subjectId";
    public static final String LECTURE_ID = "lectureId";

    //public static String lectureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lectures);

        setTitle("Lectures");

        lectures = new ArrayList<>();
        listViewLectures = findViewById(R.id.displayLectures_listViewLectures);

        Intent i = getIntent();
        final String subjectId = i.getStringExtra(SubjectListAdapter.SUBJECT_ID);

        databaseReference = FirebaseDatabase.getInstance().getReference("lecture-records").child(subjectId);


        listViewLectures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lecture lecture = lectures.get(i);
                String lectureId = lecture.getLectureId();
                Intent intent = new Intent(DisplayLecturesActivity.this, DisplayAttendanceActivity.class);
                intent.putExtra(SUBJECT_ID, subjectId);
                intent.putExtra(LECTURE_ID, lectureId);

                //lectureId = lecture.getLectureId();
                startActivity(intent);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lectures.clear();
                for(DataSnapshot lectureSnapshot : dataSnapshot.getChildren()){
                    Lecture lecture = lectureSnapshot.getValue(Lecture.class);
                    lectures.add(lecture);
                }
                LectureListAdapter adapter = new LectureListAdapter(DisplayLecturesActivity.this, lectures, subjectId);
                listViewLectures.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

   /* @Override
    protected void onStart() {
        super.onStart();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lectures.clear();
                for(DataSnapshot lectureSnapshot : dataSnapshot.getChildren()){
                    Lecture lecture = lectureSnapshot.getValue(Lecture.class);
                    lectures.add(lecture);
                }
                LectureListAdapter adapter = new LectureListAdapter(DisplayLecturesActivity.this, lectures, subjectId);
                listViewLectures.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/

    }

   /* public static String getLectureId(){
        return lectureId;
    }*/
