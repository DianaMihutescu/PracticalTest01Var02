package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    String clientAddress;
    int clientPort;
    String city;
    String informationType;
    TextView weatherView;

    Socket socket = null;

    public ClientThread(String clientAddress, int clientPort, String city, String informationType, TextView weatherView) {
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        this.city = city;
        this.informationType = informationType;
        this.weatherView = weatherView;
    }

    public void run() {
        try {
            socket = new Socket(clientAddress, clientPort);
            if (socket == null) {
                Log.e("wow", "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e("wow", "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            printWriter.println(city);
            printWriter.flush();
            printWriter.println(informationType);
            printWriter.flush();
            String weatherInformation;
            while ((weatherInformation = bufferedReader.readLine()) != null) {
                final String finalizedWeateherInformation = weatherInformation;
                weatherView.post(new Runnable() {
                    @Override
                    public void run() {
                        weatherView.setText(finalizedWeateherInformation);
                    }
                });
            }
        } catch (IOException ioException) {
            Log.e("wow", "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            ioException.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e("wow", "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    ioException.printStackTrace();

                }
            }
        }
    }
}
