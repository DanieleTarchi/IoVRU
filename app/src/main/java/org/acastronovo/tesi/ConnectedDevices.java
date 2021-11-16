package org.acastronovo.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import java.util.ArrayList;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.app.Service;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class ConnectedDevices extends AppCompatActivity {

    MqttAsyncClient client;
    String TAG = "MqttService";
    private final String serverUri = "tcp://192.168.1.1883"; //Al posto di 192.168.1.2 devi mettere l'indirizzo ip del tuo raspbery all'interno della tua rete
    private final String user = "alberto";
    private final String pwd = "1708";
    private MemoryPersistence persistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_devices);

        String clientId = MqttClient.generateClientId();

        try {
            client = new MqttAsyncClient(serverUri, clientId, persistance);
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setUserName(user);
            mqttConnectOptions.setPassword(pwd.toCharArray());
            IMqttToken token = client.connect(mqttConnectOptions);

            try{
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.e(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.e(TAG, "Message arrived");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.e(TAG, "Delivery Complete");
                }
            });

            token = client.connect(mqttConnectOptions);

        }catch (MqttException ex){
            ex.printStackTrace();
        }


            //definisco un array di stringhe
            //Per aggiungere un device collegato al broker e vederlo stampato a video basta scrivere
            //nell'array una stringa con il nome del dispositivo
            String[] namedevices = new String[]{"SM-G950F"};

            // definisco un ArrayList
            final ArrayList<String> listp = new ArrayList<String>();
            for (int i = 0; i < namedevices.length; ++i) {
                listp.add(namedevices[i]);
            }

            // recupero la lista dal layout
            final ListView mylist = (ListView) findViewById(R.id.listView1);

            // creo e istruisco l'adattatore
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listp);

            // inietto i dati
            mylist.setAdapter(adapter);

            mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id){
                final Intent intent = new Intent(ConnectedDevices.this, ReceivedData.class);
                startActivity(intent);
            }

            });

    }

}















