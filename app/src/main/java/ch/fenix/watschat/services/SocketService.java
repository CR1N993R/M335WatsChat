package ch.fenix.watschat.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import ch.fenix.watschat.threads.SocketThread;

public class SocketService extends Service {
    private SocketThread socketThread;
    public final IBinder binder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        socketThread = new SocketThread(getApplicationContext());
        socketThread.start();
        return START_STICKY;
    }

    public class LocalBinder extends Binder {
        public SocketService getService() {
            return SocketService.this;
        }
    }

    public void sendMessage(String message) {
        new Thread(() -> {
            socketThread.sendMessage(message);
        }).start();
    }
}