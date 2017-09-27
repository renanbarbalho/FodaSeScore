package com.jrdevelop.fodasescore.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jrdevelop.fodasescore.R;

public class SplashActivity extends AppCompatActivity {

    //define o tempo de 2 segundos para o splash ficar visível
    private static final int SPLASH_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //efetua o processo: fecha splash abre principal
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();

                //aplica a animação ao sair do splash
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, SPLASH_TIME);
    }
}
