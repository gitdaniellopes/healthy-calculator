package br.com.daniel.healthycalculator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.daniel.healthycalculator.R;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView imageSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        bind();
        imageSplash.setAlpha(0f);
        imageSplash.animate().setDuration(1500).alpha(1f).withEndAction(() -> {
            final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

    }

    private void bind() {
        imageSplash = findViewById(R.id.image_splash);
    }
}
