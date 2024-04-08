package com.example.projectprm.Activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm.Adapter.CartAdapter;
import com.example.projectprm.Adapter.CartProducAdapter;
import com.example.projectprm.Helper.ManagmentCart;
import com.example.projectprm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class CartProductActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ManagmentCart managmentCart;
    TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt, cartAddress;
    private double tax;
    private ScrollView scrollView;
    private ImageView backBtn;
    public static final String CHANNEL_1_ID = "channel1";
    Button order;

    FirebaseAuth auth;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managmentCart = new ManagmentCart(this);

        initView();
        setVariable();
        calculateCart();
        initList();
        getAddress();
        backBtn.setOnClickListener(v -> finish());
    }

    private void initList() {
        if (managmentCart.getListCart1().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CartProducAdapter(managmentCart.getListCart1(), this, () -> calculateCart());
        recyclerView.setAdapter(adapter);
    }

    private void calculateCart() {
        double percentTax = 0.02;
        double delivery = 20000;
        tax = Math.round(managmentCart.getTotalFee1() * percentTax * 100.0) / 100.0;

        double total = Math.round((managmentCart.getTotalFee1() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee1() * 100) / 100;

        totalFeeTxt.setText("đ" + itemTotal);
        taxTxt.setText("đ" + tax);
        deliveryTxt.setText("đ" + delivery);
        totalTxt.setText("đ" + total);
    }

    public void getAddress() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                auth = FirebaseAuth.getInstance();
                String id = auth.getCurrentUser().getUid();
                String address = dataSnapshot.child(id).child("address").getValue(String.class);
                cartAddress.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setVariable() {
        backBtn.setOnClickListener(v -> finish());
    }

    private void initView() {
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        recyclerView = findViewById(R.id.view2);
        scrollView = findViewById(R.id.scrollView2);
        backBtn = findViewById(R.id.backBtn);
        emptyTxt = findViewById(R.id.emptyTxt);
        cartAddress = findViewById(R.id.cartAddress);
        order = findViewById(R.id.order);
        order.setOnClickListener(v -> {
        });
    }



}