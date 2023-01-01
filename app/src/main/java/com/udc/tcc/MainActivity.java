package com.udc.tcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.udc.tcc.model.DbHelper;
import com.udc.tcc.model.Persona;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Intent agregarActivityIntent, mostrarActivityIntent, mostrarActualizarActivityIntent
            , mostrarEliminarActivityIntent;
    public static List<Persona> contactos;
    public static DbHelper dbHelper;
    public static SQLiteDatabase sqLiteWrite, sqLiteRead;
    public static String dbName, tableName;

    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agregarActivityIntent = new Intent(this, AgregarActivity.class);
        mostrarActivityIntent = new Intent(this, MostrarListaActivity.class);
        mostrarActualizarActivityIntent = new Intent(this, MostrarActualizarActivity.class);
        mostrarEliminarActivityIntent = new Intent(this, MostrarEliminarActivity.class);
        contactos = new ArrayList<>();

        dbName = "agenda";
        tableName = "contactos";
        String query = "CREATE TABLE "+ tableName + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombres VARCHAR(255), " +
                "apellidos VARCHAR(255), " +
                "telefono VARCHAR(50), " +
                "email VARCHAR(255), " +
                "domicilio VARCHAR(255))";

        dbHelper = new DbHelper(this, 1, dbName, query);
        sqLiteWrite = dbHelper.getWritableDatabase();
        sqLiteRead = dbHelper.getReadableDatabase();

        createNotificationChannel();
        createNotification();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.menuItem1:
                startActivity(agregarActivityIntent);
                break;
            case R.id.menuItem2:
                //Toast.makeText(this, "MenuItem2", Toast.LENGTH_SHORT).show();
                startActivity(mostrarActivityIntent);
                break;
            case R.id.menuItem3:
                startActivity(mostrarActualizarActivityIntent);
                break;
            case R.id.menuItem4:
                startActivity(mostrarEliminarActivityIntent);
                break;
            case R.id.menuItemSalir:
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqLiteWrite.close();
        sqLiteRead.close();
        dbHelper.close();
    }
}