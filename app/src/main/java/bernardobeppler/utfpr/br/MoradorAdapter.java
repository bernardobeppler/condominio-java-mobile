package bernardobeppler.utfpr.br;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bernardobeppler.utfpr.br.modelo.Morador;

public class MoradorAdapter extends BaseAdapter {

    Context context;
    List<Morador> moradores;

        private static class MoradorHolder {
            public TextView nome;
            public TextView situacao;
            public TextView bloco;    
            public TextView apartamento;
        }

        public MoradorAdapter(Context context, ArrayList<Morador> listaMoradores, List<Morador> moradores){
            this.context = context;
            this.moradores = moradores;
        }

        @Override
        public int getCount() {
            return moradores.size();
        }

        @Override
        public Object getItem(int i) {
            return moradores.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            MoradorHolder holder;

            if(view == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listview_morador, viewGroup, false);

                holder = new MoradorHolder();

                holder.nome = view.findViewById(R.id.textViewNome);
                holder.situacao = view.findViewById(R.id.textViewSituacao);
                holder.bloco = view.findViewById(R.id.textViewBloco);
                holder.apartamento = view.findViewById(R.id.textViewApartamento);

                view.setTag(holder);

            } else {
                holder = (MoradorHolder) view.getTag();
            }

            holder.nome.setText(moradores.get(i).getNome());
            holder.situacao.setText(moradores.get(i).getSituacao());
            holder.bloco.setText(moradores.get(i).getBloco());
            holder.apartamento.setText(moradores.get(i).getApartamento());

            return view;
        }
    }
