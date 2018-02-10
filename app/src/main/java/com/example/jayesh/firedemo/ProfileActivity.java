package com.example.jayesh.firedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth fbauth;
    private TextView tvUserEmail;
    private Button btnLogout;

    private DatabaseReference dbReference;
    private EditText FullName,EnrollNo;
    private Button SaveInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fbauth = FirebaseAuth.getInstance();
        if(fbauth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        tvUserEmail = (TextView)findViewById(R.id.tvUserEmail);
        btnLogout = (Button)findViewById(R.id.btnLogout);

        dbReference = FirebaseDatabase.getInstance().getReference();
        FullName = (EditText)findViewById(R.id.etFullName);
        EnrollNo = (EditText)findViewById(R.id.etEnrollNo);
        SaveInfo = (Button)findViewById(R.id.btnSaveInfo);
        FirebaseUser user = fbauth.getCurrentUser();
        tvUserEmail.setText("Welcome "+user.getEmail());
        btnLogout.setOnClickListener(this);
        SaveInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogout)
        {
            fbauth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        if(view == SaveInfo)
        {
            SaveUserInformation();
        }
    }

    private void SaveUserInformation()
    {
        String fullname = FullName.getText().toString().trim();
        Long enrollno = Long.parseLong(EnrollNo.getText().toString().trim());

        UserInformation userinfo = new UserInformation(fullname,enrollno);
        FirebaseUser user = fbauth.getCurrentUser();
        dbReference.child(user.getUid()).setValue(userinfo);
        Toast.makeText(this,"Data Saved Successfully",Toast.LENGTH_SHORT).show();
    }
}
