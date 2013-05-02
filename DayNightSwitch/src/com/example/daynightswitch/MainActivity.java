
package com.example.daynightswitch;

import com.example.daynightswitch.director.Director;
import com.example.daynightswitch.scene.IScene;
import com.example.daynightswitch.scene.impl.DayScene;
import com.example.daynightswitch.scene.impl.NightScene;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "MainActivity";

    private View mBackground;

    private Button mBtnSwitch;

    private Director mDayNightDriector;

    private IScene mDayScene;

    private IScene mNightScene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBackground = findViewById(R.id.background);
        mBtnSwitch = (Button)findViewById(R.id.btn_switch);
        mBtnSwitch.setOnClickListener(this);

        ViewGroup vg = (ViewGroup)getWindow().getDecorView();

        // create a driector
        mDayNightDriector = new Director(this);
        mDayNightDriector.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));

        // set a scene into director
        mDayScene = new DayScene(mDayNightDriector);
        mNightScene = new NightScene(mDayNightDriector);

        vg.addView(mDayNightDriector);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        String btnText = new StringBuilder(mBtnSwitch.getText()).toString();
        if (btnText.indexOf("点击切换到日间模式") != -1) {
            mDayNightDriector.setCurScene(mDayScene);
            mDayNightDriector.action();
            mBtnSwitch.setText("点击切换到夜间模式");
        } else {
            mDayNightDriector.setCurScene(mNightScene);
            mDayNightDriector.action();
            mBtnSwitch.setText("点击切换到日间模式");
        }
    }
}
