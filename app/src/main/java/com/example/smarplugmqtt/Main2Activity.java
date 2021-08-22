package com.example.smarplugmqtt;
import com.example.smarplugmqtt.ChartHelper;
import com.github.mikephil.charting.charts.LineChart;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.w3c.dom.Text;

import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {
    ChartHelper mChart;
    LineChart chart;
    TextView mensajeprueba;
    MqttAndroidClient client;
    String host="tcp://broker.mqtt-dashboard.com:1883";
    byte[] receive;
    String c="";
    String mac="";
    String blename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mensajeprueba=(TextView)findViewById(R.id.pruebamensage);
        chart = (LineChart) findViewById(R.id.chart);
        mChart = new ChartHelper(chart);
        blename= getIntent().getStringExtra("blename");;

        String clientId = MqttClient.generateClientId();

        client =new MqttAndroidClient(this.getApplicationContext(), host,clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(Main2Activity.this,"Actualizando Contraseña Online si se requiere",Toast.LENGTH_SHORT).show();
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

                receive=message.getPayload();

                int header = receive[0] & 0xFF;
                if (header == 0x21)// 蓝牙广播数据
                {

                    int length = receive[1] & 0xFF;

                        c="";
                        byte[] deviceBytes = Arrays.copyOfRange(receive, 3 + length, receive.length);

                    int deviceLength = deviceBytes[0] & 0xff;

                    mac = bytesToHexString(Arrays.copyOfRange(deviceBytes, 1, 7));
                    c="El mac es: "+mac+" ";

                        int rssi = deviceBytes[7];
                        c=c+"El rssi es: "+ rssi +"\n";
                        int dataLength = deviceBytes[8] & 0xff;
                        String rawData = bytesToHexString(Arrays.copyOfRange(deviceBytes, 9, 9+dataLength));
                        c=c+"El RawData es: "+rawData+"\n";
                        int result=rawData.indexOf("ffff");
                        result=result+4;
                        int nameLength = deviceLength - 8 - dataLength;
                        if (nameLength > 0){
                            String name = new String(Arrays.copyOfRange(deviceBytes, 9+dataLength, 9+dataLength+nameLength));
                            if(name.indexOf(blename)>=0){
                            c=c+"El name del device es: "+name+"\n";

                            double voltaje = Integer.parseInt(rawData.substring(result+6,result+10), 16)*0.1;
                            int corriente = Integer.parseInt(rawData.substring(result+10,result+16), 16);
                            double power = Integer.parseInt(rawData.substring(result+16,result+20), 16)*0.1;
                            double energy = Integer.parseInt(rawData.substring(result+20,result+26), 16)/100;
                            c=c+"voltaje: "+voltaje+" v \n corriente: "+corriente+" mA \n potencia: "+power+" W \n energia: "+energy+" j";
                            mensajeprueba.setText("mensaje:"+c);
                            mChart.addEntry(Float.valueOf((float) power));
                            }
                        }


                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

    private void sub(){
        try {
            client.subscribe("sub_g_topic",1);
        }catch (MqttException e){
            Toast.makeText(getBaseContext(), "Conectando a bluetooh sensors", Toast.LENGTH_SHORT).show();
        }


    }
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
