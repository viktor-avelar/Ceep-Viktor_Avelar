package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.alura.ceep.R;

public class FeedbackActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Feedback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle(TITULO_APPBAR);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case(R.id.menu_enviar_feedback):
                exibeListaDeNotas();
                Toast.makeText(this,
                        "Mensagem Enviada! ;)",
                        Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void exibeListaDeNotas() {
        Intent vaiParaListaNOtasActivity =
                new Intent(FeedbackActivity.this, ListaNotasActivity.class);
        startActivity(vaiParaListaNOtasActivity);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_enviar_feedback,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
