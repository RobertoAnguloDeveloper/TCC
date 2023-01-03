package com.udc.tcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.udc.tcc.model.Persona;
import com.udc.tcc.view.fragments.AgregarFragment;
import com.udc.tcc.view.fragments.ContactosFragment;
import com.udc.tcc.view.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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

        homeFragment = new HomeFragment();
        agregarFragment = new AgregarFragment();
        contactosFragment = new ContactosFragment();
        fragmentManager = getSupportFragmentManager();

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentFrame, homeFragment).commit();

        contactos = new ArrayList<>();
        contacto = new Persona();

        createNotificationChannel();
        createNotification();
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