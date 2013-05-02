
package com.example.daynightswitch.scene.impl;

import com.example.daynightswitch.animation.SimpleAnimationListener;
import com.example.daynightswitch.director.Director;
import com.example.daynightswitch.scene.AbsScene;
import com.example.daynightswitch.sprite.impl.SunMainSprite;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 日 <br>=
 * ========================= <br>
 * author：Zenip <br>
 * email：lxyczh@gmail.com <br>
 * create：2013-4-26 <br>=
 * =========================
 */
public class DayScene extends AbsScene {

    static final String TAG = "DayScene";

    private SunMainSprite mSunSprite;

    public DayScene(Director v) {
        super(v);

        init();
    }

    private void init() {
        mSunSprite = new SunMainSprite(this);
        this.addSprite(mSunSprite);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int left = (getWidth() - mSunSprite.getWidth()) / 2;
        int top = getHeight();

        mSunSprite.setLeft(left);
        mSunSprite.setTop(top);
        mSunSprite.setHide(true); // default hide
    }

    @Override
    public void onActionStart() {
        mSunSprite.setHide(false);

        final int transFromY = 0;
        final int transToY = -getHeight() / 2;

        // 放大縮小
        final Animation scaleAni = new ScaleAnimation(1, 100, 1, 100, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAni.setDuration(2000);
        scaleAni.setFillAfter(true);
        scaleAni.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                mSunSprite.setHide(true);
            }
        });

        // 位移动画
        TranslateAnimation transAni = new TranslateAnimation(Animation.ABSOLUTE, 1,
                Animation.ABSOLUTE, 1, Animation.ABSOLUTE, transFromY, Animation.ABSOLUTE, transToY);
        transAni.setDuration(2000);
        transAni.setFillAfter(true);
        transAni.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                System.out.println("onAnimationEnd");

                mSunSprite.setTop(getHeight() / 2);
                invalidate();

                // mSunSprite.setTop(240);
                mSunSprite.startAnimation(scaleAni);
            }
        });

        mSunSprite.startAnimation(transAni);
    }
}
