package com.example.projectprm.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectprm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText loginName, loginPass;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginName = findViewById(R.id.loginName);
        loginPass = findViewById(R.id.loginPass);

    }

    public void login(View v){
        if (!validateUsername() | !validatePassword()) {
        } else {
            checkUser();
        }
    }

    public Boolean validateUsername() {
        String val = loginName.getText().toString();
        if (val.isEmpty()) {
            loginName.setError("Username cannot be empty");
            return false;
        } else {
            loginName.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = loginPass.getText().toString();
        if (val.isEmpty()) {
            loginPass.setError("Password cannot be empty");
            return false;
        } else {
            loginPass.setError(null);
            return true;
        }
    }
    public void checkUser(){
        auth = FirebaseAuth.getInstance();
        String userEmail = loginName.getText().toString().trim();
        String userPassword = loginPass.getText().toString().trim();
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Email or Passwrod is wrong" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void redirectRegister(View v){
        Intent intent = new Intent(this, RegisterActitity.class);
        startActivity(intent);
    }
}