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

public class MostrarEliminarActivity extends AppCompatActivity {
    Intent agregarActivityIntent, mostrarActivityIntent, mostrarActualizarActivityIntent
            , mostrarEliminarActivityIntent;
    public static ListView listViewEliminar;
    public static CustomAdapter adapterEliminar;
    private Intent eliminarActivityIntent;
    public static Persona personaEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_eliminar);
        getSupportActionBar().setTitle("ELIMINAR CONTACTO");

        listViewEliminar = findViewById(R.id.listaContactosEliminar);
        eliminarActivityIntent = new Intent(this, EliminarActivity.class);
        mostrarActivityIntent = new Intent(this, MostrarListaActivity.class);
        mostrarActualizarActivityIntent = new Intent(this, MostrarActualizarActivity.class);
        mostrarEliminarActivityIntent = new Intent(this, MostrarEliminarActivity.class);

        adapterEliminar = new CustomAdapter(this);
        listViewEliminar.setAdapter(adapterEliminar);

        listViewEliminar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                personaEliminar = MainActivity.contactos.get(i);
                startActivity(eliminarActivityIntent);
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