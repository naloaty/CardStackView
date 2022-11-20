package com.yuyakaido.android.cardstackview;

public enum Duration {
    Fast(100),
    Normal(200),
    Slow(500);

    public final int duration;

    Duration(int duration) {
        this.duration = duration;
    }
}
