package org.acastronovo.tesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSubscribe;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.Token;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.lang.String;


public class ReceivedData extends AppCompatActivity {

    //UI
    TextView temperature;
    TextView heartbeat;
    TextView humidity;
    TextView position;
    TextView altitude;
    TextView pressure;
    TextView pedometer;
    TextView calories;

    MqttAndroidClient client;
    String TAG = "ReceivedData";
    private final String serverUri = "tcp://192.168.1.4:1883";
    private final String user = "alberto";
    private final String pwd = "1708";
    private MemoryPersistence persistance;


    private String topic = "testTemp";
    int qos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_data);

        //UI
        temperature = findViewById(R.id.temperature);
        heartbeat = findViewById(R.id.heartbeat);
        humidity = findViewById(R.id.humidity);
        position = findViewById(R.id.position);
        altitude = findViewById(R.id.altitude);
        pressure = findViewById(R.id.pressure);
        pedometer = findViewById(R.id.pedometer);
        calories = findViewById(R.id.calories);

        connect();

    }

        private void connect () {
            String clientId = MqttClient.generateClientId();

            try {
                MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
                mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
                mqttConnectOptions.setCleanSession(true);
                mqttConnectOptions.setAutomaticReconnect(true);
                mqttConnectOptions.setUserName(user);
                mqttConnectOptions.setPassword(pwd.toCharArray());
                client = new MqttAndroidClient(this.getApplicationContext(), serverUri, clientId);
                IMqttToken token = client.connect(mqttConnectOptions);

                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "eseguita la prima parte");

                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        // We are connected
                        Log.d(TAG, "onSuccess");
                        sub();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        // Something went wrong e.g. connection timeout or firewall problems
                        Log.d(TAG, "onFailure");

                    }
                });


            } catch (MqttException e) {
                Log.d(TAG, "sei dentro il primo catch");
                e.printStackTrace();
            }
        }

        private void sub () {
            try {
                client.subscribe(topic, qos);
                Log.d(TAG, "eseguita la seconda parte");
                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {
                        Log.d(TAG, "Connection Lost");
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        String str1 = new String(message.getPayload());
                        Log.d(TAG, "messaggio arrivato");
                        temperature.setText("Temperature: " + str1);
                        Log.d(TAG, "lets go");
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {
                        Log.d(TAG, "Delivery Complete");
                    }
                });

            } catch (MqttException e) {
                Log.d(TAG, "sono dentro il secondo catch");
                e.printStackTrace();
            }
        }

}