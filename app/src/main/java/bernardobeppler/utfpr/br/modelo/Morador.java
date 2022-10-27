package bernardobeppler.utfpr.br.modelo;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Morador {

    public static final String BLOCO1 = "B1";
    public static final String BLOCO2 = "B2";
    public static final String BLOCO3 = "B3";
    public static final String BLOCO4 = "B4";

    public static final String AP101 = "AP101";
    public static final String AP102 = "AP102";
    public static final String AP103 = "AP103";
    public static final String AP104 = "AP104";

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String nome;
    @NonNull
    private String situacao;
    @NonNull
    private String bloco;
    @NonNull
    private String apartamento;


    public Morador(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    @Override
    public String toString(){
        return getNome() + "              " + getSituacao() + "           " + getBloco() + "   " + getApartamento();
    }

}
