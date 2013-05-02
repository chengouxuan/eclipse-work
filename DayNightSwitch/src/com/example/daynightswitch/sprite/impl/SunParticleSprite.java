
package com.example.daynightswitch.sprite.impl;

import com.example.daynightswitch.scene.IScene;
import com.example.daynightswitch.sprite.AbsSprite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * »ÆÉ«Á£×Ó
 *
 * <br>==========================
 * <br> author£ºZenip
 * <br> email£ºlxyczh@gmail.com
 * <br> create£º2013-4-26
 * <br>==========================
 */
public class SunParticleSprite extends AbsSprite {

    private static final String TAG = "SunParticleSprite";

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF mOvalRectF = new RectF();

    public SunParticleSprite(IScene scene) {
        this(scene, 20);
    }

    public SunParticleSprite(IScene scene, float randomSize) {
        super(scene);
        initialize(randomSize);
        setWidth((int)randomSize);
        setHeight((int)randomSize);
    }

    public void initialize(float randomSize) {
        mPaint.setColor(Color.YELLOW);
    }

    @Override
    public void draw(Canvas canvas) {
        mOvalRectF.set(0, 0, getWidth(), getHeight());
        canvas.drawOval(mOvalRectF, mPaint);
    }

    @Override
    public void onSceneSizeChanged(int w, int h, int oldw, int oldh) {
    }

}
