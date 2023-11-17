package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAddStudent extends AppCompatActivity {

    EditText editTextStudentName;
    Button buttonAddStudent;
    private int count = 0;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_student);

        editTextStudentName = findViewById(R.id.editTextStudentName);
        buttonAddStudent = findViewById(R.id.buttonAddStudent);

        Intent intent = getIntent();
        String year = intent.getStringExtra(AdminActivity.YEAR);
        String department = intent.getStringExtra(AdminActivity.DEPARTMENT);
        String shift = intent.getStringExtra(AdminActivity.SHIFT);

        databaseReference = FirebaseDatabase.getInstance().getReference("students").child(year).child(department).child(shift);

        /*buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                addStudent(count);
            }
        });*/

    }

   /* private void addStudent(int count){
        String name = editTextStudentName.getText().toString().trim();

        if(!TextUtils.isEmpty(name)){
            Student student = new Student(Integer.toString(count), name, "");
            databaseReference.child(Integer.toString(count)).setValue(student);
            Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
        }
    }*/
}
