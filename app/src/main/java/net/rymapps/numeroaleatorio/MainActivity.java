package net.rymapps.numeroaleatorio;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Variables de los controles
    private TextView lblPuntosYo, lblPuntosAndroid, lblMensaje;
    private ImageView imgJuego;
    private int numGenerado;
    private int numJugador;
    private int puntosYo;
    private int puntosAndroid;

    private AdView mAdView; //Admob

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**** Asignar objetos a recursos ***/
        lblPuntosYo = (TextView) findViewById(R.id.lblPuntosYo);
        lblPuntosAndroid = (TextView) findViewById(R.id.lblPuntosAndroid);
        lblMensaje = (TextView) findViewById(R.id.lblMensaje);
        imgJuego = (ImageView) findViewById(R.id.imgJuego);

        puntosYo = 0;
        puntosAndroid = 0;

        /*Cargar Admob*/
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void clic_boton(View v) {
        switch (v.getId()) {
            case R.id.btnPiedra:
                numJugador = 1;
                break;
            case R.id.btnPapel:
                numJugador = 2;
                break;
            case R.id.btnTijeras:
                numJugador = 3;
                break;
        }

        jugar();
        String mensaje = comparar(numJugador, numGenerado);

        if(mensaje.contains("ganado"))
            puntosYo++;
        else if(mensaje.contains("perdido"))
            puntosAndroid++;

        lblPuntosYo.setText(String.valueOf(puntosYo));
        lblPuntosAndroid.setText(String.valueOf(puntosAndroid));

        lblMensaje.setText(mensaje);

        //Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuRestart:
                reiniciaPuntos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void reiniciaPuntos() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Reiniciar puntos");
        dialogo.setMessage("¿Está seguro de reiniciar la puntuación del juego a 0?");
        dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                puntosYo = 0;
                puntosAndroid = 0;
                lblPuntosYo.setText(String.valueOf(puntosYo));
                lblPuntosAndroid.setText(String.valueOf(puntosAndroid));
                lblMensaje.setText("-");
                Toast.makeText(MainActivity.this, "Puntos reiniciados", Toast.LENGTH_SHORT).show();
            }
        });
        dialogo.setNegativeButton("No", null);
        dialogo.show();
    }

    private void jugar() {
        numGenerado = getRandomNumber(1, 3);

        switch (numGenerado) {
            case 1:
                imgJuego.setImageResource(R.drawable.piedra);
                break;
            case 2:
                imgJuego.setImageResource(R.drawable.papel);
                break;
            case 3:
                imgJuego.setImageResource(R.drawable.tijeras);
                break;
        }
    }

    private String comparar(int i, int j) {
        if(i == 1 & j == 2)
            return "Papel envuelve Piedra, has perdido!!!!";

        if(i == 1 & j == 3)
            return "Piedra rompe Tijeras, has ganado!!!!";

        if(i == 2 & j == 1)
            return "Papel envuelve Piedra, has ganado!!!!";

        if(i == 2 & j == 3)
            return "Tijeras cortan Papel, has perdido!!!!";

        if(i == 3 & j == 1)
            return "Piedra rompe Tijeras, has perdido!!!!";

        if(i == 3 & j == 2)
            return "Tijeras cortan Papel, has ganado!!!!";

        return "Empate, nadie gana!!!!";

    }

    private int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }
}
