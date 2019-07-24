package br.com.alura.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.database.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private final List<Nota> notas;
    private final Context context;
    private OnItemClickListenerNotas onItemClickListenerNotas;
    private NotaDAO dao;


    public ListaNotasAdapter(Context context, List<Nota> notas, NotaDAO dao) {
        this.context = context;
        this.notas = notas;
        this.dao = dao;
        setHasStableIds(true);

    }

    public void setOnItemClickListenerNotas(OnItemClickListenerNotas onItemClickListenerNotas) {
        this.onItemClickListenerNotas = onItemClickListenerNotas;
    }

    @NonNull
    @Override
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaNotasAdapter.NotaViewHolder holder, int position) {
        Nota nota = notas.get(position);
        holder.vincula(nota);

    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    @Override
    public long getItemId(int position) {
        return  notas.get(position).getId();
    }

    public void altera(Nota nota) {
        notas.set(nota.getPosicao(), nota);
        notifyItemChanged(nota.getPosicao(),nota);
    }

    public void remove(int posicao) {
        Nota nota = notas.get(posicao);
        dao.removeNota(nota);
        notas.remove(posicao);
        notifyItemRangeRemoved(posicao,getItemCount());
        atualizaPosicaoDasNotas();
    }

    private void atualizaPosicaoDasNotas() {
        for (int i = 0; i < notas.size(); i++) {
            if (notas.get(i).getPosicao() != i) {
                notas.get(i).setPosicao(i);
                dao.atualizaNotas(notas.get(i));
            }
        }
    }

    public void adiciona(Nota nota) {
        notas.add(0,nota);
        notifyItemInserted(0);
        atualizaPosicaoDasNotas();
    }

    public void troca(int posicaoInicial, int posicaoFinal) {
        Collections.swap(notas, posicaoInicial, posicaoFinal);
        atualizaPosicaoDasNotas();
        notifyDataSetChanged();
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }
    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private final ConstraintLayout background;

        private Nota nota;

        NotaViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
            background = itemView.findViewById(R.id.item_nota_fundo);
            itemView.setOnClickListener(view -> onItemClickListenerNotas.onItemClick(nota));
        }

        void vincula(Nota nota) {
            preencheCampo(nota);
            this.nota = nota;
        }
        private void preencheCampo(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
            background.setBackgroundColor(nota.getCorNota());
        }

    }

   public interface OnItemClickListenerNotas {

        void onItemClick(Nota nota);


    }

}

