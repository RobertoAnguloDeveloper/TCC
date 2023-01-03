package com.udc.tcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.udc.tcc.model.Persona;
import com.udc.tcc.view.fragments.AgregarFragment;
import com.udc.tcc.view.fragments.ContactosFragment;
import com.udc.tcc.view.fragments.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static RequestQueue requestQueue;
    public static Context context;

    Intent agregarActivityIntent, mostrarActivityIntent, mostrarActualizarActivityIntent
            , mostrarEliminarActivityIntent;

    public static FragmentTransaction transaction;
    public static FragmentManager fragmentManager;
    Fragment homeFragment, agregarFragment, contactosFragment;
    public static List<Persona> contactos;
    public static Persona contacto;

    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        homeFragment = new HomeFragment();
        agregarFragment = new AgregarFragment();
        contactosFragment = new ContactosFragment();
        fragmentManager = getSupportFragmentManager();

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentFrame, homeFragment).commit();
        contactos = new ArrayList<>();
        contacto = new Persona();

        //API REQUESTS WITH VOLLEY
        requestQueue = Volley.newRequestQueue(this);
        getRequest();

        createNotificationChannel();
        createNotification();
    }

    public static void getRequest(){
        String api_request = "http://144.22.204.157:8080/api/Contacto/all";
        JsonArrayRequest get_request = new JsonArrayRequest(Request.Method.GET,
                api_request,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                Persona persona = new Persona();
                                JSONObject p_json = new JSONObject(response.get(i).toString());
                                persona.setId(p_json.getInt("id"));
                                persona.setImagen(p_json.getInt("imagen"));
                                persona.setNombres(p_json.getString("nombres"));
                                persona.setApellidos(p_json.getString("apellidos"));
                                persona.setTelefono(p_json.getString("telefono"));
                                persona.setEmail(p_json.getString("email"));
                                persona.setDomicilio(p_json.getString("domicilio"));

                                contactos.add(persona);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "ERROR EN GET "+error, Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(get_request);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        transaction = getSupportFragmentManager().beginTransaction();
        switch (menuItem.getItemId()){
            case R.id.menuItem1:
                transaction.replace(R.id.fragmentFrame, agregarFragment).commit();
                break;
            case R.id.menuItem2:
                transaction.replace(R.id.fragmentFrame, contactosFragment).commit();
                break;
            case R.id.menuItemSalir:
                finish();
                System.exit(0);
                finishAffinity();
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.icon_cut);
        builder.setContentTitle("CONTACTO APP");
        builder.setContentText("RVLR Soft te desea un feliz 2023");
        builder.setColor(Color.RED);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }
}