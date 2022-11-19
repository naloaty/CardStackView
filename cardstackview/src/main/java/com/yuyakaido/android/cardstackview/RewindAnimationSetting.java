package com.yuyakaido.android.cardstackview;

import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.yuyakaido.android.cardstackview.internal.AnimationSetting;

public class RewindAnimationSetting implements AnimationSetting {

    private final Direction direction;
    private final int duration;
    private final Interpolator interpolator;
    private final float naturalSwipeZone;

    private RewindAnimationSetting(
            Direction direction,
            int duration,
            Interpolator interpolator,
            float naturalSwipeZone
    ) {
        this.direction = direction;
        this.duration = duration;
        this.interpolator = interpolator;
        this.naturalSwipeZone = naturalSwipeZone;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public Interpolator getInterpolator() {
        return interpolator;
    }

    @Override
    public float getNaturalSwipeZone() {
        return naturalSwipeZone;
    }

    public static class Builder {
        private Direction direction = Direction.Bottom;
        private int duration = Duration.Normal.duration;
        private Interpolator interpolator = new DecelerateInterpolator();
        private float naturalSwipeZone = 0.0f;

        public RewindAnimationSetting.Builder setDirection(Direction direction) {
            this.direction = direction;
            return this;
        }

        public RewindAnimationSetting.Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public RewindAnimationSetting.Builder setInterpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public RewindAnimationSetting build() {
            return new RewindAnimationSetting(
                    direction,
                    duration,
                    interpolator,
                    naturalSwipeZone
            );
        }
    }

}
