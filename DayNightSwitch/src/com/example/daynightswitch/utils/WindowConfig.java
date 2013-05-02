
package com.example.daynightswitch.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * helper for fetching window config
 */
public class WindowConfig {

    public int screenWidth = 0;

    public int screenHeight = 0;

    public int screenCenterX = 0;

    public int screenCenterY = 0;

    public WindowConfig(Context context) {
        initialize(context);
    }

    private void initialize(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        screenWidth = outMetrics.widthPixels;
        screenHeight = outMetrics.heightPixels;

        screenCenterX = screenWidth / 2;
        screenCenterY = screenHeight / 2;
    }

    /**
     * ��ȡ��Ļ���
     * 
     * @return
     */
    public float getScreenWidth() {
        return screenWidth;
    }

    /**
     * ��ȡ��Ļ�߶�
     * 
     * @return
     */
    public float getScreenHeight() {
        return screenHeight;
    }

    /**
     * ��ȡ��Ļ�м����
     * 
     * @param size
     */
    public Rect getCenterRect(int size) {
        Rect r = new Rect();
        int left = screenCenterX - size / 2;
        int top = screenCenterY - size / 2;
        r.set(left, top, left + size, top + size);
        return r;
    }

    /**
     * ��ȡ��Ļ�м����
     * 
     * @param size
     */
    public void getCenterRect(RectF outRectF, int size) {
        outRectF.left = screenCenterX - size / 2;
        outRectF.top = screenCenterY - size / 2;
        outRectF.right = outRectF.left + size;
        outRectF.bottom = outRectF.top + size;
    }

    /**
     * ��ȡ��Ļ�ײ��м����
     * 
     * @param size
     */
    public Rect getCenterBottomRect(int size) {
        Rect r = new Rect();
        int left = screenCenterX - size / 2;
        int top = screenHeight - size; // NOTE!!! ���β��ܳ�����Ļ�߶ȣ�Ҫ��Ȼʹ�ø�rect��view�ᱻ��С
        r.set(left, top, left + size, top + size);
        return r;
    }

}
