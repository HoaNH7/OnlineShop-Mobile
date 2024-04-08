package com.example.projectprm.Activitys;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectprm.Adapter.CartProducAdapter;
import com.example.projectprm.Adapter.ProductAdapter;
import com.example.projectprm.Adapter.PupolarAdapter;
import com.example.projectprm.Models.PopularProduct;
import com.example.projectprm.Models.Product;
import com.example.projectprm.Models.User;
import com.example.projectprm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.s;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllProductActivity extends AppCompatActivity {


    ProductAdapter productAdapter;
    RecyclerView productRecyclerview;


    FirebaseFirestore db;

    EditText search;
    ArrayList<Product> items;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firestore.collection("Product");
    Query q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        initRecyclerView();
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout profileBtn = findViewById(R.id.profile);

        homeBtn.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        cartBtn.setOnClickListener(v -> startActivity(new Intent(this, CartProductActivity.class)));

        search = findViewById(R.id.search);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().length() == 0) {
                            q = firestore.collection("Product");
                            showAdapter(q);
                        }
                        else {
                            q = reference.orderBy("tittle").startAt(s.toString().trim()).endAt(s.toString().trim() + "\uf8ff"); // name - the field for which you want to make search
                            showAdapter(q);
                        }
                        productAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                return false;
            }
        });

    }

    private void showAdapter(Query q1) {
        ArrayList<Product> items = new ArrayList<>();
        productRecyclerview = findViewById(R.id.productRe);
        productRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));

        productAdapter = new ProductAdapter(items);
        productRecyclerview.setAdapter(productAdapter);
        q1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = document.toObject(Product.class);
                        items.add(product);
                        productAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(AllProductActivity.this, "chấm", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


    private void initRecyclerView() {
        ArrayList<Product> items = new ArrayList<>();
        productRecyclerview = findViewById(R.id.productRe);
        productRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));

        productAdapter = new ProductAdapter(items);
        productRecyclerview.setAdapter(productAdapter);

        db = FirebaseFirestore.getInstance();

        db.collection("Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product product = document.toObject(Product.class);
                                items.add(product);
                                productAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(AllProductActivity.this, "chấm", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }
}