package br.com.alura.ceep.database;

        import android.arch.persistence.room.Database;
        import android.arch.persistence.room.Room;
        import android.arch.persistence.room.RoomDatabase;
        import android.content.Context;

        import br.com.alura.ceep.database.dao.NotaDAO;
        import br.com.alura.ceep.model.Nota;

        import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.NOME_BANCO_DE_DADOS;

@Database(entities = {Nota.class}, version = 1, exportSchema = false)
public abstract class NotasDatabase extends RoomDatabase {


    public abstract NotaDAO getRoomNotaDAO();

    public static NotasDatabase getInstance(Context context){
        return Room
                .databaseBuilder(context,NotasDatabase.class, NOME_BANCO_DE_DADOS)
                .allowMainThreadQueries()
                .build();
    }


}
