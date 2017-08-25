package razon.nctbteachersguide.utils;

/**
 * Created by razon30 on 29-07-17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Leo on 2015/09/03
 */
public class MyBookListRecyclerView extends RecyclerView {
    private boolean mScrollable;

    public MyBookListRecyclerView(Context context) {
        this(context, null);
    }

    public MyBookListRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBookListRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScrollable = false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return !mScrollable || super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            animate(getChildAt(i), i);

            if (i == getChildCount() - 1) {
                getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScrollable = true;
                    }
                }, i * 300);
            }
        }
    }

    private void animate(View view, final int pos) {
        view.animate().cancel();
        view.setTranslationX(500);
        view.setAlpha(0);
        view.animate().alpha(1.0f).translationX(0).setDuration(500).setStartDelay(pos * 100);
    }
}
