package org.tikal.bus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Oren Kleks on 1/1/2015.
 */
public class BusProvider {
    private static final Bus uiBus = new Bus(ThreadEnforcer.MAIN);
    private static final Bus serviceBus = new Bus(ThreadEnforcer.ANY);
    public static Bus getUiBusInstance() {
        return uiBus;
    }

    public static Bus getServiceBusInstance() {
        return serviceBus;
    }

    private BusProvider() {
// No instances.
    }
}
