package com.nexuswawe.wownews;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;



public class MainActivity extends AppCompatActivity  {
    DrawerLayout drawerLayout;
    Toolbar toolbar;


    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private static final String TAG = "MainActivity";
    public List<WowheadNews> wowheadNewsList = new ArrayList<>();
    private WebView myWebView;
    FirebaseMessaging firebaseMessaging;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);




        NavigationView navigationView = findViewById(R.id.navigation_view);
        //navigation menüsünü beyaz yapma
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spannableString = new SpannableString(item.getTitle());
            spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannableString.length(), 0);
            item.setTitle(spannableString);
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = (item.getItemId());
                if (id == R.id.menuhome) {
                    // "Home" öğesi tıklandığında yapılacaklar
                    drawerLayout.close();
                    return true;
                } else if (id == R.id.menusaved) {
                    // "Saved" öğesi tıklandığında yapılacaklar
                    drawerLayout.close();
                    Intent savedIntent = new Intent(MainActivity.this, SavedActivity.class);
                    startActivity(savedIntent);
                    return true;
                } else if (id == R.id.menulogout) {
                    SharedPreferences preferences = getSharedPreferences("token", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("fcm_token");
                    editor.apply();
                    // "Logout" öğesi tıklandığında yapılacaklar
                    drawerLayout.close();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        user.delete();
                    }
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.notiSettings) {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    if (firebaseAuth.getCurrentUser() == null) {
                        // Kullanıcı authenticated değil, Login sayfasına yönlendir.
                        Toast.makeText(MainActivity.this, "Please Login First", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        drawerLayout.close();
                        Intent Intent = new Intent(MainActivity.this, NotiSettingsActivity.class);
                        startActivity(Intent);
                        return true;
                    }

                    // "Saved" öğesi tıklandığında yapılacaklar

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ImageButton toolbarButton = findViewById(R.id.toolbar_button);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });


        // Siyah Status Bar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        // Toolbar

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //RecyclerView
        recyclerView = findViewById(R.id.rec_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(wowheadNewsList);
        recyclerView.setAdapter(recyclerViewAdapter);


        //Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference wowTokenRef = database.getReference("wow_tokens");
        wowTokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        List<String> wowTokensList = (List<String>) snapshot.getValue();

                        String euTokenPrice = wowTokensList.get(0);
                        String naTokenprice = wowTokensList.get(1);
                        Menu menu = navigationView.getMenu();
                        MenuItem euTokenPriceMenu = menu.findItem(R.id.euTokenPrice);
                        MenuItem naTokenPriceMenu = menu.findItem(R.id.naTokenPrice);

                        euTokenPriceMenu.setTitle(euTokenPrice);
                        naTokenPriceMenu.setTitle(naTokenprice);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                FirebaseDatabase.getInstance().goOffline();
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        DatabaseReference myRef = database.getReference("wowhead_news");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            //Stil Firebase
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wowheadNewsList.clear(); // Önceki verileri silmek için
                for (DataSnapshot newsSnapshot : dataSnapshot.getChildren()) {
                    HashMap<String, String> newsMap = (HashMap<String, String>) newsSnapshot.getValue();
                    WowheadNews news = new WowheadNews(newsMap.get("postId"), newsMap.get("title"), newsMap.get("body"), newsMap.get("imageUrl"), newsMap.get("postUrl"), newsMap.get("dateStr"), newsMap.get("tag"));
                    wowheadNewsList.add(news);

                }

                recyclerViewAdapter.notifyDataSetChanged();

            }

            //Stil Firebase
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                FirebaseDatabase.getInstance().goOffline();
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });

    }

}

