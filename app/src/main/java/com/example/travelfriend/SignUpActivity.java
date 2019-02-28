package com.example.travelfriend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    EditText email,password,name;
    CheckBox checkBox;
    ImageButton signup;
    Button signin;

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);

        checkBox = findViewById(R.id.checkbox);

        signup = findViewById(R.id.signup);

        signin = findViewById(R.id.signin);

        signup.setOnClickListener(this);
        signin.setOnClickListener(this);

    }


    private void registerUser() {
        String emailF = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String nameF = name.getText().toString().trim();

        if (TextUtils.isEmpty(emailF)){
            //empty email
            Toast.makeText(this,"please enter email",Toast.LENGTH_SHORT).show();

            return;
        }

        if(TextUtils.isEmpty(pass)){
            //empty pass
            Toast.makeText(this,"please enter the password",Toast.LENGTH_SHORT).show();

            return;
        }

        if (TextUtils.isEmpty(nameF)){
            Toast.makeText(this,"please enter the name",Toast.LENGTH_SHORT).show();

            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(emailF,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        if(task.isSuccessful()){
                            //user is successfully registered and logged in
                            //we will start the profile activity here
                            //right now lets display a msg
                            Toast.makeText(SignUpActivity.this,"SSUCCESSFULL",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //display some message here
                            Toast.makeText(SignUpActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == signup){
            registerUser();
        }
        if (view == signin){
            //
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }
    }
}
