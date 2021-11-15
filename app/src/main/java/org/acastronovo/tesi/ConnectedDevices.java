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

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import org.eclipse.paho.client.mqttv3.MqttException;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class ConnectedDevices extends AppCompatActivity {

    MqttAsyncClient client;
    String TAG = "MqttService";
    private final String serverUri = "tcp://192.168.1.1883";
    private final String user = "Device";
    private final String pwd = "pass";
    private MemoryPersistence persistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_devices);

        String clientId = MqttClient.generateClientId();

        try {
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setUserName(user);
            mqttConnectOptions.setPassword(pwd.toCharArray());
            client = new MqttAsyncClient(serverUri, clientId, persistance);
            IMqttToken token = client.connect(mqttConnectOptions);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewal
                    Log.d(TAG, "onFailure");
                }
            });
        }catch (MqttException ex){
            ex.printStackTrace();
        }


        // definisco un array di stringhe
            String[] namedevices = new String[]{clientId};

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