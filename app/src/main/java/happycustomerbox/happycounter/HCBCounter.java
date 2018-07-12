package happycustomerbox.happycounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class HCBCounter extends AppCompatActivity implements View.OnClickListener {

    TextView txtHora, txtInn, txtOut, txtTotal;

    Button btnMostrar, btnInn, btnOut, btnReset;

    int intInn, intOut, intIncrement, intDecrement;
    boolean boolSumatori =true; //True = Increment, false=Decrement.

    String strInn, strOut, strTotal, strHora;
    EditText edtText;

    Intent i;
    Bundle b;

    ArrayList<Integer> pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happy_counter);

        inicialitzar();
        reset();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cam_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.MenuSumatori:
                changeSumatori();
                break;
            case R.id.MenuReset:
                alert();
                break;
            case R.id.MenuEnviar:
                enviar();
                break;
            case R.id.MenuShowSumatori:
                changeSumatori();
        }


        return super.onOptionsItemSelected(item);
    }

    private void changeSumatori() {
        if(boolSumatori){
            boolSumatori = false;
        }else{
            boolSumatori = true;
        }
        sumatori();
    }

    private void inicialitzar(){
        i = new Intent(this, Resultats.class);
        b = new Bundle();

        txtHora = findViewById(R.id.txtHora);
        txtInn = findViewById(R.id.txtInn);
        txtOut = findViewById(R.id.txtOut);
        txtTotal = findViewById(R.id.txtTotal);

        edtText = findViewById(R.id.edtText);

        btnMostrar = findViewById(R.id.btnMostrarDades);
        btnInn = findViewById(R.id.btnInn);
        btnOut = findViewById(R.id.btnOut);
        btnReset = findViewById(R.id.btnReset);

        btnMostrar.setOnClickListener(this);
        btnOut.setOnClickListener(this);
        btnInn.setOnClickListener(this);
        btnReset.setOnClickListener(this);

    }

    private void reset(){
        txtInn.setText(strInn=""+(intInn=0));
        txtOut.setText(strOut=""+(intOut=0));
        txtTotal.setText(strTotal=""+(intIncrement=0));
        intDecrement=0;
    }

    private void alert(){
        final AlertDialog.Builder alertReset = new AlertDialog.Builder(this);
        alertReset.setMessage("Esta seguro? Si acepta se perderan los datos.");
        alertReset.setTitle("RESET");
        alertReset.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset();
            }
        });
        alertReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = alertReset.create();

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnInn:
                inn();
                break;
            case R.id.btnOut:
                out();
                break;
            case R.id.btnReset:
                alert();
                break;
            case R.id.btnMostrarDades:
                enviar();
        }
    }

    private void enviar() {

        b.putString("Total",""+intIncrement);
        b.putString("TotalDec",""+intDecrement);
        b.putString("Porta",edtText.getText().toString());
        i.putExtras(b);
        startActivityForResult(i,0);
    }

    private void out() {
        intOut++;
        intIncrement++;
        intDecrement=intInn-intOut;
        txtOut.setText(strOut=""+(intOut));
        txtTotal.setText(strTotal=""+(intIncrement));
        txtHora.setText(strHora = ""+(new Date().toString()));

        b.putString(strTotal,"-1");
        b.putString("H:"+strTotal,strHora);
    }

    private void inn() {
        intInn++;
        intIncrement++;
        intDecrement=intInn-intOut;
        txtInn.setText(strInn=""+(intInn));
        txtTotal.setText(strTotal=""+(intIncrement));
        txtHora.setText(strHora = ""+(new Date().toString()));

        b.putString(strTotal,"1");
        b.putString("H:"+strTotal,strHora);
    }

    private void sumatori(){
        if(boolSumatori){
            txtTotal.setText(strTotal=""+(intDecrement));
        }else{
            txtTotal.setText(strTotal=""+(intIncrement));
        }
    }
}
