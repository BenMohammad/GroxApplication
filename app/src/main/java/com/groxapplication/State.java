package com.groxapplication;

public class State {

    public static final int INVALID_COLOR = -1;

    public final int color;
    public final String error;
    public final boolean isRefreshing;

    public State(int color, String error, boolean isRefreshing) {
        this.color = color;
        this.error = error;
        this.isRefreshing = isRefreshing;
    }

    public static State empty() {
        return new State(INVALID_COLOR, null, false);
    }

    public static State refreshing() {
        return new State(INVALID_COLOR, null, true);
    }

    public static State success(int color) {
        return new State(color, null, false);
    }

    public static State error(String error) {
        return new State(INVALID_COLOR, error, false);
    }


}
