package ro.pub.cs.systems.eim.practicaltest01var02;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Var02Service extends Service {

    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int firstNumber = Integer.valueOf(intent.getStringExtra("val1"));
        int secondNumber = Integer.valueOf(intent.getStringExtra("val2"));
        processingThread = new ProcessingThread(this, firstNumber, secondNumber);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
