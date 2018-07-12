package happycustomerbox.happycounter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStreamWriter;

public class Resultats extends AppCompatActivity implements View.OnClickListener{

    Button btnEnviar, btnReturn;

    TextView txtTotal, txt1, txt2, txt3, txt4, txt5, txtPorta, txtH1, txtH2, txtH3, txtH4, txtH5, txtCorreu;
    EditText edText;

    Bundle b;

    String str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        inicialitzar();

    }

    private void inicialitzar() {
        btnEnviar = findViewById(R.id.btnEnviar);
        btnReturn = findViewById(R.id.btnReturn);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txtTotal = findViewById(R.id.txtMostrarTotal);
        txtPorta = findViewById(R.id.txtPorta);
        txtH1 = findViewById(R.id.txtHora1);
        txtH2 = findViewById(R.id.txtHora2);
        txtH3 = findViewById(R.id.txtHora3);
        txtH4 = findViewById(R.id.txtHora4);
        txtH5 = findViewById(R.id.txtHora5);

        txtCorreu = findViewById(R.id.txtCorreu);
        edText = findViewById(R.id.edCorreu);

        btnReturn.setOnClickListener(this);
        btnEnviar.setOnClickListener(this);

        b = getIntent().getExtras();


        txtPorta.setText(b.getString("Porta"));
        txtTotal.setText(b.getString("Total"));

        txt1.setText(b.getString(""+b.getString("Total")));
        txtH1.setText(b.getString("H:"+b.getString("Total")));

        txt2.setText(b.getString(""+(Integer.parseInt(b.getString("Total"))-1)));
        txtH2.setText(b.getString("H:"+(Integer.parseInt(b.getString("Total"))-1)));

        txt3.setText(b.getString(""+(Integer.parseInt(b.getString("Total"))-2)));
        txtH3.setText(b.getString("H:"+(Integer.parseInt(b.getString("Total"))-2)));

        txt4.setText(b.getString(""+(Integer.parseInt(b.getString("Total"))-3)));
        txtH4.setText(b.getString("H:"+(Integer.parseInt(b.getString("Total"))-3)));

        txt5.setText(b.getString(""+(Integer.parseInt(b.getString("Total"))-4)));
        txtH5.setText(b.getString("H:"+(Integer.parseInt(b.getString("Total"))-4)));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnReturn:
                setResult(RESULT_CANCELED);
                break;
            case R.id.btnEnviar:
                enviar();
                break;
        }
        finish();
    }

    private void enviar() {
        try
        {
            OutputStreamWriter fout=
                    new OutputStreamWriter(
                            openFileOutput("texto"+b.getString("Porta")+".txt", Context.MODE_PRIVATE));
            str="Codi: "+b.getString("Porta")+"\nIncrement: "+b.getString("Total")+"\nDecrement: "+b.getString("TotalDec");
            for(int i = 0; i!=Integer.parseInt(b.getString("Total"));i++){
                str=str+"\n"+((b.getString(""+(Integer.parseInt(b.getString("Total"))-i))+" / "+b.getString("H:"+(Integer.parseInt(b.getString("Total"))-i))));
            }
            //fout.write(str);
            fout.close();
            //Toast.makeText(this, "Fichero guardado." , Toast.LENGTH_LONG).show();

            sendEmail();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }

    }

    protected void sendEmail() {
        String[] TO = {edText.getText().toString()}; //aquí pon tu correo
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asunto");
        emailIntent.putExtra(Intent.EXTRA_TEXT, str);

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,"No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }

}
