package zth.com.gezhi.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by Kotle on 2017/10/13.
 */


public class KtPopupWindow extends PopupWindow {
    private OnTopTitleListener onTopTitleListener;
    private View contentView;
    private final int duration = 600;

    public KtPopupWindow(View view, Context context, int width, int height) {
        this(view, context, width, height, true);
    }

    public KtPopupWindow(View view, Context context, int width, int height, boolean setFocusable) {
        super();
        contentView = view;
        setContentView(view);
        setWidth(width);
        setHeight(height);
        // 去掉默认的动画效果(showAsDropDown可能会自带默认动画)
//            setAnimationStyle(R.style.PopupAnimation);
        if (setFocusable) {
            // 下面两行是为了让PopupWindow能够相应返回按键
            setFocusable(true);
//            setOutsideTouchable(false);
        }
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public KtPopupWindow(View showView) {
        this(showView, showView.getContext(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        postAnimateIn(anchor);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        postAnimateIn(anchor);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        postAnimateIn(parent);
    }

    private void postAnimateIn(View postView) {
        if (onTopTitleListener != null) {
            onTopTitleListener.onShow();
        }
        postView.postDelayed(new Runnable() {
            @Override
            public void run() {
                animateIn();
            }
        }, 1);
    }

    private void animateIn() {
        int height = contentView.getHeight();
        contentView.setTranslationY(-height - 10);
        contentView.animate().translationY(0).setDuration(duration)
                .setListener(null).start();

    }

    /**
     * 直接关闭PopupWindow，没有动画效果
     */
    public void superDismiss() {
        super.dismiss();
        if (onTopTitleListener != null) {
            onTopTitleListener.onDismiss();
        }
    }

    @Override
    public void dismiss() {
        animateOut(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                superDismiss();
            }
        });
        if (onTopTitleListener != null) {
            onTopTitleListener.onStartDismiss();
        }
    }

    private void animateOut(final Animator.AnimatorListener listener) {
        int height = contentView.getHeight();
        contentView.animate().translationY(-height).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onAnimationEnd(animation);
                contentView.animate().setListener(null);
            }
        }).setDuration(duration).start();

    }

    public static abstract class OnTopTitleListener {

        /**
         * 开始消失，这个时候PopupWindow还在，只是在执行消失动画
         */
        public void onStartDismiss() {
        }

        ;

        /**
         * 完全消失
         */
        public void onDismiss() {
        }

        /**
         * item 点击事件
         */
        public abstract void onItemClickListener(String text, int position);

        /**
         * onshow
         */
        public void onShow() {
        }
    }
}



