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
    private final String serverUri = "tcp://192.168.1.2:1883";
    private final String user = "andrea";
    private final String pwd = "1234";
    private MemoryPersistence persistance;


    private String topic;
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
            client = new MqttAndroidClient(getApplicationContext(), serverUri, clientId);
            IMqttToken token = client.connect(mqttConnectOptions);

            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
            e.printStackTrace();
        }
    }

    private void sub () {
        try {
            client.subscribe("sensorsDevice/temp", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str1 = new String(message.getPayload());
                    temperature.setText("Temperature: " + str1 + "°C");
                    suba();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Delivery Complete");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void suba () {
        try {
            client.subscribe("HeartValue", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str2 = new String(message.getPayload());
                    heartbeat.setText("Heartbeat: " + str2 + " Bpm");
                    subb();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Delivery Complete");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void subb () {
        try {
            client.subscribe("sensorsDevice/humidity", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str3 = new String(message.getPayload());
                    humidity.setText("Humidity: " + str3 + "%");
                    subc();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Delivery Complete");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void subc () {
        try {
            client.subscribe("testPos", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str4 = new String(message.getPayload());
                    position.setText("Position: " + str4);
                    subd();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Delivery Complete");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void subd () {
        try {
            client.subscribe("sensorsDevice/altitude", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str5 = new String(message.getPayload());
                    altitude.setText("Altitude: " + str5 + " m");
                    sube();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Delivery Complete");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void sube () {
        try {
            client.subscribe("sensorsDevice/pressure", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str6 = new String(message.getPayload());
                    pressure.setText("Pressure: " + str6 + " hPa");
                    subf();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Delivery Complete");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void subf () {
        try {
            client.subscribe("sensorsDevice/pedometer", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str7 = new String(message.getPayload());
                    pedometer.setText("Pedometer: " + str7 + " Steps");
                    subg();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Delivery Complete");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void subg () {
        try {
            client.subscribe("Calories", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str8 = new String(message.getPayload());
                    calories.setText("Calories: " + str8 + " Kcal");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Delivery Complete");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}