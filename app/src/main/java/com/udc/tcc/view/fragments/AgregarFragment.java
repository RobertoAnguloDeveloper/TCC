package com.udc.tcc.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.udc.tcc.MainActivity;
import com.udc.tcc.R;
import com.udc.tcc.controller.CustomAdapter;
import com.udc.tcc.controller.ManejadorInputs;
import com.udc.tcc.model.Persona;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgregarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarFragment extends Fragment {
    private TextInputEditText id, nombres, apellidos, telefono, email, domicilio;
    private List<TextInputEditText> inputs;
    private Integer idNum;
    private Button btnAgregar;

    private View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AgregarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgregarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarFragment newInstance(String param1, String param2) {
        AgregarFragment fragment = new AgregarFragment();
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
        actionBar.setTitle("AGREGAR CONTACTO");

        view = inflater.inflate(R.layout.fragment_agregar, container, false);

        inputs = new ArrayList<>();

        id = view.findViewById(R.id.id);

        idNum = 0;

        if(MainActivity.contactos.size() > 0){
            idNum = MainActivity.contactos.get(MainActivity.contactos.size()-1).getId()+1;
        }else{
            idNum = 1;
        }

        id.setText(idNum.toString());
        id.setEnabled(false);

        nombres = view.findViewById(R.id.nombres);
        nombres.setSingleLine(true);
        nombres.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(nombres, InputMethodManager.SHOW_IMPLICIT);
        inputs.add(nombres);

        apellidos = view.findViewById(R.id.apellidos);
        apellidos.setSingleLine(true);
        inputs.add(apellidos);

        telefono = view.findViewById(R.id.telefono);
        telefono.setSingleLine(true);
        inputs.add(telefono);

        email = view.findViewById(R.id.email);
        email.setSingleLine(true);
        inputs.add(email);

        domicilio = view.findViewById(R.id.domicilio);
        domicilio.setSingleLine(true);
        inputs.add(domicilio);

        btnAgregar = view.findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Persona persona = new Persona(Integer.valueOf(id.getText().toString()), nombres.getText().toString(),
                        apellidos.getText().toString(), telefono.getText().toString(),
                        email.getText().toString(), domicilio.getText().toString());
                persona.setImagen(R.drawable.contact);

                JSONObject p_json = new JSONObject();

                try {
                    p_json.put("imagen",persona.getImagen());
                    p_json.put("nombres",persona.getNombres());
                    p_json.put("apellidos",persona.getApellidos());
                    p_json.put("telefono",persona.getTelefono());
                    p_json.put("email",persona.getEmail());
                    p_json.put("domicilio",persona.getDomicilio());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String api_request = "http://144.22.204.157:8080/api/Contacto/save";
                JsonObjectRequest post_request = new JsonObjectRequest(Request.Method.POST, api_request, p_json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                ManejadorInputs.limpiarCampos(inputs);
                                idNum++;
                                id.setEnabled(true);
                                id.setText(idNum.toString());
                                id.setEnabled(false);
                                nombres.requestFocus();

                                MainActivity.getRequest();
                                Toast.makeText(getContext(), "CONTACTO GUARDADO", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                MainActivity.requestQueue.add(post_request);
            }
        });

        return view;
    }
}