
package com.example.daynightswitch.scene;

import com.example.daynightswitch.sprite.AbsSprite;

import android.content.Context;
import android.graphics.Canvas;

public interface IScene {

    void onSizeChanged(int w, int h, int oldw, int oldh);

    void draw(Canvas canvas);

    int getHeight();

    int getWidth();

    Context getContext();

    void invalidate();

    void postToUIThread(Runnable runable);

    void addSprite(AbsSprite sprite);

    void removeSprite(AbsSprite sprite);

    /**
     * 开始拍电影 
     */
    void onActionStart();

}
