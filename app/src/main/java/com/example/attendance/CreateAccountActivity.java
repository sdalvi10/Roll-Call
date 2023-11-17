package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class CreateAccountActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextRe_enterPassword;
    Button buttonCreateAccount;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextRe_enterPassword = (EditText) findViewById(R.id.editTextRe_enterPassword);
        buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);


        mAuth = FirebaseAuth.getInstance();

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String re_enterPassword = editTextRe_enterPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Please enter an email");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password can not be empty");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        if(!re_enterPassword.equals(password)){
            editTextRe_enterPassword.setError("Passwords don't match");
            editTextRe_enterPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(CreateAccountActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    editTextEmail.getText().clear();
                    editTextEmail.clearFocus();
                    editTextPassword.getText().clear();
                    editTextPassword.clearFocus();
                    editTextRe_enterPassword.getText().clear();
                    editTextRe_enterPassword.clearFocus();
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(CreateAccountActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(CreateAccountActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(CreateAccountActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
