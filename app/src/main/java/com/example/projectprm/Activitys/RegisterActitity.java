package com.example.projectprm.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectprm.Models.User;
import com.example.projectprm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActitity extends AppCompatActivity {
    EditText registerName, registerEmail, registerPass, registerRepass, registerUserName;
    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_actitity);

        registerName = findViewById(R.id.registerName);
        registerUserName = findViewById(R.id.registerUserName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPass = findViewById(R.id.registerPass);
        registerRepass = findViewById(R.id.registerRePass);

    }

    public void redirectLogin(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void register(View v){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        auth = FirebaseAuth.getInstance();
        
        String username = registerUserName.getText().toString();
        String email = registerEmail.getText().toString();
        String password = registerPass.getText().toString();
        String phone = "";
        String address ="";
        String name = registerName.getText().toString();
        String repass = registerRepass.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Vui lòng điền tên", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Vui lòng điền tên đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng điền email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8) {
            Toast.makeText(this, "Mật khẩu phải chứa ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(repass)) {
            Toast.makeText(this, "Mật khẩu và Mật khẩu nhập lại không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(username,name,email,password, address,phone);
                    String id = task.getResult().getUser().getUid();
                    reference.child(id).setValue(user);
                    Toast.makeText(RegisterActitity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActitity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterActitity.this, "Chấm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}