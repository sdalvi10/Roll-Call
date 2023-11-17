package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubjectCRUDActivity extends AppCompatActivity {


    Button buttonMarkAttendance;
    Button buttonDisplayLectures;
    Button buttonUpdate;

    public static final String USER = "user";

    public static final String SUBJECT_ID = "subjectId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_crud);



        buttonMarkAttendance = findViewById(R.id.subjectCRUDActivity_buttonMarkAttendance);
        buttonDisplayLectures = findViewById(R.id.subjectCRUDActivity_buttonDisplayLectures);
        buttonUpdate = findViewById(R.id.subjectCRUDActivity_buttonUpdate);

        Intent i = getIntent();
        final String user = i.getStringExtra(MySubjectActivity.USER);
       // String flg = i.getStringExtra(MarkAttendanceActivity.FLAG);
        final String subjectId = i.getStringExtra(MySubjectActivity.SUBJECT_ID);

        /*if(flg == "true"){
            Intent ii = new Intent(SubjectCRUDActivity.this, MySubjectActivity.class);
            startActivity(ii);
        }*/

        buttonMarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SubjectCRUDActivity.this, MarkAttendanceActivity.class);
                intent.putExtra(USER, user);
                intent.putExtra(SUBJECT_ID, subjectId);
                startActivity(intent);
            }
        });

        buttonDisplayLectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubjectCRUDActivity.this, DisplayLecturesActivity.class);
                intent.putExtra(SUBJECT_ID, subjectId);
                startActivity(intent);
            }
        });



        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubjectCRUDActivity.this, UpdateLectureActivity.class);
                intent.putExtra(SUBJECT_ID, subjectId);
                startActivity(intent);
            }
        });




    }
}
