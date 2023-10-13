package com.example.ebird;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

    private EditText signUpName, signUpEmail, signUpPassword, signUpCpassword;
    private TextView signupRedirectText;
    private Button signUpButton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signUpName = findViewById(R.id.edit_name);
        signUpEmail = findViewById(R.id.edit_email);
        signUpPassword = findViewById(R.id.edit_password);
        signUpCpassword = findViewById(R.id.edit_cpassword);
        signupRedirectText = findViewById(R.id.redirect_btn);
        signUpButton = findViewById(R.id.next_btn);
        progressDialog = new ProgressDialog(this);
        mAuth =FirebaseAuth.getInstance();
        mUser =mAuth.getCurrentUser();

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(register.this, signin.class));
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });
    }
    private void performAuth() {

        String email=signUpEmail.getText().toString();
        String password=signUpPassword.getText().toString();
        String confirmPassword=signUpCpassword.getText().toString();


        if (!email.matches(emailPattern))
        {
            signUpEmail.setError("Enter valid Email");
        } else if (password.isEmpty() || password.length()<6)
        {
            signUpPassword.setError("Enter proper password");
        } else if (!password.equals(confirmPassword))
        {
            signUpCpassword.setError("Password not match both field");
        } else
        {
            progressDialog.setMessage("Please Wait While Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(register.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }

                private void sendUserToNextActivity() {
                    Intent intent=new Intent(register.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
    }
}