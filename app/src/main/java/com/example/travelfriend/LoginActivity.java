package com.example.travelfriend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    EditText email,password;
    CheckBox checkBox;
    ImageButton signin;
    Button signup;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        checkBox = (CheckBox)findViewById(R.id.checkbox);

        signin =(ImageButton)findViewById(R.id.signin);

        signup = (Button) findViewById(R.id.signup);

        signin.setOnClickListener(this);

            }

            private void registerUser() {
                String emailF = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

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

                progressDialog.setMessage("Registering User...");
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(emailF,pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //user is successfully registered and logged in
                                    //we will start the profile activity here
                                    //right now lets display a msg
                                    Toast.makeText(LoginActivity.this,"SSUCCESSFULL",Toast.LENGTH_SHORT).show();
                                }else{
                                    //display some message here
                                    Toast.makeText(LoginActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
            }
    @Override
    public void onClick(View view) {

        if(view == signin){
            Log.e("status--","CLICKED");
            registerUser();
        }
        if (view == signup){
            //
            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);
        }
    }
}
