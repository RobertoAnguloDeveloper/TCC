package com.udc.tcc.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.udc.tcc.MainActivity;
import com.udc.tcc.R;

import org.json.JSONException;
import org.json.JSONObject;

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

                String api_request = "http://144.22.204.157:8080/api/Contacto/"+idBuscar;

                StringRequest delete_request = new StringRequest(Request.Method.DELETE, api_request,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(MainActivity.context, "CONTACTO ELIMINADO", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                );

                MainActivity.requestQueue.add(delete_request);

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
