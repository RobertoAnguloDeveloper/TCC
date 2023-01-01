package com.udc.tcc.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.udc.tcc.MainActivity;
import com.udc.tcc.R;
import com.udc.tcc.controller.CustomAdapter;
import com.udc.tcc.model.Persona;

public class MostrarActualizarActivity extends AppCompatActivity {
    Intent agregarActivityIntent, mostrarActivityIntent, mostrarActualizarActivityIntent
            , mostrarEliminarActivityIntent;
    public static ListView listViewActualizar;
    public static CustomAdapter adapterActualizar;
    private Intent actualizarActivityIntent;
    public static Persona personaActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_actualizar);
        getSupportActionBar().setTitle("ACTUALIZAR CONTACTO");

        listViewActualizar = findViewById(R.id.listaContactosActualizar);
        actualizarActivityIntent = new Intent(this, ActualizarActivity.class);
        mostrarActivityIntent = new Intent(this, MostrarListaActivity.class);
        mostrarActualizarActivityIntent = new Intent(this, MostrarActualizarActivity.class);
        mostrarEliminarActivityIntent = new Intent(this, MostrarEliminarActivity.class);

        adapterActualizar = new CustomAdapter(this);
        listViewActualizar.setAdapter(adapterActualizar);

        listViewActualizar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                personaActualizar = MainActivity.contactos.get(i);
                startActivity(actualizarActivityIntent);
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