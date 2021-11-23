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
    private final String serverUri = "tcp://192.168.1.3:1883";
    private final String user = "alberto";
    private final String pwd = "1708";
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
                client.subscribe("testTemp", qos);

                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {
                        Log.d(TAG, "Connection Lost");
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        String str1 = new String(message.getPayload());
                        Log.d(TAG, "messaggio arrivato");
                        temperature.setText("Temperature: " + str1 + " °C");
                        suba();
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

        private void suba () {
            try {
                client.subscribe("testHeart", qos);

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
            client.subscribe("testHum", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str2 = new String(message.getPayload());
                    humidity.setText("Humidity: " + str2 + "%");
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
                    String str2 = new String(message.getPayload());
                    position.setText("Position: " + str2);
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
            client.subscribe("testAlt", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str2 = new String(message.getPayload());
                    altitude.setText("Altitude: " + str2 + " m");
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
            client.subscribe("testPres", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str2 = new String(message.getPayload());
                    pressure.setText("Pressure: " + str2 + " Pa");
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
            client.subscribe("testPed", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str2 = new String(message.getPayload());
                    pedometer.setText("Pedometer: " + str2 + " Steps");
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
            client.subscribe("testCal", qos);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String str2 = new String(message.getPayload());
                    calories.setText("Calories: " + str2 + " Kcal");
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