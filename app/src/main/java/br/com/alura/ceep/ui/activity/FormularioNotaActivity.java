package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Cor;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.CoresAdapter;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.COLOR_KEY;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.TITULO_APPBAR_ALTERA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.TITULO_APPBAR_INSERE;

public class FormularioNotaActivity extends AppCompatActivity {

    private int posicaoRecibida = POSICAO_INVALIDA;
    private Nota nota;
    private View backgroundDoFormulario;
    private TextView titulo;
    private TextView descricao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
        inicializaCampos();
        pegaDadosDasNotas();
        List<Integer> cores = new Cor().ListaDeCores;
        CoresAdapter coresAdapter = configuraRecyclerVIewCores(cores);
        mudaCorDoFormulario(coresAdapter);

    }

    private void pegaDadosDasNotas() {
        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            setTitle(TITULO_APPBAR_ALTERA);
            nota = (Nota) dadosRecebidos
                    .getSerializableExtra(CHAVE_NOTA);
            posicaoRecibida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            preencheCampos(nota);
        }else{
            setTitle(TITULO_APPBAR_INSERE);
            backgroundDoFormulario.setBackgroundColor(Color.WHITE);
            nota = new Nota();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COLOR_KEY, nota.getCorNota());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restauraCorDeExibicaoDoFormularioAoRotacionarTela(savedInstanceState);
    }

    private void restauraCorDeExibicaoDoFormularioAoRotacionarTela(Bundle savedInstanceState) {
        int cor = savedInstanceState.getInt(COLOR_KEY);
        backgroundDoFormulario.setBackgroundColor(cor);
        nota.setCorNota(cor);
    }

    private void mudaCorDoFormulario(CoresAdapter coresAdapter) {
        coresAdapter.setOnItemClickListenerCores(this::onItemClickCor);
    }


    @NonNull
    private CoresAdapter configuraRecyclerVIewCores(List<Integer> cores) {
        RecyclerView listaCores = findViewById(R.id.formulario_nota_recyclerview_cores);
        CoresAdapter coresAdapter = new CoresAdapter(cores, this);
        listaCores.setAdapter(coresAdapter);
        LinearLayoutManager layoutDaPaletaDeCores = new LinearLayoutManager(this, HORIZONTAL, false);
        listaCores.setLayoutManager(layoutDaPaletaDeCores);
        return coresAdapter;
    }

    private void preencheCampos(Nota nota) {
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());
        backgroundDoFormulario.setBackgroundColor(nota.getCorNota());
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
        backgroundDoFormulario = findViewById(R.id.fundo_formulario_nota);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (ehMenuSalvaNota(item)) {
            criaNota();
            retornaNota(nota);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota nota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecibida);
        setResult(Activity.RESULT_OK, resultadoInsercao);
    }

    private void criaNota() {
        nota.setTitulo(titulo.getText().toString());
        nota.setDescricao(descricao.getText().toString());
    }

    private boolean ehMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }

    private void onItemClickCor(Integer cor) {
        backgroundDoFormulario.setBackgroundColor(cor);
        nota.setCorNota(cor);
    }
}

