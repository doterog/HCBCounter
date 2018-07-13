package happycustomerbox.happycounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class HCBCounter extends AppCompatActivity implements View.OnClickListener {

    TextView txtHora, txtInn, txtOut, txtTotal;

    Button btnMostrar, btnInn, btnOut, btnReset;

    int intInn, intOut, intIncrement, intDecrement;
    boolean boolSumatori =false; //True = Decrement, false=Increment.

    String strInn, strOut, strTotal, strHora;
    EditText edEmail, edtText;

    Intent i;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happy_counter);

        inicialitzar();
        crono();

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
            case R.id.MenuIncrement:
                boolSumatori = false;
                actualitzar();
                break;
            case R.id.MenuReset:
                alert();
                break;
            case R.id.MenuEnviar:
                enviar();
                break;
            case R.id.MenuDecrement:
                boolSumatori = true;
                actualitzar();
                break;
            case R.id.MenuSetting:
                info();
                break;
            case R.id.MenuEmail:
                email();

        }
        return super.onOptionsItemSelected(item);
    }

    private void email(){
        final AlertDialog.Builder alertReset = new AlertDialog.Builder(this);
        alertReset.setMessage("E-mail:");
        // set prompts.xml to alertdialog builder
        final EditText et = new EditText(this);
        alertReset.setView(et);

        // set dialog message
        alertReset.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                edEmail.setText(et.getText().toString());
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertReset.create();
        // show it
        alertDialog.show();
    }


    private void inicialitzar(){
        i = new Intent(this, Resultats.class);
        b = new Bundle();

        txtInn = findViewById(R.id.txtInn);
        txtOut = findViewById(R.id.txtOut);
        txtTotal = findViewById(R.id.txtTotal);
        txtHora = findViewById(R.id.txtHora);

        edtText = findViewById(R.id.edtText);
        edEmail = findViewById(R.id.edEmail);

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

    private void info(){
        final AlertDialog.Builder alertReset = new AlertDialog.Builder(this);
        alertReset.setMessage("done by \nHappyCustomerBox SL\n" +
                "Version: 1.2");
        alertReset.setTitle("HCBCounter");
        alertReset.setNegativeButton("Ok.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = alertReset.create();

        dialog.show();
    }

    private void alert(){
        final AlertDialog.Builder alertReset = new AlertDialog.Builder(this);
        alertReset.setMessage("Are you sure?");
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
        b.putString("email",edEmail.getText().toString());
        i.putExtras(b);
        startActivityForResult(i,0);
    }

    private void out() {
        intOut++;
        intIncrement++;
        intDecrement=intInn-intOut;
        txtOut.setText(strOut=""+(intOut));
        actualitzar();
        b.putString(""+intIncrement,"-1");
        b.putString("H:"+intIncrement,strHora);
    }

    private void inn() {
        intInn++;
        intIncrement++;
        intDecrement=intInn-intOut;
        txtInn.setText(strInn=""+(intInn));
        actualitzar();
        b.putString(""+intIncrement,"1");
        b.putString("H:"+intIncrement,strHora);
    }

    //Guardar estado, evitar el reinicio no intencionado de a app

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("Inn",intInn);
        bundle.putInt("Out",intOut);
        bundle.putInt("Dec",intDecrement);
        bundle.putInt("Inc",intIncrement);
        bundle.putString("Hora",strHora);
        bundle.putString("Total",edtText.toString());
        bundle.putBundle("Bundle",b);
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        intInn = bundle.getInt("Inn");
        intOut = bundle.getInt("Out");
        intDecrement = bundle.getInt("Dec");
        intIncrement = bundle.getInt("Inc");
        strHora = bundle.getString("Hora");
        strTotal = bundle.getString("Total");
        b = bundle.getBundle("Bundle");
        actualitzar();
    }

    private void actualitzar(){
        if(boolSumatori){
            txtTotal.setText(strTotal=""+(intDecrement));
        }else{
            txtTotal.setText(strTotal=""+(intIncrement));
        }
    }

    private void crono() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy\nhh:mm:ss.SSS a");
                                strHora = format.format(date);
                                txtHora.setText(strHora);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }
}
