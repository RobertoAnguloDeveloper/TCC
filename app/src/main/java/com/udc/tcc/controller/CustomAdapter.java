package com.udc.tcc.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.udc.tcc.MainActivity;
import com.udc.tcc.R;
import com.udc.tcc.view.fragments.EditarFragment;
import com.udc.tcc.view.fragments.ModalFragment;

public class CustomAdapter extends BaseAdapter {
    Context context;
    private View view;
    private ImageView btnLlamar, btnEditar, btnEliminar;
    FragmentTransaction transaction;
    Fragment editarFragment, modalFragment;

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
        btnLlamar = view.findViewById(R.id.btnLlamar);
        btnEditar = view.findViewById(R.id.btnEditar);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        editarFragment = new EditarFragment();
        modalFragment = new ModalFragment();

        String nombreCompleto = MainActivity.contactos.get(i).getNombres()+" "+MainActivity.contactos.get(i).getApellidos();

        imageView.setImageResource(MainActivity.contactos.get(i).getImagen());
        textViewNombre.setText(nombreCompleto);
        textViewNumero.setText(MainActivity.contactos.get(i).getTelefono());

        this.view = view;

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.contacto = MainActivity.contactos.get(i);
                context.startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + MainActivity.contacto.getTelefono())));
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.contacto = MainActivity.contactos.get(i);
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentFrame, editarFragment).commit();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.contacto = MainActivity.contactos.get(i);
                ModalFragment modalFragment = new ModalFragment();
                modalFragment.show(MainActivity.fragmentManager, "modal");
            }
        });

        return view;
    }

    public View getView(){
        return this.view;
    }
}
