package com.nexuswawe.wownews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.messaging.FirebaseMessaging;

public class NotiSettingsActivity extends AppCompatActivity {

    ListView listViewData;
    ArrayAdapter<String> adapter;
    String[] arrayPeliculas = {"WOW LIVE AND PTR", "DIABLO 4", "WOW WRATH", "WOW TBC", "WOW CLASSIC", "BLIZZARD", "ARCLIGHT",
            "DIABLO 2 AND DIABLO 3", "DIABLO IMMORTAL", "OVERWATCH"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotiSettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        listViewData = findViewById(R.id.listView_data);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arrayPeliculas);
        listViewData.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("notiPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("wow_live_ptr", listViewData.isItemChecked(0));
                editor.putBoolean("diablo_4", listViewData.isItemChecked(1));
                editor.putBoolean("wow_wrath", listViewData.isItemChecked(2));
                editor.putBoolean("wow_tbc", listViewData.isItemChecked(3));
                editor.putBoolean("wow_classic", listViewData.isItemChecked(4));
                editor.putBoolean("blizzard", listViewData.isItemChecked(5));
                editor.putBoolean("arclight", listViewData.isItemChecked(6));
                editor.putBoolean("d2_d3", listViewData.isItemChecked(7));
                editor.putBoolean("d_immortal", listViewData.isItemChecked(8));
                editor.putBoolean("overwatch", listViewData.isItemChecked(9));
                editor.apply();
                Toast.makeText(NotiSettingsActivity.this, "Preferences saved", Toast.LENGTH_SHORT).show();

                // Seçilen topic'lere FirebaseMessaging üzerinden abone ol
                if (listViewData.isItemChecked(0)) {
                    FirebaseMessaging.getInstance().subscribeToTopic("wow_live_ptr");
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("wow_live_ptr");
                }
                if (listViewData.isItemChecked(1)) {
                    FirebaseMessaging.getInstance().subscribeToTopic("diablo_4");
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("diablo_4");
                }
                if (listViewData.isItemChecked(2)) {
                    FirebaseMessaging.getInstance().subscribeToTopic("wow_wrath");
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("wow_wrath");
                }
                if (listViewData.isItemChecked(3)) {
                    FirebaseMessaging.getInstance().subscribeToTopic("wow_tbc");
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("wow_tbc");
                }
                if (listViewData.isItemChecked(4)) {
                    FirebaseMessaging.getInstance().subscribeToTopic("wow_classic");
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("wow_classic");
                }
                if (listViewData.isItemChecked(5)) {
                    FirebaseMessaging.getInstance().subscribeToTopic("blizzard");
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("blizzard");
                }
                if (listViewData.isItemChecked(6)) {
                    FirebaseMessaging.getInstance().subscribeToTopic("arclight");
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("arclight");
                }
                if (listViewData.isItemChecked(7)) {
                    FirebaseMessaging.getInstance().subscribeToTopic("d2_d3");
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("d2_d3");
                }
                if (listViewData.isItemChecked(8)) {
                    FirebaseMessaging.getInstance().subscribeToTopic("d_immortal");
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("d_immortal");
                }
                if (listViewData.isItemChecked(9)) {
                    FirebaseMessaging.getInstance().subscribeToTopic("overwatch");
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("overwatch");
                }

                Intent intent = new Intent(NotiSettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        // Get saved values
        boolean wowLivePtr = sharedPreferences.getBoolean("wow_live_ptr", false);
        boolean diablo4 = sharedPreferences.getBoolean("diablo_4", false);
        boolean wowWrath = sharedPreferences.getBoolean("wow_wrath", false);
        boolean wowTbc = sharedPreferences.getBoolean("wow_tbc", false);
        boolean wowClassic = sharedPreferences.getBoolean("wow_classic", false);
        boolean blizzard = sharedPreferences.getBoolean("blizzard", false);
        boolean arclight = sharedPreferences.getBoolean("arclight", false);
        boolean d2D3 = sharedPreferences.getBoolean("d2_d3", false);
        boolean dImmortal = sharedPreferences.getBoolean("d_immortal", false);
        boolean overwatch = sharedPreferences.getBoolean("overwatch", false);

        // Set ListView items as checked according to saved values
        listViewData.setItemChecked(0, wowLivePtr);
        listViewData.setItemChecked(1, diablo4);
        listViewData.setItemChecked(2, wowWrath);
        listViewData.setItemChecked(3, wowTbc);
        listViewData.setItemChecked(4, wowClassic);
        listViewData.setItemChecked(5, blizzard);
        listViewData.setItemChecked(6, arclight);
        listViewData.setItemChecked(7, d2D3);
        listViewData.setItemChecked(8, dImmortal);
        listViewData.setItemChecked(9, overwatch);
    }
}