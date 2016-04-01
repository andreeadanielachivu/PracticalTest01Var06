package ro.pub.cs.systems.eim.practicaltest01var06;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Var06Service extends Service {

    private ProcessingThread processingThread = null;

    public PracticalTest01Var06Service() {
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        String adresa = intent.getStringExtra("internetAdd");
        processingThread = new ProcessingThread(this, adresa);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    public void onDestroy() {
        processingThread.stopThread();
    }
}
