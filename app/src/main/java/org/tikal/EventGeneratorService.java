package org.tikal;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.tikal.bus.BusProvider;
import org.tikal.bus.Command;
import org.tikal.bus.LabelMessage;
import org.tikal.bus.StartCommand;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventGeneratorService extends Service {

    private Executor executor = Executors.newSingleThreadExecutor();
    private AtomicBoolean stopThread = new AtomicBoolean(false);
    private Bus serviceBus;
    private Bus uiBus;

    public EventGeneratorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        uiBus = BusProvider.getUiBusInstance();
        serviceBus = BusProvider.getServiceBusInstance();
        handler = new Handler(Looper.getMainLooper());
        Log.d(getClass().getSimpleName(), "start service");
        serviceBus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getSimpleName(), "destroy service");
        serviceBus.unregister(this);
    }

    public void startSampleExecution() {
        stopThread.set(false);
        Log.d(getClass().getSimpleName(), "startSampleExecution");

        executor.execute(new SampleThread());
    }

    public void stopSampleExecution() {
        stopThread.set(true);
    }

    private static final String[] messages = {"Bla Bla", "Text", "Word", "Demo"};

    @Subscribe
    public void commandLogger(Command command) {
        Log.d(getClass().getSimpleName(), "Command Received:" + command);

    }

    @Subscribe
    public void startReceived(StartCommand command) {
        Log.d(getClass().getSimpleName(), "Start Command Received:" + command);
        startSampleExecution();

    }

    @Subscribe
    public void stopReceived(Command command) {
        Log.d(getClass().getSimpleName(), "Stop Command Received:" + command);
        stopSampleExecution();

    }

    private class SampleThread extends Thread {
        @Override
        public void run() {
            while (true) {
                for (int j = 0; j < messages.length; j++) {
                    for (int i = 0; i < 3; i++) {

                        if (stopThread.get()) {
                            return;
                        }
                        SystemClock.sleep(1000);
                        postMessage(i, j);
                    }
                }

            }


        }
    }
    private Handler handler;
    private void postMessage(final int i, final int j) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                uiBus.post(new LabelMessage(i, messages[j]));
            }
        });


    }
}
