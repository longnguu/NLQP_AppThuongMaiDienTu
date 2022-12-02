package com.example.appthuongmaidientu.activity;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appthuongmaidientu.Adapter.SanPhamAdapter;
import com.example.appthuongmaidientu.R;
import com.example.appthuongmaidientu.model.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsSanPham extends AppCompatActivity {

    String mobile,email,name,namesp,motasp,giasp,img,uid;
    Button btnchinhsua,bthaddcart,btnchat;
    LinearLayout linearLayout;
    ImageView imageView,imgShop;
    TextView textViewName,textViewMota,textViewGia,tenShop;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ProgressDialog progressDialog;
    List<SanPham> sanPhams=new ArrayList<>();
    SanPhamAdapter sanPhamAdapter;
    RecyclerView recyclerView;
    TextView ssp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_san_pham);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        AnhXa();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        ssp = (TextView) findViewById(R.id.soSPDetail);
        mobile = getIntent().getStringExtra("mobile");
        mobile="+84869622389";
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        namesp = getIntent().getStringExtra("namesp");
        motasp = getIntent().getStringExtra("motasp");
        giasp = getIntent().getStringExtra("giasp");
        img = getIntent().getStringExtra("imgsp");
        uid = getIntent().getStringExtra("UID");
        System.out.println("uid : "+uid);
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ssp.setText("Tổng sản phẩm: "+snapshot.child("SanPham").child(uid).getChildrenCount());
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    tenShop.setText(dataSnapshot.child(uid).child("tenUser").getValue(String.class));
                    Picasso.get().load(dataSnapshot.child(uid).child("imgUS").getValue(String.class)).into(imgShop);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        databaseReference.child("SanPham").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanPhams.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String ten= dataSnapshot.child("ten").getValue(String.class);
                    SanPham sanPham = new SanPham(ten);
                    sanPham.setImg(dataSnapshot.child("img").getValue(String.class));
                    sanPham.setMaSP(dataSnapshot.getKey());
                    sanPham.setUID(getIntent().getStringExtra("mobile"));
                    sanPham.setMota(dataSnapshot.child("mota").getValue(String.class));
                    sanPham.setGia(dataSnapshot.child("gia").getValue(String.class));
                    sanPham.setDaBan("0");
                    sanPhams.add(sanPham);
                    sanPhamAdapter.updateSanPham(sanPhams);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sanPhamAdapter=new SanPhamAdapter(sanPhams,this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,2, RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(sanPhamAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        textViewName.setText(namesp);
        textViewMota.setText(motasp);
        textViewGia.setText(giasp+" VNĐ");

        Picasso.get().load(img).into(imageView);
        System.out.println(uid+":abc"+mobile);
        if (uid.equals(mobile)){
            btnchinhsua.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }else{
            btnchinhsua.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }

        //Chỉnh sửa sản phẩm
        btnchinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsSanPham.this, ProductEditActivity.class);
                intent.putExtra("maSP", getIntent().getStringExtra("mobile"));
                startActivity(intent);
            }
        });

    }

    private void AnhXa() {
        linearLayout = findViewById(R.id.linerBottomDetails);
        btnchinhsua = findViewById(R.id.btnChinhSuaDetails);
        btnchat=findViewById(R.id.btn_chatDetails);
        bthaddcart = findViewById(R.id.btnadd_Cart);
        textViewName=findViewById(R.id.nameSPDetail);
        textViewMota=findViewById(R.id.motaSPDetail);
        textViewGia=findViewById(R.id.giaSPDetail);
        imageView=findViewById(R.id.imgSPDetail);
        tenShop=findViewById(R.id.namePRShop);
        imgShop=findViewById(R.id.imgShopDetai);
        recyclerView=findViewById(R.id.recyclerSPDetail);
    }
}