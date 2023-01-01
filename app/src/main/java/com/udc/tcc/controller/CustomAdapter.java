package com.udc.tcc.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.udc.tcc.MainActivity;
import com.udc.tcc.R;

public class CustomAdapter extends BaseAdapter {
    Context context;

    public CustomAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return MainActivity.contactos.size();
    }

    @Override
    public Object getItem(int i) {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        TextView textViewNombre;
        TextView textViewNumero;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.listview_custom, null);
        }

        imageView = view.findViewById(R.id.iconImage);
        textViewNombre = view.findViewById(R.id.nombreCompleto);
        textViewNumero = view.findViewById(R.id.numeroTel);

        String nombreCompleto = MainActivity.contactos.get(i).getNombres()+" "+MainActivity.contactos.get(i).getApellidos();

        imageView.setImageResource(MainActivity.contactos.get(i).getImagen());
        textViewNombre.setText(nombreCompleto);
        textViewNumero.setText(MainActivity.contactos.get(i).getTelefono());

        return view;
    }
}
