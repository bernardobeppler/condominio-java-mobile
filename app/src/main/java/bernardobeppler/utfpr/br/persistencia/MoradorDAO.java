package bernardobeppler.utfpr.br.persistencia;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import bernardobeppler.utfpr.br.modelo.Morador;

@Dao
public interface MoradorDAO {

    @Insert
    long inserir(Morador morador);

    @Update
    void atualizar(Morador morador);

    @Delete
    void excluir(Morador morador);

    @Query("SELECT * FROM morador WHERE id = :id")
    Morador moradorPorId(long id);


    @Query("SELECT * FROM morador ORDER BY nome ASC")
    List<Morador> carregarTudo();



}
