package com.keelim.free.ui.custome;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Keep;

public class Titanic {

    private AnimatorSet animatorSet;
    private Animator.AnimatorListener animatorListener;

    public Animator.AnimatorListener getAnimatorListener() {
        return animatorListener;
    }

    public void setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.animatorListener = animatorListener;
    }

    public void start(final TitanicTextView textView) {

        final Runnable animate = () -> {
            textView.setSinking(true);
            // horizontal animation. 200 = wave.png width
            ObjectAnimator maskXAnimator = ObjectAnimator.ofFloat(textView, "maskX", 0, 200);
            maskXAnimator.setRepeatCount(ValueAnimator.INFINITE);
            maskXAnimator.setDuration(3000);
            maskXAnimator.setStartDelay(0);

            int h = textView.getHeight();
            // vertical animation
            // maskY = 0 -> wave vertically centered
            // repeat mode REVERSE to go back and forth
            ObjectAnimator maskYAnimator = ObjectAnimator.ofFloat(textView, "maskY", h/2, - h/2);
            maskYAnimator.setRepeatCount(ValueAnimator.INFINITE);
            maskYAnimator.setRepeatMode(ValueAnimator.REVERSE);
            maskYAnimator.setDuration(500);
            maskYAnimator.setStartDelay(0);

            // now play both animations together
            animatorSet = new AnimatorSet();
            animatorSet.playTogether(maskXAnimator, maskYAnimator);
            animatorSet.setInterpolator(new LinearInterpolator());
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    textView.setSinking(false);
                    textView.postInvalidateOnAnimation();
                    animatorSet = null;
                }

                @Override
                public void onAnimationCancel(Animator animation) {}

                @Override
                public void onAnimationRepeat(Animator animation) {}
            });

            if (animatorListener != null) {
                animatorSet.addListener(animatorListener);
            }
            animatorSet.start();
        };

        if (!textView.isSetUp()) {
            textView.setAnimationSetupCallback(target -> animate.run());
        } else {
            animate.run();
        }
    }

    public void cancel() {
        if (animatorSet != null) {
            animatorSet.cancel();
        }
    }
}