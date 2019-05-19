package ro.pub.cs.systems.eim.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class PracticalTest02MainActivity extends AppCompatActivity {

    TextView serverText, clientText, weatherView;
    EditText port, address, portClient, cityClient;
    Button connect, weather;
    Spinner spinner;


    ServerSocket serverSocket = null;
    ServerThread serverThread = null;
    ClientThread clientThread;
    HashMap<String, WeatherForecastInformation> data = new HashMap<String, WeatherForecastInformation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverText = findViewById(R.id.serverText);
        clientText = findViewById(R.id.clientText);
        weatherView = findViewById(R.id.weatherView);
        port = findViewById(R.id.port);
        address = findViewById(R.id.address);
        portClient = findViewById(R.id.portClient);
        cityClient = findViewById(R.id.city);
        connect = findViewById(R.id.connect);
        weather = findViewById(R.id.weather);
        spinner = findViewById(R.id.spinner);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverPort = port.getText().toString();
                if (serverPort == null || serverPort.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                serverThread = new ServerThread(Integer.parseInt(serverPort));
                if (serverThread.getServerSocket() == null) {
                    Log.e("wow", "[MAIN ACTIVITY] Could not create server thread!");
                    return;
                }
                serverThread.start();


            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientAddress = address.getText().toString();
                String clientPort = portClient.getText().toString();
                if (clientAddress == null || clientAddress.isEmpty()
                        || clientPort == null || clientPort.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (serverThread == null || !serverThread.isAlive()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String city = cityClient.getText().toString();
                String informationType = spinner.getSelectedItem().toString();
                if (city == null || city.isEmpty()
                        || informationType == null || informationType.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (city / information type) should be filled", Toast.LENGTH_SHORT).show();
                    return;
                }

                weatherView.setText("");

                clientThread = new ClientThread(
                        clientAddress, Integer.parseInt(clientPort), city, informationType, weatherView
                );
                clientThread.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.i("wow", "[MAIN ACTIVITY] onDestroy() callback method has been invoked");
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
}
