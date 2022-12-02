package com.example.appthuongmaidientu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appthuongmaidientu.Adapter.DMSPAdapter;
import com.example.appthuongmaidientu.R;
import com.example.appthuongmaidientu.model.DMSP;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductEditActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> dmmmm=new ArrayList<>();
    String maSP;
    ArrayList<DMSP> dmsps;
    DMSPAdapter dmspAdapter;
    private Spinner spnCategory;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);

        spnCategory = findViewById(R.id.sp_danhmuc);

        maSP = getIntent().getStringExtra("maSP");

        dmsps = new ArrayList<DMSP>();
        databaseReference.child("DanhMucSanPham").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dmmmm.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String asd=dataSnapshot.child("id").getValue(String.class)+" - "+dataSnapshot.child("ten").getValue(String.class);
                    dmmmm.add(asd);
                    System.out.println(asd);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter  = new ArrayAdapter(this, android.R.layout.simple_spinner_item,dmmmm);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCategory.setAdapter(adapter);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ProductEditActivity.this, spnCategory.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}