package com.groxapplication;

import com.groupon.grox.Action;

public class ChangeColorAction implements Action<State> {

    private final int color;

    public ChangeColorAction(int color) {
        this.color = color;
    }

    @Override
    public State newState(State oldState) {
        return State.success(color);
    }
}
