package com.nakharin.wongfah.manager.bus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by nuuneoi on 12/6/14 AD.
 */
public class BusProvider extends Bus {

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private static BusProvider instance;

    public static BusProvider getInstance() {
        if (instance == null)
            instance = new BusProvider();
        return instance;
    }

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    BusProvider.super.post(event);
                }
            });
        }
    }

}
