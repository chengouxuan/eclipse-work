
package com.example.daynightswitch.sprite.impl;

import com.example.daynightswitch.R;
import com.example.daynightswitch.animation.SimpleAnimationListener;
import com.example.daynightswitch.scene.IScene;
import com.example.daynightswitch.sprite.AbsSprite;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
import android.graphics.RectF;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class SunMainSprite extends AbsSprite {

    private static final String TAG = "SunMainSprite";

    /**
     * ��ɫ����
     */
    private Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * ��ѡPath �� �ڹ��캯��������FillType.INVERSE_WINDING
     */
    private Path mFillReversePath = new Path();

    /**
     * ��Ⱦ���ӵ������Χ
     */
    private float mRandomDistanceRange = 100;

    /**
     * left �� top ��0�ľ��Ρ�
     */
    private RectF mRectFRelativeToSelf = new RectF();

    /**
     * �����ϴ���Ⱦʱ�䣬 ���ڼ��㡰��Ⱦʱ�����������ü�����ơ����ӵĳ����ܶȡ�
     */
    private long mLastRenderParticleTime = 0;

    public SunMainSprite(IScene scene) {
        super(scene);
        mBackgroundPaint.setColor(getResources().getColor(R.color.simple_grep));
        mFillReversePath.setFillType(FillType.INVERSE_WINDING);
    }

    @Override
    public void draw(Canvas canvas) {
        mRectFRelativeToSelf.set(0, 0, getWidth(), getHeight());
        mFillReversePath.reset();
        mFillReversePath.addOval(mRectFRelativeToSelf, Direction.CW);
        canvas.drawPath(mFillReversePath, mBackgroundPaint);
    }

    @Override
    public void afterConcatAnimationMatrix(Matrix matrix) {
        super.afterConcatAnimationMatrix(matrix);
        float[] points = {
                getLeft(), getTop()
        };

        float[] vals = {
                0, 0, 0, 0, 0, 0, 0, 0, 0
        };

        matrix.getValues(vals);

        // ifλ�ƶ���
        if (vals[0] == 1f && vals[1] == 0f && vals[2] != 0f && vals[3] == 0f && vals[4] == 1f
                && vals[5] != 0f) {
            matrix.mapPoints(points);
            renderParticle(points);
        }

    }

    /**
     * ���ݵ�ǰ���ˡ���λ�á�����������Ӷ���
     * 
     * @param points �˵�λ��
     */
    private void renderParticle(float[] points) {
        final Animation animation = getAnimation();
        if (animation == null) {
            return;
        }

        long timeSpan = System.currentTimeMillis() - mLastRenderParticleTime;

        if (timeSpan > 150) {
            // ��ʣ��Ķ������
            long leftDuration = getAnimation().getDuration()
                    - (System.currentTimeMillis() - animation.getStartTime());
            if (leftDuration <= 0) {
                return;
            }

            // create a sprite
            final float y = points[1];
            final float x = points[0];
            final SunParticleSprite sprite = new SunParticleSprite(getScene(), getRandomSize());
            sprite.setLeft((int)x);
            sprite.setTop((int)y);
            getScene().addSprite(sprite);

            // create translate animation for sprite
            TranslateAnimation transAni = new TranslateAnimation(Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, getRandomDistance(mRandomDistanceRange / 2, false),
                    Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -getRandomDistance(
                            mRandomDistanceRange, true));
            transAni.setFillAfter(true);
            transAni.setDuration(leftDuration);
            transAni.setAnimationListener(new SimpleAnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    sprite.setHide(true);
                    // do remove in next UI thread frame . so remove never
                    // disturb animation loop
                    postToUIThread(new Runnable() {
                        @Override
                        public void run() {
                            getScene().removeSprite(sprite);
                        }
                    });
                }
            });

            // start animation
            sprite.startAnimation(transAni);

            // mark last render Particle Time
            mLastRenderParticleTime = System.currentTimeMillis();
        }
    }

    @Override
    public void startAnimation(Animation ani) {
        super.startAnimation(ani);
        mLastRenderParticleTime = ani.getStartTime();

    }

    @Override
    public void onSceneSizeChanged(int w, int h, int oldw, int oldh) {
    }

    /**
     * ��ȡ���������С
     * 
     * @return
     */
    private float getRandomSize() {
        float ret = getWidth() / 2 + (float)(Math.random() * (getWidth() / 2));
        return ret;
    }

    /**
     * ��ȡ�����Χ��֧��������
     * 
     * @param range �����Χ
     * @param forcePlus �Ƿ�ǿ������
     * @return
     */
    private float getRandomDistance(float range, boolean forcePlus) {
        // �����븺�����
        int plusMinus = -1;
        if (forcePlus) {
            plusMinus = 1;
        } else if (Double.compare(Math.random(), 0.5) >= 0) {
            plusMinus = 1;
        }
        // �������
        float ret = plusMinus * (float)(Math.random() * range);
        return ret;
    }

}
