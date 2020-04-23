package com.groxapplication;

import com.groupon.grox.Action;
import com.groupon.grox.commands.rxjava2.Command;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static android.graphics.Color.rgb;
import static io.reactivex.Observable.error;
import static io.reactivex.Observable.just;
import static io.reactivex.schedulers.Schedulers.io;

public class RefreshColorCommand implements Command {

    private static final int SEED = 7;
    private static final int ERROR_RATE = 5;
    private static final int LATENCY_IN_MS = 1000;
    private static final Random random = new Random(SEED);
    private static final int MAX_COLOR = 256;
    private static final String ERROR_MSG = "Error. Please retry";


    @Override
    public Observable<? extends Action<State>> actions() {
        final Observable<Action<State>> refresh =  Observable.just(new RefreshAction());

        return refresh.concatWith(refreshColor()).onErrorReturn(ErrorAction::new);
    }

    private Observable<Action<State>> refreshColor() {
        return getColorFromServer().subscribeOn(io()).map(ChangeColorAction::new);

    }

    private Observable<Integer> getColorFromServer() {
        final Observable<Integer> result;
        if(random.nextInt() % ERROR_RATE == 0) {
            result = error(new RuntimeException(ERROR_MSG));
        } else {
            final int red = random.nextInt(MAX_COLOR);
            final int green = random.nextInt(MAX_COLOR);
            final int blue  = random.nextInt(MAX_COLOR);
            final int color = rgb(red, green, blue);
            result = just(color);
        }

        return result.delaySubscription(LATENCY_IN_MS, TimeUnit.MILLISECONDS);
    }
}
