package org.tikal.bus;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;

import com.squareup.otto.Bus;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventGeneratorService extends Service {

    private Executor executor = Executors.newSingleThreadExecutor();
    private AtomicBoolean stopThread = new AtomicBoolean(false);
    private Bus serviceBus;
    private Bus uiBus;
    public EventGeneratorService() {
        uiBus = BusProvider.getUiBusInstance();
        serviceBus = BusProvider.getServiceBusInstance();
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceBus.register(this);
        executor.execute(new SampleThread());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceBus.unregister(this);
        stopThread.set(true);
    }

    private static final String[] messages = {"Red","Green","Blue","Yellow"};

    private class SampleThread extends Thread{
        @Override
        public void run() {
            while(true){
                    for (int i = 0 ; i < 3 ; i++) {
                        for (int j = 0 ; j < messages.length; j++){
                        if (stopThread.get()) {
                            return;
                        }
                        SystemClock.sleep(1000);
                        uiBus.post(new LabelMessage(i,messages[j]));
                    }
                }

            }


        }
    }
}
