
package com.example.daynightswitch.director;

import com.example.daynightswitch.scene.IScene;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * usage¡£ <br>=
 * ========================= <br>
 * author£ºZenip <br>
 * email£ºlxyczh@gmail.com <br>
 * create£º2013-4-26 <br>=
 * =========================
 */

public class Director extends View {
    static final String TAG = "DaynightDriector";

    /**
     * µ±Ç°³¡¾°
     */
    private IScene mCurScene;

    private String mFpsString;

    private long mFpsStartTime = 0;

    private long mFpsEndTime = 0;
    
    private Paint mRedPaint = new Paint();

    public Director(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Director(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Director(Context context) {
        super(context);
        init();
    }

    private void init() {
        // disable gpu
        ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, null);

        // set red color
        mRedPaint.setColor(Color.RED);
    }

    public void setCurScene(IScene scene) {
        if (mCurScene != scene && getHeight() != 0) {
            scene.onSizeChanged(getWidth(), getHeight(), getWidth(), getHeight());
        }
        mCurScene = scene;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mFpsStartTime = System.currentTimeMillis();

        // dispatch draw
        if (mCurScene != null) {
            mCurScene.draw(canvas);
        }
        // caculate and show fps
        mFpsEndTime = System.currentTimeMillis();
        long timeSpan = mFpsEndTime - mFpsStartTime;
        double fps = 1000f / (timeSpan != 0 ? timeSpan : 1);
        mFpsString = "Fps:" + fps;
        canvas.drawText(mFpsString, 0, 100, mRedPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mCurScene != null) {
            mCurScene.onSizeChanged(w, h, oldw, oldh);
        }
    }

    public void action() {
        if (mCurScene != null) {
            mCurScene.onActionStart();
        }
    }

}
