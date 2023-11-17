package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.attendance.LoginActivity.user;

public class MySubjectActivity extends AppCompatActivity {

    ListView listViewSubject;

    DatabaseReference databaseReference;
    List<MySubject> subjects;
    ImageButton imageButtonAdd;



    public static final String LECTURE_ID = "lectureid";
    public static final String USER = "user";

    public static final String SUBJECT_ID = "subjectId";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subject);

        setTitle("My Subjects");

        final Intent intent = getIntent();
        /*final String subjectId = intent.getStringExtra(ProfileActivity.SUBJECT_ID);
        String subject = intent.getStringExtra(ProfileActivity.SUBJECT);
        String year = intent.getStringExtra(ProfileActivity.YEAR);
        String shift = intent.getStringExtra(ProfileActivity.SHIFT);
        String department = intent.getStringExtra(ProfileActivity.DEPARTMENT);
        String rollNoRange = intent.getStringExtra(ProfileActivity.ROLL_NO_RANGE);*/

       // MySubject mySubject = new MySubject(subject,year,shift,department,rollNoRange);




        listViewSubject = (ListView) findViewById(R.id.mySubjectActivityLayout_listViewSubject);
        subjects = new ArrayList<>();
        imageButtonAdd = findViewById(R.id.imageButtonAdd);


        /*Intent i = getIntent();
        final String user = i.getStringExtra(LoginActivity.USER);*/

        final String user = LoginActivity.getUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("my-subjects").child(user);




        listViewSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MySubject subject = subjects.get(i);
                Intent intent = new Intent(MySubjectActivity.this, SubjectCRUDActivity.class);
                intent.putExtra(USER, user);
                intent.putExtra(SUBJECT_ID, subject.getSubjectId());
                startActivity(intent);
            }
        });

        /*listViewLecture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lecture lecture = subjects.get(i);
                Intent intent = new Intent(MySubjectActivity.this, DisplayAttendanceActivity.class);
                intent.putExtra(LECTURE_ID, lecture.getLectureId());
                startActivity(intent);
            }
        });*/


        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MySubjectActivity.this, ProfileActivity.class);
                intent1.putExtra(USER, user);
                startActivity(intent1);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subjects.clear();
                for(DataSnapshot subjectSnapshot : dataSnapshot.getChildren()){
                    MySubject subject = subjectSnapshot.getValue(MySubject.class);
                    subjects.add(subject);
                }
                SubjectListAdapter adapter = new SubjectListAdapter(MySubjectActivity.this, subjects, user);
                listViewSubject.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //String letter = Character.toString(text.charAt(0));
    }


