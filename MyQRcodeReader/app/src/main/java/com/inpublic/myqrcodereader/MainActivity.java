package com.inpublic.myqrcodereader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnScan;
    TextView txtResp;
    TextView txtRestaurante;
    TextView txtMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = (Button)findViewById(R.id.btn_scan);
        txtResp = (TextView)findViewById(R.id.txt_resposta);
        txtRestaurante = (TextView) findViewById(R.id.txt_restaurante);
        txtMesa = (TextView) findViewById(R.id.txt_mesa);


        eventoButton();
    }


    private void eventoButton() {
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("SCAN");
                intentIntegrator.setCameraId(0);
                intentIntegrator.initiateScan();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(intentResult != null){

            if (intentResult.getContents() !=  null){

                alert(intentResult.getContents().toString());
                txtResp.setText(intentResult.getContents().toString());

                try {

                    JSONObject jsonObject = new JSONObject(intentResult.getContents().toString());

                    Log.i("Scanxxx","Json restaurante: " + jsonObject.getString("id_restaurante"));

                    txtRestaurante.setText(jsonObject.getString("id_restaurante"));
                    txtMesa.setText(jsonObject.getString("mesa"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                alert("Scan cancelado");
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

}
