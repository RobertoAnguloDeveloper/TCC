package com.udc.tcc.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.udc.tcc.R;
import com.udc.tcc.controller.ManejadorInputs;
import com.udc.tcc.model.Persona;

import java.util.ArrayList;
import java.util.List;

public class LlamarActivity extends AppCompatActivity {
    Intent agregarActivityIntent, mostrarActivityIntent, mostrarActualizarActivityIntent
            , mostrarEliminarActivityIntent;
    TextInputEditText idContact, nombresContact, apellidosContact, telefonoContact, emailContact, domicilioContact;
    List<TextInputEditText> textInputEditTextList;
    Button btnLlamar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llamar);
        getSupportActionBar().setTitle("LLAMAR A CONTACTO");

        mostrarActivityIntent = new Intent(this, MostrarListaActivity.class);
        mostrarActualizarActivityIntent = new Intent(this, MostrarActualizarActivity.class);
        mostrarEliminarActivityIntent = new Intent(this, MostrarEliminarActivity.class);

        textInputEditTextList = new ArrayList<>();

        idContact = findViewById(R.id.idContact);
        textInputEditTextList.add(idContact);

        nombresContact = findViewById(R.id.nombresContact);
        textInputEditTextList.add(nombresContact);

        apellidosContact = findViewById(R.id.apellidosContact);
        textInputEditTextList.add(apellidosContact);

        telefonoContact = findViewById(R.id.telefonoContact);
        textInputEditTextList.add(telefonoContact);

        emailContact = findViewById(R.id.emailContact);
        textInputEditTextList.add(emailContact);

        domicilioContact =findViewById(R.id.domicilioContact);
        textInputEditTextList.add(domicilioContact);

        Persona contacto = MostrarListaActivity.personaClicked;

        btnLlamar = findViewById(R.id.btnLlamar);

        idContact.setText(contacto.getId().toString());
        nombresContact.setText(contacto.getNombres());
        apellidosContact.setText(contacto.getApellidos());
        telefonoContact.setText(contacto.getTelefono());
        emailContact.setText(contacto.getEmail());
        domicilioContact.setText(contacto.getDomicilio());

        ManejadorInputs.disable(textInputEditTextList);

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + contacto.getTelefono())));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.menuItem1:
                startActivity(agregarActivityIntent);
                finish();
                break;
            case R.id.menuItem2:
                //Toast.makeText(this, "MenuItem2", Toast.LENGTH_SHORT).show();
                startActivity(mostrarActivityIntent);
                finish();
                break;
            case R.id.menuItem3:
                startActivity(mostrarActualizarActivityIntent);
                finish();
                break;
            case R.id.menuItem4:
                startActivity(mostrarEliminarActivityIntent);
                finish();
                break;
            case R.id.menuItemSalir:
                finishAffinity();
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }
}