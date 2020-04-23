package com.groxapplication;

import com.groupon.grox.Action;

public class ErrorAction implements Action<State> {

    private String msg;

    public ErrorAction(Throwable error) {
        this.msg = error.getMessage();
    }

    @Override
    public State newState(State oldState) {
        return State.error(msg);
    }
}
