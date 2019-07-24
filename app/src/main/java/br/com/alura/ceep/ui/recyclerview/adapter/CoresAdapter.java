package br.com.alura.ceep.ui.recyclerview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.alura.ceep.R;

public class CoresAdapter extends RecyclerView.Adapter<CoresAdapter.CoresViewHolder> {

    private List<Integer> cores;
    private Context context;
    private OnItemClickListenerCores listenerCores;

    public CoresAdapter(List<Integer> cores, Context context) {
        this.cores = cores;
        this.context = context;
    }

    public void setOnItemClickListenerCores(OnItemClickListenerCores listenerCores) {
        this.listenerCores = listenerCores;
    }

    @NonNull
    @Override
    public CoresAdapter.CoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_cor_fundo, parent, false);
        return new CoresViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull CoresViewHolder holder, int position) {
        Integer cor = cores.get(position);
        holder.vinculaCor(cor);
    }


    @Override
    public int getItemCount() {
        return cores.size();
    }


    class CoresViewHolder extends RecyclerView.ViewHolder {
        private Integer cor;
        private View botaoColorido;
        private Drawable corFormulario;


        CoresViewHolder(View itemView) {
            super(itemView);
            botaoColorido = itemView.findViewById(R.id.item_botao_muda_cor_fundo_nota);
            corFormulario = botaoColorido.getBackground();
            itemView.setOnClickListener(v -> listenerCores.onItemClickCor(cor));
        }

        @SuppressLint("NewApi")
        void vinculaCor(Integer cor) {
            this.cor = cor;
            corFormulario.setTint(cor);
        }

    }

    public interface OnItemClickListenerCores {

        void onItemClickCor(Integer cor);
    }


}

