
package com.example.daynightswitch.scene;

import com.example.daynightswitch.director.Director;
import com.example.daynightswitch.sprite.AbsSprite;

import android.content.Context;
import android.graphics.Canvas;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

import java.util.ArrayList;

/**
 * a scene may contain may sprites <br>=
 * ========================= <br>
 * author£ºZenip <br>
 * email£ºlxyczh@gmail.com <br>
 * create£º2013-4-23 <br>=
 * =========================
 */

public abstract class AbsScene implements IScene {

    static final String TAG = "DayScene";

    private ArrayList<AbsSprite> mSpriteList = new ArrayList<AbsSprite>();

    private long mStartTime = 0;

    private long mEndTime = 0;

    private Director mDirector = null;

    private Animation mCurAnimation;

    public AbsScene(Director driector) {
        mDirector = driector;
    }

    public void addSprite(AbsSprite cell) {
        mSpriteList.add(cell);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        for (AbsSprite cell : mSpriteList) {
            cell.onSceneSizeChanged(w, h, oldw, oldh);
        }
    }

    public void removeSprite(AbsSprite sprite) {
        boolean isRemoveSuccess = mSpriteList.remove(sprite);
        if (isRemoveSuccess) {
            invalidate();
        }
    }

    public void draw(Canvas canvas) {
        mStartTime = System.currentTimeMillis();

        for (int i = 0, len = mSpriteList.size(); i < len; i++) {
            if (mSpriteList.size() > i) {
                AbsSprite sprite = mSpriteList.get(i);
                if ((sprite != null) && (!sprite.isHide())) {
                    sprite.drawWithAnimation(canvas);
                }
            }
        }

        Animation a = null;
        boolean hasAnimation = false;
        for (int i = 0, len = mSpriteList.size(); i < len; i++) {
            if (mSpriteList.size() > i) {
                AbsSprite s = mSpriteList.get(i);
                if ((s != null) && (!s.isHide())) {
                    a = s.getAnimation();
                    hasAnimation = hasAnimation || s.hasAnimation();
                    if (s.getAnimation() != null && !a.getFillAfter()) {
                        s.clearAnimation();
                    }
                }
            }
        }

        if (hasAnimation) {
            mDirector.invalidate();
        }

        mEndTime = System.currentTimeMillis();

        long timeSpan = mEndTime - mStartTime;
        double fps = 1000 / (timeSpan != 0 ? timeSpan : 1);
        System.out.println("FPS:" + fps);
    }

    @Override
    public int getHeight() {
        return mDirector.getHeight();
    }

    @Override
    public int getWidth() {
        return mDirector.getWidth();
    }

    @Override
    public Context getContext() {
        return mDirector.getContext();
    }

    @Override
    public void invalidate() {
        mDirector.invalidate();
    }

    @Override
    public void postToUIThread(Runnable runable) {
        mDirector.post(runable);
    }

    public Animation setAnimation() {
        return mCurAnimation;
    }

    public Animation getAnimation() {
        return mCurAnimation;
    }

}
