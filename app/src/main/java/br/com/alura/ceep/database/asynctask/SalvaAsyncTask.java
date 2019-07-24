package br.com.alura.ceep.database.asynctask;

import android.os.AsyncTask;

import br.com.alura.ceep.database.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;

public class SalvaAsyncTask extends AsyncTask<Void, Void, Long> {


    private NotaDAO dao;
    private Nota nota;
    private ListenerGeraId listener;

    public SalvaAsyncTask(NotaDAO dao, Nota nota, ListenerGeraId listener) {
        this.dao = dao;
        this.nota = nota;
        this.listener = listener;
    }

    @Override
    protected Long doInBackground(Void... voids) {
        return dao.salvaNota(nota);
    }


    @Override
    protected void onPostExecute(Long id) {
        super.onPostExecute(id);
        listener.geraIdDasNotas(id);

    }

    public interface ListenerGeraId {
        void geraIdDasNotas(long id);
    }
}
