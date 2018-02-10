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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail,etPwd;
    private Button btnRegister;
    private TextView tvSignin;
    private ProgressDialog prgdlg;
    private FirebaseAuth fbauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPwd = (EditText) findViewById(R.id.etPwd);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        tvSignin = (TextView)findViewById(R.id.tvSignin);

        prgdlg = new ProgressDialog(this);
        fbauth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(this);
        tvSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnRegister)
        {
            RegisterUser();
        }
        if(view == tvSignin)
        {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
    private void RegisterUser()
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
        prgdlg.setMessage("Registering User...");
        prgdlg.show();
        fbauth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this,"Registerd Successfully",Toast.LENGTH_SHORT).show();
                            prgdlg.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Could not Register. Please try Again...",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
