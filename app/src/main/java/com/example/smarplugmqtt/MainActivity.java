package com.example.smarplugmqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private EditText pnombre,pcontraseña;
    private CheckBox recordar;
    MqttAndroidClient client;
    String b="mk110casaprueba";
    String a="mk110prueba";
    String host="tcp://broker.mqtt-dashboard.com:1883";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pnombre=(EditText)findViewById(R.id.et_pnombre);
        pcontraseña=(EditText)findViewById(R.id.et_pcontraseña);
        recordar=(CheckBox)findViewById(R.id.recordarcheck);

        String clientId = MqttClient.generateClientId();
        client =new MqttAndroidClient(this.getApplicationContext(), host,clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this,"Actualizando Contraseña Online si se requiere",Toast.LENGTH_SHORT).show();
                    sub();
                    // We are connected
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        SharedPreferences preferences=getSharedPreferences("datos", Context.MODE_PRIVATE);

        pnombre.setText(preferences.getString("nombre",""));
        pcontraseña.setText(preferences.getString("contraseña",""));


    }
    public  void CambiarContraseña(View view){
        SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor=preferencias.edit();
        Obj_editor.putString("nombre",pnombre.getText().toString());


        if(recordar.isChecked()==true){
            Obj_editor.putString("contraseña",pcontraseña.getText().toString());

        }
        else {
            Obj_editor.putString("contraseña","");

        }
        Obj_editor.commit();

        Intent i =new Intent(this,CambiarUsuario.class);
        i.putExtra("contraactual",b);
        startActivity(i);
    }
    public  void  Entrar(View view){

        SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor=preferencias.edit();
        b=preferencias.getString("contraRegis",a);

        Obj_editor.putString("nombre",pnombre.getText().toString());
        if(recordar.isChecked()==true){
            Obj_editor.putString("contraseña",pcontraseña.getText().toString());

        }
        else {
            Obj_editor.putString("contraseña","");

        }
        Obj_editor.commit();

        if(pcontraseña.getText().toString().equals(b) && pnombre.getText().toString().replace(" ","").length()>0){
            if(pnombre.getText().toString().equals("mk110prueba"))

            Toast.makeText(this,"Entro a la base de datos ",Toast.LENGTH_SHORT).show();
            Intent i =new Intent(this,agregardispositivos.class);
            i.putExtra("nombreArchivo",pnombre.getText().toString());
            startActivity(i);

        }else {
            Toast.makeText(this,"Contraseña incorrecta o falta digitar nombre del archivo",Toast.LENGTH_SHORT).show();

        }


    }




    private void sub(){
        try {
            client.subscribe("sub_g_topic",1);
        }catch (MqttException e){
            Toast.makeText(getBaseContext(), "No se ha conectado al gateway", Toast.LENGTH_SHORT).show();
        }


    }

}