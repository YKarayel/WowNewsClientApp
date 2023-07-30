package com.nexuswawe.wownews;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SavedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DBHandler db;
    ArrayList<String> titledb, image_urldb, post_urldb, datedb;
    SavedAdapter savedAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        recyclerView = findViewById(R.id.rec_view);

        db = new DBHandler(SavedActivity.this);
        titledb = new ArrayList<>();
        image_urldb = new ArrayList<>();
        post_urldb = new ArrayList<>();
        datedb = new ArrayList<>();

        storeDataInArrays();
        savedAdapter = new SavedAdapter(SavedActivity.this, titledb, image_urldb, post_urldb, datedb);
        recyclerView.setAdapter(savedAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SavedActivity.this));/////////

    }

    void storeDataInArrays() {
        Cursor cursor = db.readNewPost();
        if (cursor == null) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                titledb.add(cursor.getString(1));
                image_urldb.add(cursor.getString(2));
                post_urldb.add(cursor.getString(3));
                datedb.add(cursor.getString(4));


            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }
}
