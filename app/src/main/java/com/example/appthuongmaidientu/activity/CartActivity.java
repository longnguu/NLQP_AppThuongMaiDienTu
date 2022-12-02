package com.example.appthuongmaidientu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.appthuongmaidientu.Adapter.CartAdapter;
import com.example.appthuongmaidientu.Adapter.SanPhamAdapter;
import com.example.appthuongmaidientu.R;
import com.example.appthuongmaidientu.model.CartList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    CartAdapter cartAdapter;
    List<CartList> cartLists= new ArrayList<>();
    RecyclerView recyclerView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.rvCart);
        cartAdapter = new CartAdapter(cartLists, CartActivity.this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(CartActivity.this, 1, RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        databaseReference.child("GioHang").child(getIntent().getStringExtra("mobile")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartLists.clear();
                int i=0;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String tenSP=dataSnapshot.child("tenSP").getValue(String.class);
                    String gia=dataSnapshot.child("gia").getValue(String.class);
                    String check=dataSnapshot.child("check").getValue(String.class);
                    String name=dataSnapshot.child("name").getValue(String.class);
                    String maSP=dataSnapshot.child("maSP").getValue(String.class);
                    String uid=dataSnapshot.child("uid").getValue(String.class);
                    String soLuongMua=dataSnapshot.child("soLuongMua").getValue(String.class);
                    String hinhanh=dataSnapshot.child("hinhanh").getValue(String.class);
                    CartList cartList = new CartList(maSP,tenSP,soLuongMua,check,gia,uid,hinhanh);
                    cartLists.add(cartList);
                    i++;
                }
                cartAdapter.updateCart(cartLists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}