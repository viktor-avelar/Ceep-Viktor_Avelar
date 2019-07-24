package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.database.NotasDatabase;
import br.com.alura.ceep.database.asynctask.SalvaAsyncTask;
import br.com.alura.ceep.database.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import br.com.alura.ceep.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;

import static android.support.v7.widget.RecyclerView.LayoutManager;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_SALVA_LAYOUT;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.SALVA_EXIBICAO_DA_LISTA;

public class ListaNotasActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR = "Notas";
    private ListaNotasAdapter adapter;
    private boolean mudaIconeDoMenuLayout;
    private RecyclerView listaNotas;
    private SharedPreferences preferences;
    private NotaDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        setTitle(TITULO_APPBAR);
        NotasDatabase notasDatabase = NotasDatabase.getInstance(this);
        dao = notasDatabase.getRoomNotaDAO();
        List<Nota> todasNotas = pegaTodasNotas();
        sharedPreferencesDoLayout();
        configuraRecyclerView(todasNotas);
        configuraBotaoInsereNota();
    }

    @Override
    protected void onResume() {
        super.onResume();
        exibeEstadoLayoutSalvo();

    }

    private void exibeEstadoLayoutSalvo() {
        if (!mudaIconeDoMenuLayout) {
            mudaLayoutParaGrid();
        } else {
            mudaLayoutParaLinear();
        }
    }

    private void sharedPreferencesDoLayout() {
        preferences = getSharedPreferences(SALVA_EXIBICAO_DA_LISTA, MODE_PRIVATE);
        mudaIconeDoMenuLayout = preferences.getBoolean(CHAVE_SALVA_LAYOUT, mudaIconeDoMenuLayout);
    }


    private void configuraBotaoInsereNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener((v) ->
                vaiParaFormularioNotaActivityInsere()
        );
    }

    private void vaiParaFormularioNotaActivityInsere() {
        Intent iniciaFormularioNota =
                new Intent(ListaNotasActivity.this,
                        FormularioNotaActivity.class);
        startActivityForResult(iniciaFormularioNota,
                CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private List<Nota> pegaTodasNotas() {
        return dao.mostraTodasAsNotas();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ehResultadoInsereNota(requestCode, data)) {

            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                    new SalvaAsyncTask(dao,notaRecebida, id -> {
                        notaRecebida.setId(id);
                        adapter.adiciona(notaRecebida);
                    }).execute();

/*                (NOTA AO PROFESSOR - DUVIDA)
                ESTAVA TENTANDO FAZER SEM O ASYNCTASK PARA SALVAR AS NOTAS, E PARA ISSO USEI ESTAVA USANDO ESSES DOIS COMANDOS ABAIXO

//                dao.salvaNota(notaRecebida);
//                adapter.adiciona(notaRecebida);
                 SÓ QUE NÃO GERAVA O ID DAS NOTAS DE PRIMEIRA, SÓ QUE QUANDO EU REINICIAVA A ACTIVITY ELE GERAVA OS ID's O QUE EU ESTAVA FAZENDO DE ERRADO?
*/


            }

        }

        if (ehResultadoAlteraNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                dao.atualizaNotas(notaRecebida);
                adapter.altera(notaRecebida);
            }
        }
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoAlteraNota(requestCode) &&
                temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }


    private boolean ehResultadoInsereNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) &&
                temNota(data);
    }

    private boolean temNota(Intent data) {
        return data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }


    private void configuraRecyclerView(List<Nota> todasNotas) {
        listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
        configuraItemTouchHelper(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas, dao);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListenerNotas(ListaNotasActivity.this::vaiParaFormularioNotaActivityAltera);
    }

    private void vaiParaFormularioNotaActivityAltera(Nota nota) {
        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this,
                FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioComNota.putExtra(CHAVE_POSICAO, nota.getPosicao());
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_notas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.menu_lista_notas_linear_ic):
                mudaLayoutParaLinear();
                break;
            case (R.id.menu_lista_notas_grid_ic):
                mudaLayoutParaGrid();
                break;

            case (R.id.menu_formulario_feedback):
                exibeFormularioDeFeedback();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void exibeFormularioDeFeedback() {
        Intent vaiParaFormularioDeFeedback =
                new Intent(ListaNotasActivity.this, FeedbackActivity.class);
        startActivity(vaiParaFormularioDeFeedback);
        finish();
    }

    private void mudaLayoutParaLinear() {
        mudaIconeDoMenuLayout = true;
        LayoutManager layoutLinear =
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false);
        listaNotas.setLayoutManager(layoutLinear);
        invalidateOptionsMenu();
    }

    private void mudaLayoutParaGrid() {
        mudaIconeDoMenuLayout = false;
        StaggeredGridLayoutManager layoutGrid =
                new StaggeredGridLayoutManager(2
                        , StaggeredGridLayoutManager.VERTICAL);
        listaNotas.setLayoutManager(layoutGrid);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        recuperaUltimoEstadoSalvoDaListaDeNotas();
        alteraVisualizacaoDoIconeEntreGridOuLinear(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    private void recuperaUltimoEstadoSalvoDaListaDeNotas() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(CHAVE_SALVA_LAYOUT, mudaIconeDoMenuLayout);
        editor.apply();
    }


    private void alteraVisualizacaoDoIconeEntreGridOuLinear(Menu menu) {
        if (mudaIconeDoMenuLayout) {
            menu.findItem(R.id.menu_lista_notas_linear_ic).setVisible(false);
            menu.findItem(R.id.menu_lista_notas_grid_ic).setVisible(true);

        } else {
            menu.findItem(R.id.menu_lista_notas_linear_ic).setVisible(true);
            menu.findItem(R.id.menu_lista_notas_grid_ic).setVisible(false);
        }
    }


}
