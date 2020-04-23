package com.groxapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.groupon.grox.Store;
import com.groupon.grox.commands.rxjava2.Command;

import static com.groupon.grox.RxStores.states;

import io.reactivex.disposables.CompositeDisposable;

import static com.groupon.grox.RxStores.states;
import static com.jakewharton.rxbinding2.view.RxView.clicks;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class MainActivity extends AppCompatActivity {

    private final Store<State> store = new Store<>(State.empty());
    private final CompositeDisposable   compositeDisposable = new CompositeDisposable();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final View button = findViewById(R.id.button);

        compositeDisposable.add(states(store).observeOn(mainThread()).subscribe(this::updateUI, this::doLog));

        compositeDisposable.add(clicks(button)
            .map(click -> new RefreshColorCommand())
            .flatMap(Command::actions)
            .subscribe(store::dispatch, this::doLog));
    }

    private void updateUI(State newState) {
        final TextView label = findViewById(R.id.label);
        label.setBackgroundColor(getResources().getColor(android.R.color.background_light));
        label.setText("");
        if(newState.isRefreshing) {
            label.setText("↺");
        } else if(newState.error != null) {
            label.setText(newState.error);
        } else if(newState.color != State.INVALID_COLOR) {
            label.setBackgroundColor(newState.color);
        }
    }


    private void doLog(Throwable t) {
        Log.d("Grox", "An error has occurred", t);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
