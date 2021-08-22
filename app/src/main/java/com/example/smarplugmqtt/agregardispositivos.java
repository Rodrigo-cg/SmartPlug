package com.example.smarplugmqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class agregardispositivos extends AppCompatActivity {
    EditText bl1,bl2,bl3,bl4,bl5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregardispositivos);
        bl1=(EditText)findViewById(R.id.bl1);
        bl2=(EditText)findViewById(R.id.bl2);
        bl3=(EditText)findViewById(R.id.bl3);
        bl4=(EditText)findViewById(R.id.bl4);
        bl5=(EditText)findViewById(R.id.bl5);

        SharedPreferences dispositivosBle=getSharedPreferences("ble", Context.MODE_PRIVATE);
        bl1.setText(dispositivosBle.getString("bl1",""));
        bl2.setText(dispositivosBle.getString("bl2",""));
        bl3.setText(dispositivosBle.getString("bl3",""));
        bl4.setText(dispositivosBle.getString("bl4",""));
        bl5.setText(dispositivosBle.getString("bl5",""));
    }

    public void analizar1(View view){
        SharedPreferences preferencias=getSharedPreferences("ble",Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor=preferencias.edit();
        Obj_editor.putString("bl1",bl1.getText().toString());
        Obj_editor.commit();

        Intent i =new Intent(this,Main2Activity.class);


        if (!(bl1.getText().toString().equals(null) || bl1.getText().toString().equals(""))){
            i.putExtra("blename", bl1.getText().toString());
            startActivity(i);
        }else{
            Toast.makeText(agregardispositivos.this,"Digite el nombre del smartplug 1 conectado al gateway",Toast.LENGTH_SHORT).show();
        }

    }
    public void analizar2(View view){
        SharedPreferences preferencias=getSharedPreferences("ble",Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor=preferencias.edit();
        Obj_editor.putString("bl2",bl2.getText().toString());
        Obj_editor.commit();

        Intent i =new Intent(this,Main2Activity.class);


        if (!(bl2.getText().toString().equals(null) || bl2.getText().toString().equals(""))) {
            i.putExtra("", bl2.getText().toString());
            startActivity(i);
        }else{
            Toast.makeText(agregardispositivos.this,"Digite el nombre del smartplug 2 conectado al gateway",Toast.LENGTH_SHORT).show();
        }
    }
    public void analizar3(View view){
        SharedPreferences preferencias=getSharedPreferences("ble",Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor=preferencias.edit();
        Obj_editor.putString("bl3",bl3.getText().toString());
        Obj_editor.commit();

        Intent i =new Intent(this,Main2Activity.class);


        if (!(bl3.getText().toString().equals(null) || bl3.getText().toString().equals(""))){
            i.putExtra("blename", bl3.getText().toString());
            startActivity(i);
        }else{
            Toast.makeText(agregardispositivos.this,"Digite el nombre del smartplug 3 conectado al gateway",Toast.LENGTH_SHORT).show();
        }

    }
    public void analizar4(View view){
        SharedPreferences preferencias=getSharedPreferences("ble",Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor=preferencias.edit();
        Obj_editor.putString("bl4",bl4.getText().toString());
        Obj_editor.commit();

        Intent i =new Intent(this,Main2Activity.class);


        if (!(bl4.getText().toString().equals(null) || bl4.getText().toString().equals(""))){
            i.putExtra("blename", bl4.getText().toString());
            startActivity(i);
        }else{
            Toast.makeText(agregardispositivos.this,"Digite el nombre del smartplug 4 conectado al gateway",Toast.LENGTH_SHORT).show();
        }

    }
    public void analizar5(View view){
        SharedPreferences preferencias=getSharedPreferences("ble",Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor=preferencias.edit();
        Obj_editor.putString("bl5",bl5.getText().toString());
        Obj_editor.commit();


        if (!(bl5.getText().toString().equals(null) || bl5.getText().toString().equals(""))){

            Intent i =new Intent(this,Main2Activity.class);
            i.putExtra("blename", bl5.getText().toString());
            startActivity(i);
        }else{
          Toast.makeText(agregardispositivos.this,"Digite el nombre del smartplug 5 conectado al gateway",Toast.LENGTH_SHORT).show();
        }

    }

}
