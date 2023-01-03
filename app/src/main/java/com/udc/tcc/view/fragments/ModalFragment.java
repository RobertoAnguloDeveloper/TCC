package com.udc.tcc.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.udc.tcc.MainActivity;
import com.udc.tcc.R;

public class ModalFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for the dialog
        View view = inflater.inflate(R.layout.modal_layout, container, false);
        TextView mensaje = view.findViewById(R.id.modalMessage);
        mensaje.setText("Deseas eliminar a "+ MainActivity.contacto.getNombres() + " " +MainActivity.contacto.getApellidos() + "?");

        // Find the buttons in the layout and set their click listeners
        Button btnYes = view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 0;
                int idBuscar = MainActivity.contacto.getId();
                for (int i = 0; i < MainActivity.contactos.size(); i++) {
                    if(MainActivity.contactos.get(i).getId() == idBuscar){
                        index = i;
                        break;
                    }
                }

                MainActivity.contactos.remove(index);
                ContactosFragment.adapter.notifyDataSetChanged();
                dismiss();
            }
        });

        Button btnNo = view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}
