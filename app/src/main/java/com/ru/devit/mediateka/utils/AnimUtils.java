package com.ru.devit.mediateka.utils;

import android.animation.Animator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;


public class AnimUtils {
    public static void startRevealAnimation(View view){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            view.post(() -> {
                view.setVisibility(View.INVISIBLE);
                int cx = view.getWidth() / 2;
                int cy = view.getHeight();

                float endRadius = (float) Math.hypot(cx , cy);
                Animator animator = ViewAnimationUtils.createCircularReveal(view , cx , cy , 0 , endRadius);
                view.setVisibility(View.VISIBLE);
                animator.start();
            });
        }
    }

    public static void startRevealAnimationWithOutVisibility(View view){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            int cx = view.getWidth();
            int cy = view.getHeight();

            float endRadius = (float) Math.hypot(cx , cy);
            Animator animator = ViewAnimationUtils.createCircularReveal(view , cx , cy , 0 , endRadius);
            animator.setInterpolator(new FastOutSlowInInterpolator());
            animator.start();
        }
    }
}
