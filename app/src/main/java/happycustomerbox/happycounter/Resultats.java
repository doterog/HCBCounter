package happycustomerbox.happycounter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
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

    TextView txtTotal, txt1, txt2, txt3, txt4, txt5, txtPorta, txtCorreu;
    EditText email;

    Bundle b;

    String str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        txtCorreu = findViewById(R.id.txtCorreu);
        email = findViewById(R.id.edCorreu);

        btnReturn.setOnClickListener(this);
        btnEnviar.setOnClickListener(this);

        b = getIntent().getExtras();

        if(b.getString("email")!=null){
            email.setText(b.getString("email"));
        }

        txtPorta.setText(b.getString("Porta"));
        txtTotal.setText(b.getString("Total"));

        if((b.getString(b.getString("Total")))!=null){
            txt1.setText(b.getString(b.getString("Total"))+" - "+
                    b.getString("H:"+b.getString("Total")));
            if(Integer.parseInt(b.getString(b.getString("Total")))==1){
                txt1.setTextColor(Color.GREEN);
            }else if(Integer.parseInt(b.getString(b.getString("Total")))==-1){
                txt1.setTextColor(Color.RED);
            }
        }

        if(b.getString(""+(Integer.parseInt(b.getString("Total"))-1))!=null){
            txt2.setText(b.getString(""+(Integer.parseInt(b.getString("Total"))-1))+" - "+
                    b.getString("H:"+(Integer.parseInt(b.getString("Total"))-1)));
            if(Integer.parseInt(b.getString(""+(Integer.parseInt(b.getString("Total"))-1)))==1){
                txt2.setTextColor(Color.GREEN);
            }else if(Integer.parseInt(b.getString(""+(Integer.parseInt(b.getString("Total"))-1)))==-1){
                txt2.setTextColor(Color.RED);
            }
        }

        if(b.getString(""+(Integer.parseInt(b.getString("Total"))-2))!=null){
            txt3.setText(b.getString(""+(Integer.parseInt(b.getString("Total"))-2))+" - "+
                    b.getString("H:"+(Integer.parseInt(b.getString("Total"))-2)));
            if(Integer.parseInt(b.getString(""+(Integer.parseInt(b.getString("Total"))-2)))==1){
                txt3.setTextColor(Color.GREEN);
            }else if(Integer.parseInt(b.getString(""+(Integer.parseInt(b.getString("Total"))-2)))==-1){
                txt3.setTextColor(Color.RED);
            }
        }

        if(b.getString(""+(Integer.parseInt(b.getString("Total"))-3))!=null){
            txt4.setText(b.getString(""+(Integer.parseInt(b.getString("Total"))-3))+" - "+
                    b.getString("H:"+(Integer.parseInt(b.getString("Total"))-3)));
            if(Integer.parseInt(b.getString(""+(Integer.parseInt(b.getString("Total"))-3)))==1){
                txt4.setTextColor(Color.GREEN);
            }else if(Integer.parseInt(b.getString(""+(Integer.parseInt(b.getString("Total"))-3)))==-1){
                txt4.setTextColor(Color.RED);
            }
        }

        if(b.getString(""+(Integer.parseInt(b.getString("Total"))-4))!=null){
            txt5.setText(b.getString(""+(Integer.parseInt(b.getString("Total"))-4))+" - "+
                    b.getString("H:"+(Integer.parseInt(b.getString("Total"))-4)));
            if(Integer.parseInt(b.getString(""+(Integer.parseInt(b.getString("Total"))-4)))==1){
                txt5.setTextColor(Color.GREEN);
            }else if(Integer.parseInt(b.getString(""+(Integer.parseInt(b.getString("Total"))-4)))==-1){
                txt5.setTextColor(Color.RED);
            }
        }

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
        String[] TO = {email.getText().toString()}; //aquí pon tu correo
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
