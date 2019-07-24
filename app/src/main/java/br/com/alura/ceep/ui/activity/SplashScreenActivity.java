package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.alura.ceep.R;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.APLICATIVO_ACIONADO;

public class SplashScreenActivity extends AppCompatActivity {

    int tempoSplashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences preferences =
                getSharedPreferences(APLICATIVO_ACIONADO,MODE_PRIVATE);

        if (preferences.contains(APLICATIVO_ACIONADO)){
            tempoSplashScreen = 500;
        }else{
            adicionaPreferenceJaAbriu(preferences);
            tempoSplashScreen = 2000;
        }

        mostraSplashScreen();

    }

    private void mostraSplashScreen() {
        Handler handler = new Handler();
        handler.postDelayed(this::mostraListaNotasActivity,tempoSplashScreen);
    }

    private void adicionaPreferenceJaAbriu(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(APLICATIVO_ACIONADO, true);
        editor.apply();
    }

    private void mostraListaNotasActivity() {
        Intent vaiParaListaNotasActivity = new Intent(SplashScreenActivity.this, ListaNotasActivity.class);
        startActivity(vaiParaListaNotasActivity);
        finish();
    }
}
