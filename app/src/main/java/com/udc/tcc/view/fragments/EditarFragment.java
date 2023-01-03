package com.udc.tcc.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.udc.tcc.MainActivity;
import com.udc.tcc.R;
import com.udc.tcc.model.Persona;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarFragment extends Fragment {
    private TextInputEditText id, nombres, apellidos, telefono, email, domicilio;
    private List<TextInputEditText> inputs;
    private Integer idNum;
    private Button btnEditar;

    private View view;
    Fragment contactosFragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarFragment newInstance(String param1, String param2) {
        EditarFragment fragment = new EditarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("EDITAR CONTACTO");

        view = inflater.inflate(R.layout.fragment_editar, container, false);
        contactosFragment = new ContactosFragment();

        inputs = new ArrayList<>();

        id = view.findViewById(R.id.id);

        Persona contacto = MainActivity.contacto;

        id.setText(contacto.getId().toString());
        id.setEnabled(false);

        nombres = view.findViewById(R.id.nombres);
        nombres.setSingleLine(true);
        nombres.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(nombres, InputMethodManager.SHOW_IMPLICIT);
        nombres.setText(contacto.getNombres());

        apellidos = view.findViewById(R.id.apellidos);
        apellidos.setSingleLine(true);
        apellidos.setText(contacto.getApellidos());

        telefono = view.findViewById(R.id.telefono);
        telefono.setSingleLine(true);
        telefono.setText(contacto.getTelefono());

        email = view.findViewById(R.id.email);
        email.setSingleLine(true);
        email.setText(contacto.getEmail());

        domicilio = view.findViewById(R.id.domicilio);
        domicilio.setSingleLine(true);
        domicilio.setText(contacto.getDomicilio());

        btnEditar = view.findViewById(R.id.btnEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persona persona = new Persona(Integer.valueOf(id.getText().toString()), nombres.getText().toString(),
                        apellidos.getText().toString(), telefono.getText().toString(),
                        email.getText().toString(), domicilio.getText().toString());

                int index = 0;
                int idBuscar = Integer.valueOf(id.getText().toString());
                for (int i = 0; i < MainActivity.contactos.size(); i++) {
                    if(MainActivity.contactos.get(i).getId() == idBuscar){
                        index = i;
                        break;
                    }
                }

                persona.setImagen(MainActivity.contactos.get(index).getImagen());

                MainActivity.contactos.set(index, persona);
                ContactosFragment.adapter.notifyDataSetChanged();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentFrame, contactosFragment).commit();
            }
        });

        return view;
    }
}