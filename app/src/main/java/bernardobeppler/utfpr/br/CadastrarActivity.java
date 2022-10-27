package bernardobeppler.utfpr.br;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import bernardobeppler.utfpr.br.modelo.Morador;
import bernardobeppler.utfpr.br.persistencia.MoradoresDatabase;

public class CadastrarActivity extends AppCompatActivity {

    private EditText editTextNome;
    private Spinner spinnerSituacao;
    private RadioGroup radioGroupBlocos;
    private RadioGroup radioGroupApartamentos;
    private RadioButton bloco1, bloco2, bloco3, bloco4;
    private RadioButton ap101, ap102, ap103, ap104;

    public static final String MODO = "MODO";
    private static final String ID  = "ID";
    public static final String NOME = "NOME";
    public static final String SITUACAO = "SITUACAO";
    public static final String BLOCO = "BLOCO";
    public static final String APARTAMENTO = "APARTAMENTO";

    public static final int NOVO = 1;
    public static final int ALTERAR = 2;

    private int modo;
    private String nomeOriginal;
    private String situacaoOriginal;
    private String blocoOriginal;
    private String apartamentoOriginal;
    private Morador morador;
    
    


    public static void novoMorador(Activity activity, int requestCode){

        Intent intent = new Intent(activity, CadastrarActivity.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, requestCode);
    }


    public static void alterarMorador(Activity activity, int requestCode, Morador morador){

        Intent intent = new Intent(activity, CadastrarActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(ID, morador.getId());

        activity.startActivityForResult(intent, requestCode);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextNome = findViewById(R.id.editTextNome);
        spinnerSituacao = findViewById(R.id.spinnerSituacao);
        radioGroupBlocos = findViewById(R.id.radioGroupBlocos);
        radioGroupApartamentos = findViewById(R.id.radioGroupApartamentos);
        popularSpinner();
        bloco1 = findViewById(R.id.radioButtonBloco1);
        bloco2 = findViewById(R.id.radioButtonBloco2);
        bloco3 = findViewById(R.id.radioButtonBloco3);
        bloco4 = findViewById(R.id.radioButtonBloco4);
        ap101 = findViewById(R.id.radioButtonAp101);
        ap102 = findViewById(R.id.radioButtonAp102);
        ap103 = findViewById(R.id.radioButtonAp103);
        ap104 = findViewById(R.id.radioButtonAp104);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        modo = bundle.getInt(MODO);

        if (modo == ALTERAR) {
            setTitle(getString(R.string.alterar_morador));

            long id = bundle.getLong(ID);

            MoradoresDatabase db = MoradoresDatabase.getDatabase(this);

            morador = db.moradorDAO().moradorPorId(id);

            editTextNome.setText(morador.getNome());
            spinnerSituacao.setSelection(getIndex(spinnerSituacao, morador.getSituacao()));


            if (morador.getBloco().equals("B1")) {
                bloco1.setChecked(true);
            } else if (morador.getBloco().equals("B2")) {
                bloco2.setChecked(true);
            } else if (morador.getBloco().equals("B3")) {
                bloco3.setChecked(true);
            } else if (morador.getBloco().equals("B4")) {
                bloco4.setChecked(true);
            }
            if (morador.getApartamento().equals("AP101")) {
                ap101.setChecked(true);
            } else if (morador.getApartamento().equals("AP102")) {
                ap102.setChecked(true);
            } else if (morador.getApartamento().equals("AP103")) {
                ap103.setChecked(true);
            } else if (morador.getApartamento().equals("AP104")) {
                ap104.setChecked(true);
            }

            editTextNome.requestFocus();

        } else {
            setTitle(getString(R.string.novo_morador));

            morador = new Morador("");

        }
    }

    private int getIndex(Spinner spinner, String myString){
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }




    private void popularSpinner(){
        ArrayList<String> lista = new ArrayList<>();

        lista.add(getString(R.string.proprietario));
        lista.add(getString(R.string.locatario));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                                            android.R.layout.simple_list_item_1,
                                                            lista);

        spinnerSituacao.setAdapter(adapter);
    }

    public void limparCampos(View view){
        editTextNome.setText(null);
        radioGroupBlocos.clearCheck();
        radioGroupApartamentos.clearCheck();

        editTextNome.requestFocus();

        Toast.makeText(this,
                R.string.limpo,
                Toast.LENGTH_SHORT).show();
    }


    public void salvar(View view){
        String nome = editTextNome.getText().toString();

        if (nome == null || nome.trim().isEmpty()){
            Toast.makeText(this,
                    R.string.erro_nome,
                    Toast.LENGTH_SHORT).show();
            editTextNome.requestFocus();
            return;
        }

        String situacao = (String) spinnerSituacao.getSelectedItem();

        if (situacao == null){
            Toast.makeText(this,
                            R.string.erro_situacao,
                            Toast.LENGTH_SHORT).show();
        }

        if (bloco1.isChecked() || bloco2.isChecked() || bloco3.isChecked() || bloco4.isChecked()){

        }else {
            Toast.makeText(this,
                    R.string.bloco_erro,
                    Toast.LENGTH_SHORT).show();
        }

        if (ap101.isChecked() || ap102.isChecked() || ap103.isChecked() || ap104.isChecked()){

        }else{
            Toast.makeText(this,
                    R.string.apartamento_erro,
                    Toast.LENGTH_SHORT).show();
        }

        String bloco;

        switch (radioGroupBlocos.getCheckedRadioButtonId()){

            case R.id.radioButtonBloco1:
                bloco = Morador.BLOCO1;
                break;

            case R.id.radioButtonBloco2:
                bloco = Morador.BLOCO2;
                break;

            case R.id.radioButtonBloco3:
                bloco = Morador.BLOCO3;
                break;

            case R.id.radioButtonBloco4:
                bloco = Morador.BLOCO4;
                break;

            default:
                Toast.makeText(this,
                               getString(R.string.bloco_erro),
                                Toast.LENGTH_SHORT).show();
                return;
        }

        String apartamento;

        switch (radioGroupApartamentos.getCheckedRadioButtonId()){

            case R.id.radioButtonAp101:
                apartamento = Morador.AP101;
                break;

            case R.id.radioButtonAp102:
                apartamento = Morador.AP102;
                break;

            case R.id.radioButtonAp103:
                apartamento = Morador.AP103;
                break;

            case R.id.radioButtonAp104:
                apartamento = Morador.AP104;
                break;

            default:
                Toast.makeText(this,
                        getString(R.string.apartamento_erro),
                        Toast.LENGTH_SHORT).show();
                return;
        }

        morador.setNome(nome);
        morador.setSituacao(situacao);
        morador.setBloco(bloco);
        morador.setApartamento(apartamento);

        MoradoresDatabase db = MoradoresDatabase.getDatabase(this);

        if (modo == NOVO){
            db.moradorDAO().inserir(morador);
        }else {
            db.moradorDAO().atualizar(morador);
        }

        setResult(Activity.RESULT_OK);
        finish();
    }


    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_cadastrar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuItemLimpar:
                limparCampos(null);
                return true;
            case R.id.menuItemSalvar:
                salvar(null);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;    

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}