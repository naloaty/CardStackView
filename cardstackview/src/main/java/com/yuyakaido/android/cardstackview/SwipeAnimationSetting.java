package com.yuyakaido.android.cardstackview;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.FloatRange;

import com.yuyakaido.android.cardstackview.internal.AnimationSetting;

public class SwipeAnimationSetting implements AnimationSetting {

    private final Direction direction;
    private final int duration;
    private final float naturalSwipeZone;
    private final Interpolator interpolator;

    private SwipeAnimationSetting(
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
        private Direction direction = Direction.Right;
        private int duration = Duration.Normal.duration;
        private Interpolator interpolator = new AccelerateInterpolator();
        private float naturalSwipeZone = -1.0f;

        public Builder setDirection(Direction direction) {
            this.direction = direction;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setInterpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }


        public  Builder setNaturalSwipeZone(@FloatRange(from = -1.0f, to = 1.0f) float naturalSwipeZone) {
            if (naturalSwipeZone < -1.0f || 1.0f < naturalSwipeZone) {
                throw new IllegalArgumentException("Natural swipe zone bound must be -1.0f to 1.0f.");
            }
            this.naturalSwipeZone = naturalSwipeZone;
            return this;
        }

        public SwipeAnimationSetting build() {
            return new SwipeAnimationSetting(
                    direction,
                    duration,
                    interpolator,
                    naturalSwipeZone
            );
        }
    }

}
