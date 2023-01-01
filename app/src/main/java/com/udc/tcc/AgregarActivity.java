package com.udc.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.udc.tcc.controller.ManejadorInputs;
import com.udc.tcc.model.Persona;

import java.util.ArrayList;
import java.util.List;

public class AgregarActivity extends AppCompatActivity {
    Intent agregarActivityIntent, mostrarActivityIntent, mostrarActualizarActivityIntent
            , mostrarEliminarActivityIntent;
    private TextInputEditText id, nombres, apellidos, telefono, email, domicilio;
    private List<TextInputEditText> inputs;
    private Integer idNum;
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        getSupportActionBar().setTitle("AGREGAR CONTACTO");

        agregarActivityIntent = new Intent(this, AgregarActivity.class);
        mostrarActivityIntent = new Intent(this, MostrarListaActivity.class);
        mostrarActualizarActivityIntent = new Intent(this, MostrarActualizarActivity.class);
        mostrarEliminarActivityIntent = new Intent(this, MostrarEliminarActivity.class);

        inputs = new ArrayList<>();

        id = findViewById(R.id.id);

        idNum = 0;

        if(MainActivity.contactos.size() > 0){
            idNum = MainActivity.contactos.get(MainActivity.contactos.size()-1).getId()+1;
        }else{
            idNum = 1;
        }
//        String query = "SELECT seq FROM sqlite_sequence WHERE name='"+MainActivity.tableName+"';";
//        Cursor cursor = MainActivity.sqLiteRead.rawQuery(query, null);
//        cursor.moveToFirst();
//
//        String resultQuery = (cursor.getInt(0)+1)+"";
//
//        id.setText(resultQuery);
        id.setText(idNum.toString());
        id.setEnabled(false);

        nombres = findViewById(R.id.nombres);
        nombres.setSingleLine(true);
        nombres.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.showSoftInput(nombres, InputMethodManager.SHOW_IMPLICIT);
        inputs.add(nombres);

        apellidos = findViewById(R.id.apellidos);
        apellidos.setSingleLine(true);
        inputs.add(apellidos);

        telefono = findViewById(R.id.telefono);
        telefono.setSingleLine(true);
        inputs.add(telefono);

        email = findViewById(R.id.email);
        email.setSingleLine(true);
        inputs.add(email);

        domicilio = findViewById(R.id.domicilio);
        domicilio.setSingleLine(true);
        inputs.add(domicilio);

        btnAgregar = findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persona persona = new Persona(Integer.valueOf(id.getText().toString()), nombres.getText().toString(),
                        apellidos.getText().toString(), telefono.getText().toString(),
                        email.getText().toString(), domicilio.getText().toString());

                persona.setImagen(R.drawable.contact);

                MainActivity.contactos.add(persona);

//                for (Persona per :
//                        MainActivity.contactos) {
//                    System.out.println(per.getId()
//                            + "\n"+per.getNombres()
//                            + "\n"+per.getApellidos()
//                            + "\n"+per.getTelefono()
//                            + "\n"+per.getEmail()
//                            + "\n"+per.getDomicilio());
//                }
//
//                ContentValues contentValues = new ContentValues();
//
//                contentValues.put("nombres",persona.getNombres());
//                contentValues.put("apellidos",persona.getApellidos());
//                contentValues.put("telefono", persona.getTelefono());
//                contentValues.put("email",persona.getEmail());
//                contentValues.put("domicilio",persona.getDomicilio());
//                MainActivity.sqLiteWrite.insert(MainActivity.tableName, null, contentValues);

                ManejadorInputs.limpiarCampos(inputs);
                idNum++;
                id.setEnabled(true);
                id.setText(idNum.toString());
                id.setEnabled(false);
                nombres.requestFocus();
                Toast.makeText(AgregarActivity.this, "CONTACTO GUARDADO", Toast.LENGTH_SHORT).show();
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