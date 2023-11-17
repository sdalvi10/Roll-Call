package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String SUBJECT_ID = "subjectId";
    public static final String USER = "user";
    public static final String YEAR = "year";
    public static final String DEPARTMENT = "department";
    public static final String SHIFT = "shift";
    public static final String SUBJECT = "subjectName";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String ROLL_NO_RANGE = "rollNoRange";


    Spinner spinnerSubject;
    Spinner spinnerYear;
    Spinner spinnerDepartment;
    Spinner spinnerShift;

    Button buttonConfirm;
    EditText editTextRollNoFrom;
    EditText editTextRollNoTo;


    DatabaseReference databaseReference;

    private String initialTextRollNoFrom;
    private String initialTextRollNoTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Please Enter Details");

        spinnerSubject = (Spinner) findViewById(R.id.spinnerSubject);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        spinnerDepartment = (Spinner) findViewById(R.id.spinnerDepartment);
        spinnerShift = (Spinner) findViewById(R.id.spinnerShift);
        editTextRollNoFrom = (EditText) findViewById(R.id.editTextRollNoFrom);
        editTextRollNoTo = (EditText) findViewById(R.id.editTextRollNoTo);


        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_layout, R.array.subject);

        initialTextRollNoFrom = editTextRollNoFrom.getText().toString().trim();
        initialTextRollNoTo = editTextRollNoTo.getText().toString().trim();

        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);

        Intent i = getIntent();
        final String user = i.getStringExtra(MySubjectActivity.USER);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    int rollNoFrom = Integer.parseInt(editTextRollNoFrom.getText().toString().trim());
                    int rollNoTo = Integer.parseInt(editTextRollNoTo.getText().toString().trim());
                    int totalRollNo = (rollNoTo - rollNoFrom) + 1;
                    String subjectName = spinnerSubject.getSelectedItem().toString();
                    String year = spinnerYear.getSelectedItem().toString();
                    String department = spinnerDepartment.getSelectedItem().toString();
                    String shift = spinnerShift.getSelectedItem().toString();


                    String rollNoRange = Integer.toString(totalRollNo);

                    Intent intent = new Intent(ProfileActivity.this, MySubjectActivity.class);
                    intent.putExtra(USER, user);
                    intent.putExtra(SUBJECT, subjectName);
                    intent.putExtra(YEAR, year);
                    intent.putExtra(DEPARTMENT, department);
                    intent.putExtra(SHIFT, shift);
                    intent.putExtra(ROLL_NO_RANGE, rollNoRange);

                    databaseReference = FirebaseDatabase.getInstance().getReference("my-subjects");
                    String subjectId = databaseReference.push().getKey();
                    intent.putExtra(SUBJECT_ID, subjectId);

                    MySubject mySubject = new MySubject(subjectId, subjectName, year, department, shift, rollNoRange);

                    databaseReference.child(user).child(subjectId).setValue(mySubject);


                    databaseReference = FirebaseDatabase.getInstance().getReference("students").child(subjectId);

                    for (int i = 0; i < totalRollNo; i++) {
                        Student student = new Student(Integer.toString(i + 1), "");
                        databaseReference.child(Integer.toString(i + 1)).setValue(student);
                    }
                    startActivity(intent);
                }
        });

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.subject,
                R.layout.color_spinner_layout
        );

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);

        spinnerSubject.setAdapter(adapter);

        spinnerSubject.setOnItemSelectedListener(this);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.year,
                R.layout.color_spinner_layout
        );

        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);

        spinnerYear.setAdapter(adapter2);

        spinnerYear.setOnItemSelectedListener(this);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(
                this,
                R.array.department,
                R.layout.color_spinner_layout
        );

        adapter3.setDropDownViewResource(R.layout.spinner_dropdown_layout);

        spinnerDepartment.setAdapter(adapter3);

        spinnerDepartment.setOnItemSelectedListener(this);

        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(
                this,
                R.array.shift,
                R.layout.color_spinner_layout
        );

        adapter4.setDropDownViewResource(R.layout.spinner_dropdown_layout);

        spinnerShift.setAdapter(adapter4);

        spinnerShift.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

