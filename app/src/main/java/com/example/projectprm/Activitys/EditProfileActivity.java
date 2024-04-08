package com.example.projectprm.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectprm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    EditText editName, editEmail, editUsername, editPhone, editAddress;
    Button saveButton;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        reference = FirebaseDatabase.getInstance().getReference("users");
        editName = (EditText) findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPhone = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);
        editAddress = findViewById(R.id.editAddress);
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id = auth.getCurrentUser().getUid();
                String name = dataSnapshot.child(id).child("name").getValue(String.class);
                String email = dataSnapshot.child(id).child("email").getValue(String.class);
                String address = dataSnapshot.child(id).child("address").getValue(String.class);
                String phone = dataSnapshot.child(id).child("phone").getValue(String.class);
                String username = dataSnapshot.child(id).child("username").getValue(String.class);
                editName.setText(name);
                editEmail.setText(email);
                editUsername.setText(username);
                editPhone.setText(phone);
                editAddress.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
    });
        saveButton.setOnClickListener(v -> {
            String id = auth.getCurrentUser().getUid();
            String name = editName.getText().toString();
            String email = editEmail.getText().toString();
            String phone = editPhone.getText().toString();
            String address = editAddress.getText().toString();
            String username = editUsername.getText().toString();
            reference.child(id).child("name").setValue(name);
            reference.child(id).child("address").setValue(address);
            reference.child(id).child("phone").setValue(phone);
            reference.child(id).child("username").setValue(username);
            reference.child(id).child("email").setValue(email);
            Intent intent = new Intent(this, ProifileActivity.class);
            startActivity(intent);

        });
    }
}