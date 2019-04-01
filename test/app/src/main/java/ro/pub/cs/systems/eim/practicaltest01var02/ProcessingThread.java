package ro.pub.cs.systems.eim.practicaltest01var02;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread {

    private int suma;
    private int dif;
    private boolean isRunning = true;
    private Context context = null;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;

        suma = (firstNumber + secondNumber);
        dif = firstNumber - secondNumber;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            String[] actions = {"1", "2"};
            for (int i = 0; i < 2; i ++) {
                sendMessage(actions[i]);
                sleep();
            }
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra("message", + suma + " " + dif);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }


    public void stopThread() {
        isRunning = false;
    }
}
