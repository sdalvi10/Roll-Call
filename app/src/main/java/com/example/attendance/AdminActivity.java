package com.example.attendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {


    Button buttonCreateNewAccount;
    Button buttonAddStudents;

    public static final String YEAR = "year";
    public static final String DEPARTMENT = "department";
    public static final String SHIFT = "shift";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        buttonCreateNewAccount = (Button) findViewById(R.id.buttonCreateNewAccount);
        buttonAddStudents = (Button) findViewById(R.id.buttonAddStudents);

        buttonCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        buttonAddStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddStudentDialog();
            }
        });

    }

        private void showAddStudentDialog(){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.add_students_dialog, null);
            dialogBuilder.setView(dialogView);

            final Spinner spinnerYear = (Spinner) dialogView.findViewById(R.id.spinnerYear);
            final Spinner spinnerDepartment = (Spinner) dialogView.findViewById(R.id.spinnerDepartment);
            final Spinner spinnerShift = (Spinner) dialogView.findViewById(R.id.spinnerShift);
            Button buttonNext = (Button) dialogView.findViewById(R.id.buttonNext);

            TextView title = new TextView(this);
            title.setText("Enter Details");
            title.setBackgroundColor(Color.DKGRAY);
            title.setPadding(10, 10, 10, 10);
            title.setGravity(Gravity.CENTER);
            title.setTextColor(Color.WHITE);
            title.setTextSize(20);
            dialogBuilder.setCustomTitle(title);

            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String year = spinnerYear.getSelectedItem().toString();
                    String department = spinnerDepartment.getSelectedItem().toString();
                    String shift = spinnerShift.getSelectedItem().toString();

                    Intent intent = new Intent(AdminActivity.this, AdminAddStudent.class);
                    intent.putExtra(YEAR, year);
                    intent.putExtra(DEPARTMENT, department);
                    intent.putExtra(SHIFT, shift);

                    alertDialog.dismiss();

                    startActivity(intent);


                }
            });
        }
}
