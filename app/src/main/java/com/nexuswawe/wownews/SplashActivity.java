package com.nexuswawe.wownews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import com.google.firebase.auth.FirebaseAuth;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView imageView = findViewById(R.id.imageView);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(imageView);
        Glide.with(this)
                .load(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageViewTarget);

        //Firebase'ye baglanmadan kullanıcının daha önce giriş yapıp yapmadığını kontrol et
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent (SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
