
package com.example.daynightswitch.scene.impl;

import com.example.daynightswitch.animation.SimpleAnimationListener;
import com.example.daynightswitch.director.Director;
import com.example.daynightswitch.scene.AbsScene;
import com.example.daynightswitch.sprite.impl.SunMainSprite;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class NightScene extends AbsScene {

    static final String TAG = "NightScene";

    private SunMainSprite mSunSprite;

    public NightScene(Director v) {
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

        // 设置精灵位置
        mSunSprite.setLeft(left);
        mSunSprite.setTop(getHeight() / 2);
        mSunSprite.setHide(true);
    }

    @Override
    public void onActionStart() {
        mSunSprite.setHide(false);

        int XT_ABS = Animation.ABSOLUTE;
        int XT_RTP = Animation.RELATIVE_TO_PARENT;

        AnimationSet set = new AnimationSet(false);
        final int transToY = getHeight() / 2;
        final int transFromY = 0;

        // 位移动画
        final TranslateAnimation transAni = new TranslateAnimation(XT_ABS, 1, XT_ABS, 1, XT_ABS,
                transFromY, XT_ABS, transToY);
        transAni.setDuration(2000);
        transAni.setFillAfter(true);
        transAni.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                System.out.println("onAnimationEnd");
                mSunSprite.setHide(true);
            }
        });

        // 放大縮小
        final Animation scaleAni = new ScaleAnimation(100, 1, 100, 1, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAni.setDuration(2000);
        scaleAni.setFillAfter(true);
        scaleAni.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                mSunSprite.startAnimation(transAni);
            }
        });

        mSunSprite.startAnimation(scaleAni);
    }

}
