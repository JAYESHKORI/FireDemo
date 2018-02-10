package com.example.jayesh.firedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etEmail,etPwd;
    private Button btnLogin;
    private TextView tvSignup;
    private ProgressDialog prgdlg;
    private FirebaseAuth fbauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPwd = (EditText) findViewById(R.id.etPwd);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        tvSignup = (TextView)findViewById(R.id.tvSignup);

        prgdlg = new ProgressDialog(this);
        fbauth = FirebaseAuth.getInstance();

        if(fbauth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        btnLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnLogin)
        {
            UserLogin();
        }
        if(view == tvSignup)
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    private void UserLogin()
    {
        String email = etEmail.getText().toString().trim();
        String password = etPwd.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        prgdlg.setMessage("Loggin in...");
        prgdlg.show();
        fbauth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        prgdlg.dismiss();
                        if(task.isSuccessful())
                        {
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
