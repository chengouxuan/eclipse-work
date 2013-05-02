
package com.example.daynightswitch.sprite;

import com.example.daynightswitch.scene.IScene;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public abstract class AbsSprite {

    private IScene mScene;

    private Animation mCurAnimation = null;

    private Matrix mMatrix = new Matrix();

    private int mWidth = 20;

    private int mHeight = 20;

    private int mLeft;

    private int mTop;

    private boolean mIsHide = false;

    public boolean isHide() {
        return mIsHide;
    }

    public void setHide(boolean b) {
        mIsHide = b;
        invalidate();
    }

    public AbsSprite(IScene screen) {
        mScene = screen;
    }

    public Matrix getMatrix() {
        return mMatrix;
    }

    public IScene getScene() {
        return mScene;
    }

    public int getSceneHeight() {
        return mScene.getHeight();
    }

    public int getSceneWidth() {
        return mScene.getWidth();
    }

    protected Resources getResources() {
        return mScene.getContext().getResources();
    }

    protected Context getContext() {
        return mScene.getContext();
    }

    protected void invalidate() {
        mScene.invalidate();
    }

    protected void postToUIThread(Runnable runable) {
        mScene.postToUIThread(runable);
    }

    public Animation getAnimation() {
        return mCurAnimation;
    }

    public boolean hasAnimation() {
        System.out.println("mCurAnimation = " + mCurAnimation);
        if (mCurAnimation == null) {
            return false;
        }
        return mCurAnimation != null && (!mCurAnimation.hasEnded());
    }

    public void setAnimation(Animation a) {
        mCurAnimation = a;
    }

    public void startAnimation(Animation ani) {
        setAnimation(ani);
        if (!ani.isInitialized()) {
            ani.initialize(getWidth(), getHeight(), getSceneWidth(), getSceneHeight());
        }
        ani.setStartTime(System.currentTimeMillis());
        invalidate();
    }

    public void setLeft(int left) {
        mLeft = left;
    }

    public void setTop(int top) {
        mTop = top;
    }

    public int getLeft() {
        return mLeft;
    }

    public int getTop() {
        return mTop;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    private Transformation mTransformation = new Transformation();

    public void drawWithAnimation(Canvas canvas) {
        canvas.save();
        
        Animation ani = this.getAnimation();
        Matrix matrix = null;
        // 移动原点坐标到 （left， top）
        canvas.translate(getLeft(), getTop());
        if (ani != null) {
            boolean more = ani.getTransformation(System.currentTimeMillis(), mTransformation);
            if (more) {
                matrix = mTransformation.getMatrix();
                System.out.println(", mTransformation.getMatrix() = " + matrix.toShortString());
                canvas.concat(matrix);
                afterConcatAnimationMatrix(matrix);
            }
        }
        draw(canvas);
        
        canvas.restore();
    }

    public void afterConcatAnimationMatrix(Matrix matrix) {
    }

    public abstract void draw(Canvas canvas);

    /**
     * 场景大小改变
     * 
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    public abstract void onSceneSizeChanged(int w, int h, int oldw, int oldh);

    public void clearAnimation() {
        mCurAnimation = null;
        invalidate();
    }
}
