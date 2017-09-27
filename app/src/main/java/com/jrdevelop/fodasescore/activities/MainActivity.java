package com.jrdevelop.fodasescore.activities;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jrdevelop.fodasescore.R;
import com.jrdevelop.fodasescore.acesso.DatabaseOpenHelper;
import com.jrdevelop.fodasescore.util.ConstantsApp;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    DatabaseOpenHelper mDbHelper;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gera receita app
        buildIncome();
        /*
            //envia tracking app para firebase
            Bundle params = new Bundle();
            params.putString("passo_corrente", "step03");
            params.putString("valor_definido", "gerou senha");
            mFirebaseAnalytics.logEvent("generate_password", params);
         */


        //faz o report de itens utilizados na app
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, "welcomeScreen", null);
    }

    private void checkDataBase(){
        //VALIDA O BANCO DE DADOS
        mDbHelper = DatabaseOpenHelper.getInstance(getApplicationContext());

        try {
            //Check if database exists
            mDbHelper.createDataBase();
        } catch (IOException ioe) {
            Log.e(ConstantsApp.TAG_MAIN, "Erro ao criar o banco de dados", ioe);
        }

        try {
            mDbHelper.openDataBase();
        }catch(SQLException sqle){
            Log.e(ConstantsApp.TAG_MAIN, "Erro ao abrir o banco de dados", sqle);
        }
    }

    /**
     * Prepara os dados de geração de receita, ou seja, os dados que lançam os anuncios dentro do app
     */
    private void buildIncome(){
        //gerador de receita
        MobileAds.initialize(this, getString(R.string.id_app_ad_senses));
        AdView mAdView = (AdView) findViewById(R.id.adBannerInicial);
        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("A646429C5D4FBEAA99BE19F75236C1EC").build();
        mAdView.loadAd(adRequest);
    }
}
