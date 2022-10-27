package bernardobeppler.utfpr.br.persistencia;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import bernardobeppler.utfpr.br.modelo.Morador;

@Database(entities = {Morador.class}, version = 1, exportSchema = false)
public abstract class MoradoresDatabase extends RoomDatabase {


    public abstract MoradorDAO moradorDAO();

    public static MoradoresDatabase instance;

    public static MoradoresDatabase getDatabase(final Context context){

        if (instance == null){
            synchronized (MoradoresDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context,
                                                    MoradoresDatabase.class,
                                                "moradores.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}