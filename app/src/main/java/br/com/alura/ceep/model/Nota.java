package br.com.alura.ceep.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Nota implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private long id = 0;
    private String titulo;
    private String descricao;
    private int corNota;
    private int posicao;

    public Nota() {
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCorNota(int corNota) {
        this.corNota = corNota;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCorNota() {
        return corNota;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
}
