package com.example.projectprm.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectprm.Adapter.PupolarAdapter;
import com.example.projectprm.Helper.ManagmentCart;
import com.example.projectprm.Models.PopularProduct;
import com.example.projectprm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterPupolar;
    private RecyclerView recyclerViewPupolar;
    private ManagmentCart managmentCart;

    TextView loginName, cart;
    ImageView redcirclr, mapView, chat;

    DatabaseReference reference;
    FirebaseAuth auth;
    private boolean isFunctionExecuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        loginName = findViewById(R.id.name);
        getName();
        initRecyclerView();
        bottomNavigation();
        cart();
        mapView =findViewById(R.id.mapView);
        chat = findViewById(R.id.chat);
        mapView.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, MapsActivity.class);
            startActivity(intent1);
        });
        chat.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, MessageActivity.class);
            startActivity(intent1);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        managmentCart = new ManagmentCart(this);
        String size= String.valueOf(managmentCart.getListCart1().size());
        if (!isFunctionExecuted) {
            String title = "Cart";
            String mess = "Trong gio hang co "+ size + " san pham";
            sendNotification(title, mess);
            isFunctionExecuted = true;
        }
    }

    private void sendNotification(String messageTitle, String messageBody) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel=new NotificationChannel("my_notification","n_channel",NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("description");
            notificationChannel.setName("Channel Name");
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.bell)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.bell))
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setChannelId("my_notification")
                .setColor(Color.parseColor("#3F5996"));

        //.setProgress(100,50,false);
        assert notificationManager != null;
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m, notificationBuilder.build());
    }

    public void getName(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id = auth.getCurrentUser().getUid();
                String name = dataSnapshot.child(id).child("name").getValue(String.class);
                loginName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void cart(){
        cart = findViewById(R.id.textView3);
        redcirclr = findViewById(R.id.imageView2);
        managmentCart = new ManagmentCart(this);
        if (managmentCart.getListCart1().isEmpty()) {
            cart.setVisibility(View.GONE);
            redcirclr.setVisibility(View.GONE);
        } else {
            cart.setVisibility(View.VISIBLE);
            redcirclr.setVisibility(View.VISIBLE);
            String size= String.valueOf(managmentCart.getListCart1().size());
            cart.setText(size);
        }
    }

    public void seeAll(View v){
        Intent intent = new Intent(this, AllProductActivity.class);
        startActivity(intent);
    }

    private void bottomNavigation() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String username = intent.getStringExtra("username");
        String phone = intent.getStringExtra("phone");
        String address = intent.getStringExtra("address");
        LinearLayout homeBtn=findViewById(R.id.homeBtn);
        LinearLayout cartBtn=findViewById(R.id.cartBtn);
        LinearLayout profileBtn= findViewById(R.id.profile);

        homeBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,MainActivity.class)));
        cartBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartProductActivity.class)));
        profileBtn.setOnClickListener(v -> {
            Intent intent1 =new Intent(MainActivity.this, ProifileActivity.class);
            intent1.putExtra("name", name);
            intent1.putExtra("email", email);
            intent1.putExtra("phone", phone);
            intent1.putExtra("address", address);
            intent1.putExtra("username", username);
            startActivity(intent1);


        });
    }

    private void initRecyclerView() {
        ArrayList<PopularProduct> items = new ArrayList<>();
        items.add(new PopularProduct("T-shirt black","Immerse yourself in a world of vibrant visuals and\n" +
                " immersive sound with the VisionX Pro LED TV.\n" +
                " Its cutting-edge LED technology brings every\n" +
                " scene to life with striking clarity and rich colors.\n" +
                " With seamless integration and a sleek, modern\n" +
                " design, the VisionX Pro is not just a TV, but a\n" +
                " centerpiece for your entertainment space.\n" +
                "With its sleek, modern design, the VisionX Pro is\n" +
                " not just a TV, but a centerpiece for your \n" +
                "entertainment space. The ultra-slim bezel and\n" +
                " premium finish blend seamlessly with any decor","item_1",15,4,500));
        items.add(new PopularProduct("Smart Watch","Immerse yourself in a world of vibrant visuals and\n" +
                " immersive sound with the VisionX Pro LED TV.\n" +
                " Its cutting-edge LED technology brings every\n" +
                " scene to life with striking clarity and rich colors.\n" +
                " With seamless integration and a sleek, modern\n" +
                " design, the VisionX Pro is not just a TV, but a\n" +
                " centerpiece for your entertainment space.\n" +
                "With its sleek, modern design, the VisionX Pro is\n" +
                " not just a TV, but a centerpiece for your \n" +
                "entertainment space. The ultra-slim bezel and\n" +
                " premium finish blend seamlessly with any decor","item_2",10,4.5,450));
        items.add(new PopularProduct("IPhone 14","Immerse yourself in a world of vibrant visuals and\n" +
                " immersive sound with the VisionX Pro LED TV.\n" +
                " Its cutting-edge LED technology brings every\n" +
                " scene to life with striking clarity and rich colors.\n" +
                " With seamless integration and a sleek, modern\n" +
                " design, the VisionX Pro is not just a TV, but a\n" +
                " centerpiece for your entertainment space.\n" +
                "With its sleek, modern design, the VisionX Pro is\n" +
                " not just a TV, but a centerpiece for your \n" +
                "entertainment space. The ultra-slim bezel and\n" +
                " premium finish blend seamlessly with any decor","item_3",15,4.3,800));
        items.add(new PopularProduct(" VisionX Pro LED TV","Immerse yourself in a world of vibrant visuals and\n" +
                " immersive sound with the VisionX Pro LED TV.\n" +
                " Its cutting-edge LED technology brings every\n" +
                " scene to life with striking clarity and rich colors.\n" +
                " With seamless integration and a sleek, modern\n" +
                " design, the VisionX Pro is not just a TV, but a\n" +
                " centerpiece for your entertainment space.\n" +
                "With its sleek, modern design, the VisionX Pro is\n" +
                " not just a TV, but a centerpiece for your \n" +
                "entertainment space. The ultra-slim bezel and\n" +
                " premium finish blend seamlessly with any decor","item_4",18,4.0,1500));

        recyclerViewPupolar = findViewById(R.id.view1);
        recyclerViewPupolar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterPupolar = new PupolarAdapter(items);
        recyclerViewPupolar.setAdapter(adapterPupolar);
    }
}