package com.example.nicolas.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Adapter_item_relatorio_criacao extends BaseAdapter {
    private ArrayList<String> RelatorioNum;
    private ArrayList<String> RelatorioResumo;
    private Context contexto;

    //Necessario Criar o construtor
    public Adapter_item_relatorio_criacao(Context contexto, ArrayList RelatorioNum,
                                          ArrayList RelatorioResumo) {
        this.RelatorioNum = RelatorioNum;
        this.RelatorioResumo = RelatorioResumo;

        this.contexto = contexto;
    }

    private class ViewHolder {

        TextView txtRelatorioID;
        TextView txtRelatorioResumo;


    }


    @Override
    public int getCount() {
        return RelatorioNum.size();
    }

    @Override
    public Object getItem(int position) {
        return RelatorioNum.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Adapter_item_relatorio_criacao.ViewHolder holder = null;
        //Classe para chamar a associação que irá receber
        //os elementos para cada item
        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {

            view = inflater.inflate(R.layout.item_activity_relatorio_criacao, viewGroup, false);
            holder = new Adapter_item_relatorio_criacao.ViewHolder();

            holder.txtRelatorioID = (TextView) view.findViewById(R.id.txtRelatorioID);
            holder.txtRelatorioResumo = (TextView) view.findViewById(R.id.txtRelatorioResumo);


            view.setTag(holder);
        } else {
            holder = (Adapter_item_relatorio_criacao.ViewHolder) view.getTag();
        }

        holder.txtRelatorioID.setText(RelatorioNum.get(position));
        holder.txtRelatorioResumo.setText(RelatorioResumo.get(position));


        return view;
    }




}