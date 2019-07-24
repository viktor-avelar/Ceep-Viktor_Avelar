package br.com.alura.ceep.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.alura.ceep.model.Nota;

@Dao
public  interface NotaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long salvaNota(Nota nota);

    @Query("SELECT * FROM nota ORDER BY posicao")
    List<Nota> mostraTodasAsNotas();

    @Update
    void atualizaNotas(Nota nota);

    @Delete
    void removeNota(Nota nota);



}