package com.example.projectprm.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm.Adapter.CartAdapter;
import com.example.projectprm.Helper.ManagmentCart;
import com.example.projectprm.R;

public class CartActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ManagmentCart managmentCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managmentCart = new ManagmentCart(this);
        backBtn.setOnClickListener(v -> {
            finish();
        });

        initView();
        setVariable();
//        calculateCart();
//        initList();
    }

//    private void initList() {
//        if (managmentCart.getListCart().isEmpty()) {
//            emptyTxt.setVisibility(View.VISIBLE);
//            scrollView.setVisibility(View.GONE);
//        } else {
//            emptyTxt.setVisibility(View.GONE);
//            scrollView.setVisibility(View.VISIBLE);
//        }
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        adapter = new CartAdapter(managmentCart.getListCart(), this, () -> calculateCart());
//        recyclerView.setAdapter(adapter);
//    }
//
//    private void calculateCart() {
//        double percentTax = 0.02;
//        double delivery = 10;
//        tax = Math.round(managmentCart.getTotalFee() * percentTax * 100.0) / 100.0;
//
//        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
//        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;
//
//        totalFeeTxt.setText("$" + itemTotal);
//        taxTxt.setText("$" + tax);
//        deliveryTxt.setText("$" + delivery);
//        totalTxt.setText("$" + total);
//    }

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
    }
}