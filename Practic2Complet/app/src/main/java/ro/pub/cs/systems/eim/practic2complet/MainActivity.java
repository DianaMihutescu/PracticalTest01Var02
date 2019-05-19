package ro.pub.cs.systems.eim.practic2complet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView serverText, clientText, weatherView;
    EditText serverPort, clientPort, address, city;
    Button connect, weather;
    Spinner spinner;

    ServerThread serverThread = null;
    ClientThread clientThread = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverText = findViewById(R.id.serverText);
        clientText = findViewById(R.id.clientText);
        serverPort = (EditText) findViewById(R.id.serverPort);
        clientPort = findViewById(R.id.clientPort);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        connect = findViewById(R.id.connect);
        weather = findViewById(R.id.weather);
        spinner = findViewById(R.id.spinner);
        weatherView = findViewById(R.id.weatherView);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String svPort = serverPort.getText().toString();
                if (svPort != null && !svPort.isEmpty()) {
                    // Verificare ca e corect
                    serverThread = new ServerThread(Integer.parseInt(svPort));
                    //verificare iara
                    serverThread.start();
                }
                else
                {
                    // TODO toast
                }
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = city.getText().toString();
                String query2 = spinner.getSelectedItem().toString();

                String clPort = clientPort.getText().toString();
                String clAddress = address.getText().toString();

                clientThread = new ClientThread(clAddress, Integer.parseInt(clPort), query, query2, weatherView);
                clientThread.start();
            }
        });
    }

    @Override
    protected void onDestroy() {

        if (serverThread != null)
            serverThread.stopThread();
        super.onDestroy();


    }
}
