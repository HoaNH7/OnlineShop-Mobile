package com.example.projectprm.Activitys;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectprm.Models.User;
import com.example.projectprm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProifileActivity extends AppCompatActivity {


    TextView profileName, profileEmail, profileUsername, profilePhone, profileAdress;
    TextView titleName, titleUsername;
    Button editProfile;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proifile);

        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileUsername = findViewById(R.id.profileUsername);
        profilePhone = findViewById(R.id.profilePhone);
        profileAdress = findViewById(R.id.profileAddress);
        titleName = findViewById(R.id.titleName);
        titleUsername = findViewById(R.id.titleUsername);
        editProfile = findViewById(R.id.editButton);
        showAllUserData();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProifileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }
    public void showAllUserData(){
        auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child(id).child("name").getValue(String.class);
                String email = dataSnapshot.child(id).child("email").getValue(String.class);
                String address = dataSnapshot.child(id).child("address").getValue(String.class);
                String phone = dataSnapshot.child(id).child("phone").getValue(String.class);
                String username = dataSnapshot.child(id).child("username").getValue(String.class);
                String titleNamee = dataSnapshot.child(id).child("name").getValue(String.class);
                String titleUserr = dataSnapshot.child(id).child("username").getValue(String.class);
                profileName.setText(name);
                profileEmail.setText(email);
                profileUsername.setText(username);
                profilePhone.setText(phone);
                profileAdress.setText(address);
                titleName.setText(titleNamee);
                titleUsername.setText(titleUserr);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
//    public void passUserData(){
//        String userUsername = profileUsername.getText().toString().trim();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
//        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
//                    String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
//                    String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
//                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
//                    Intent intent = new Intent(ProifileActivity.this, EditProfileActivity.class);
//                    intent.putExtra("name", nameFromDB);
//                    intent.putExtra("email", emailFromDB);
//                    intent.putExtra("username", usernameFromDB);
//                    intent.putExtra("password", passwordFromDB);
//                    startActivity(intent);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
//    }
}