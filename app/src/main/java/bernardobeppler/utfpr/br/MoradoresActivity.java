package bernardobeppler.utfpr.br;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.List;

import bernardobeppler.utfpr.br.modelo.Morador;
import bernardobeppler.utfpr.br.persistencia.MoradoresDatabase;
import bernardobeppler.utfpr.br.utils.UtilsGUI;

public class MoradoresActivity extends AppCompatActivity {

    private ListView listViewMoradores;

    private ActionMode actionMode;
    private View viewSelecionada;
    private int tema = AppCompatDelegate.MODE_NIGHT_NO;
    private static final String ARQUIVO = "bernardobeppler.utfpr.br.PREFERENCIAS_TEMA";
    private static final String TEMA = "tema";
    private ArrayAdapter<Morador> listaAdapter;
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morador);

        listViewMoradores = findViewById(R.id.listViewMoradores);

        listViewMoradores.setOnItemClickListener((parent, view, position, id) -> {

            Morador morador = (Morador) parent.getItemAtPosition(position);

            CadastrarActivity.alterarMorador(MoradoresActivity.this,
                    ALTERAR,
                    morador);
        });

        listViewMoradores.setOnItemLongClickListener(this::onItemLongClick);


        listViewMoradores.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        popularLista();

        registerForContextMenu(listViewMoradores);

        lerPreferenciaTema();

    }

    private boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        if (actionMode != null) {
            return false;
        }

        viewSelecionada = view;
        viewSelecionada.setBackgroundColor(Color.LTGRAY);

        listViewMoradores.setEnabled(false);


        actionMode = startActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_contexto, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuItemAlterar:
                        Morador morador = (Morador) parent.getItemAtPosition(position);
                        CadastrarActivity.alterarMorador(MoradoresActivity.this, ALTERAR, morador);
                        mode.finish();
                        return true;
                    case R.id.menuItemExcluir:
                        excluirMorador((Morador) parent.getItemAtPosition(position));
                        mode.finish();
                        return true;
                    default:
                        return MoradoresActivity.super.onContextItemSelected(item);
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

                if (viewSelecionada != null){
                    viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
                }

                actionMode = null;
                viewSelecionada = null;

                listViewMoradores.setEnabled(true);

            }
        });

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == NOVO || requestCode == ALTERAR) &&
                resultCode == Activity.RESULT_OK) {

            listaAdapter.notifyDataSetChanged();

            popularLista();
        }
    }


    private void popularLista() {

        MoradoresDatabase db = MoradoresDatabase.getDatabase(this);

        List<Morador> lista = db.moradorDAO().carregarTudo();

        listaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                lista);

        listViewMoradores.setAdapter(listaAdapter);

    }



    private void excluirMorador(final Morador morador) {

        String mensagem = getString(R.string.excluirMorador);

        DialogInterface.OnClickListener listener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    MoradoresDatabase database = MoradoresDatabase.getDatabase(MoradoresActivity.this);

                    database.moradorDAO().excluir(morador);

                    listaAdapter.remove(morador);
                    listaAdapter.notifyDataSetChanged();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
        UtilsGUI.confirmaAcao(this, mensagem, listener);
    }

    private void lerPreferenciaTema() {

        SharedPreferences shared = getSharedPreferences("ARQUIVO", Context.MODE_PRIVATE);

        tema = shared.getInt("tema", tema);

        mudaTema();

    }

    private void salvarPreferenciasTema(int novoValor) {
        SharedPreferences shared = getSharedPreferences("ARQUIVO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("tema", novoValor);
        editor.commit();
        tema = novoValor;
        mudaTema();
    }


    private void mudaTema() {
        if (tema == AppCompatDelegate.MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }


    public void abrirAutoria() {
        Intent intent = new Intent(this,
                AutoriaActivity.class);

        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuItemAdicionar:
                CadastrarActivity.novoMorador(this, NOVO);
                return true;
            case R.id.menuItemSobre:
                abrirAutoria();
                return true;
            case R.id.menuItemTemaEscuro:
                salvarPreferenciasTema(AppCompatDelegate.MODE_NIGHT_YES);
                return true;
            case R.id.menuItemTemaClaro:
                salvarPreferenciasTema(AppCompatDelegate.MODE_NIGHT_NO);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);

            getMenuInflater().inflate(R.menu.menu_contexto, menu);
        }
    }
