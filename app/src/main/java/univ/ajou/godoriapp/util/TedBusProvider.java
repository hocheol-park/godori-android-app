package univ.ajou.godoriapp.util;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

public final class TedBusProvider extends Bus {

    private static TedBusProvider instance;

    public static TedBusProvider getInstance() {

        if(instance==null)
            instance = new TedBusProvider();

        return instance;
    }


    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {


        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    TedBusProvider.getInstance().post(event);
                }
            });
        }


    }
}
