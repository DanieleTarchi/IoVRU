package org.acastronovo.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AvailableDevices extends AppCompatActivity {

    private String ArrayList;
    private String ArrayAdapter;
    private String ListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_devices);


        // definisco un array di stringhe
        String[] nameproducts = new String[]{"Product1", "Product2"};

        // definisco un ArrayList
        final ArrayList<String> listp = new ArrayList<String>();
        for (int i = 0; i < nameproducts.length; ++i) {
            listp.add(nameproducts[i]);
        }

        // recupero la lista dal layout
        final ListView mylist = (ListView) findViewById(R.id.listView1);

        // creo e istruisco l'adattatore
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listp);

        // inietto i dati
        mylist.setAdapter(adapter);

    }

}