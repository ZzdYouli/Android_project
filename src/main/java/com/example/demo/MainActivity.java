package com.example.demo;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

public class MainActivity extends Activity {

    private static final String TAG = "AnimationDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 用你自己的布局替换这个视图
        View view = new View(this);
        setContentView(view);

        // 设置动画
        AnimationSet animationSet = new AnimationSet(true);

        // 缩放动画：以中心缩放 1.5 倍
        ScaleAnimation scale = new ScaleAnimation(
                1.0f, 1.5f, 1.0f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scale.setDuration(2000);

        // 旋转动画：逆时针 720 度
        RotateAnimation rotate = new RotateAnimation(
                0, -720,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(2000);

        // 透明度动画：不透明到透明度 0.8
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.8f);
        alpha.setDuration(2000);

        // 加入动画集合
        animationSet.addAnimation(scale);
        animationSet.addAnimation(rotate);
        animationSet.addAnimation(alpha);

        // 设置重复次数为3次（即动画播放4次：初次+重复3次）
        animationSet.setRepeatCount(3);

        // 设置监听器
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            int repeatCount = 0;

            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG, "animation start");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                repeatCount++;
                Log.d(TAG, "animation repeat: " + repeatCount);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "animation end");
            }
        });

        // 开始动画
        view.startAnimation(animationSet);
    }
}
