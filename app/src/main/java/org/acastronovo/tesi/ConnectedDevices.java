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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_devices);

            //definisco un array di stringhe
            //Per aggiungere un device collegato al broker e vederlo stampato a video è sufficiente
            //scrivere nell'array una stringa con il nome del dispositivo
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















