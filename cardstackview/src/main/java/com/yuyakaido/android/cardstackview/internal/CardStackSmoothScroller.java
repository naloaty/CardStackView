package com.yuyakaido.android.cardstackview.internal;

import android.view.View;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardStackSmoothScroller extends RecyclerView.SmoothScroller {

    public enum ScrollType {
        AutomaticSwipe,
        AutomaticRewind,
        ManualSwipe,
        ManualCancel
    }

    private ScrollType type;
    private CardStackLayoutManager manager;

    public CardStackSmoothScroller(
            ScrollType type,
            CardStackLayoutManager manager
    ) {
        this.type = type;
        this.manager = manager;
    }

    @Override
    protected void onSeekTargetStep(
            int dx,
            int dy,
            @NonNull RecyclerView.State state,
            @NonNull Action action
    ) {
        if (type == ScrollType.AutomaticRewind) {
            CardStackSetting stackSetting = manager.getCardStackSetting();
            RewindAnimationSetting animSetting = stackSetting.rewindAnimationSetting;
            action.update(
                    -getDx(animSetting, stackSetting),
                    -getDy(animSetting, stackSetting),
                    animSetting.getDuration(),
                    animSetting.getInterpolator()
            );
        }
    }

    @Override
    protected void onTargetFound(
            @NonNull View targetView,
            @NonNull RecyclerView.State state,
            @NonNull Action action
    ) {
        int x = (int) targetView.getTranslationX();
        int y = (int) targetView.getTranslationY();
        AnimationSetting animSetting;
        CardStackSetting stackSetting;
        switch (type) {
            case AutomaticSwipe:
                stackSetting = manager.getCardStackSetting();
                animSetting = stackSetting.swipeAnimationSetting;
                action.update(
                        -getDx(animSetting, stackSetting),
                        -getDy(animSetting, stackSetting),
                        animSetting.getDuration(),
                        animSetting.getInterpolator()
                );
                break;
            case AutomaticRewind:
                animSetting = manager.getCardStackSetting().rewindAnimationSetting;
                action.update(
                        x,
                        y,
                        animSetting.getDuration(),
                        animSetting.getInterpolator()
                );
                break;
            case ManualSwipe:
                int dx = -x * 10;
                int dy = -y * 10;
                animSetting = manager.getCardStackSetting().swipeAnimationSetting;
                action.update(
                        dx,
                        dy,
                        animSetting.getDuration(),
                        animSetting.getInterpolator()
                );
                break;
            case ManualCancel:
                animSetting = manager.getCardStackSetting().rewindAnimationSetting;
                action.update(
                        x,
                        y,
                        animSetting.getDuration(),
                        animSetting.getInterpolator()
                );
                break;
        }
    }

    @Override
    protected void onStart() {
        CardStackListener listener = manager.getCardStackListener();
        CardStackState state = manager.getCardStackState();
        switch (type) {
            case AutomaticSwipe:
                state.next(CardStackState.Status.AutomaticSwipeAnimating);
                listener.onCardDisappeared(manager.getTopView(), manager.getTopPosition());
                break;
            case AutomaticRewind:
                state.next(CardStackState.Status.RewindAnimating);
                break;
            case ManualSwipe:
                state.next(CardStackState.Status.ManualSwipeAnimating);
                listener.onCardDisappeared(manager.getTopView(), manager.getTopPosition());
                break;
            case ManualCancel:
                state.next(CardStackState.Status.RewindAnimating);
                break;
        }
    }

    @Override
    protected void onStop() {
        CardStackListener listener = manager.getCardStackListener();
        switch (type) {
            case AutomaticSwipe:
                // Notify callback from CardStackLayoutManager
                break;
            case AutomaticRewind:
                listener.onCardRewound();
                listener.onCardAppeared(manager.getTopView(), manager.getTopPosition());
                break;
            case ManualSwipe:
                // Notify callback from CardStackLayoutManager
                break;
            case ManualCancel:
                listener.onCardCanceled();
                break;
        }
    }

    private int getDx(AnimationSetting animSetting, CardStackSetting stackSetting) {
        CardStackState state = manager.getCardStackState();
        int dx = 0;
        switch (animSetting.getDirection()) {
            case Left:
                dx = -state.width * 2;
                break;
            case Right:
                dx = state.width * 2;
                break;
            case Top:
                if (stackSetting.imitateNaturalSwipe)
                    dx = state.width;
                break;
            case Bottom:
                if (stackSetting.imitateNaturalSwipe)
                    dx = -state.width;
                break;
        }
        return dx;
    }

    private int getDy(AnimationSetting setting, CardStackSetting stackSetting) {
        CardStackState state = manager.getCardStackState();
        int dy = 0;
        switch (setting.getDirection()) {
            case Left:
            case Right:
                if (!stackSetting.imitateNaturalSwipe)
                    dy = state.height / 4;
                break;
            case Top:
                dy = -state.height * 2;
                break;
            case Bottom:
                dy = state.height * 2;
                break;
        }
        return dy;
    }

}
