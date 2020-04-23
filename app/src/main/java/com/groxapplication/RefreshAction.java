package com.groxapplication;

import com.groupon.grox.Action;

import java.util.Stack;

public class RefreshAction implements Action<State> {

    @Override
    public State newState(State oldState) {
        return State.refreshing();
    }
}
