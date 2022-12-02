package com.example.appthuongmaidientu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.appthuongmaidientu.Adapter.ViewPageAdapter;
import com.example.appthuongmaidientu.R;
import com.example.appthuongmaidientu.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference myRef;
    User user = new User();
    String userKey;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    ViewPageAdapter viewPageAdapter;
    ImageView iconMess, iconCart;
    SearchView searchView;
    RecyclerView recyclerViewSearch;
    public static CardView unSeenMain;
    public static TextView textUnSeenMain;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unSeenMain = (CardView) findViewById(R.id.unseenMain);
        textUnSeenMain = (TextView) findViewById(R.id.textUnseenMain);
        viewPager = (ViewPager) findViewById(R.id.viewpg);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navnav);
        iconMess = (ImageView) findViewById(R.id.topnavMess);
        iconCart = (ImageView) findViewById(R.id.topnavCart);
        searchView = (SearchView) findViewById(R.id.topSearchView);
        recyclerViewSearch = (RecyclerView) findViewById(R.id.recycleViewMainSearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        iconMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MessengerActivity.class);
                System.out.println(getIntent().getStringExtra("email"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                intent.putExtra("mobile", getIntent().getStringExtra("mobile"));
                intent.putExtra("name", getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navhome:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navorder:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navfavourite:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.navThongBao:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.navprofile:
                        viewPager.setCurrentItem(4);
                        break;
                    default:
                        viewPager.setCurrentItem(0);
                        break;
                }
                return true;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.navhome).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.navfavourite).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.navThongBao).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.navprofile).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPageAdapter);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        myRef = FirebaseDatabase.getInstance().getReference();

    }
}