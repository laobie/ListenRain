package com.jaeger.listenrain.widget;

import android.app.ActionBar;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jaeger.listenrain.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Jaeger on 15/9/30.
 * ListenRain
 */
public class CustomRefreshLayout extends SwipeRefreshLayout {
    private int downY;
    private int upY;
    private View mChildView;
    private OnPullUpListener pullUpListener;
    private boolean loading = false;

    private Context context;

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public CustomRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomRefreshLayout(Context context) {
        super(context);
        this.context = context;
    }

    public void autoRefresh() {
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(VISIBLE);

            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        Log.d("fuck", getChildCount() + "");
        if (getChildCount() > 1 && mChildView == null) {
            mChildView = getChildAt(1);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.d("fuck", ViewCompat.canScrollVertically(mChildView, 1) + "");
                if (!ViewCompat.canScrollVertically(mChildView, 1) && !loading) {
                    addLoadView();
                    if (pullUpListener != null) {
                        loading = true;
                        requestLayout();
//                        pullUpListener.onLoadMore();
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                upY = (int) ev.getY();
        }
        return super.dispatchTouchEvent(ev);
    }


    private void addLoadView() {
//        LinearLayout view = new LinearLayout(context);
////                (LinearLayout) LayoutInflater.from(context).inflate(R.layout.title_bar, null);
//        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 120);
//        view.setLayoutParams(params);
//        view.bringToFront();
//        addView(view,getChildCount());
//        View footerView = LayoutInflater.from(context).inflate(R.layout.title_bar, null);
//        if (mChildView instanceof ViewGroup) {
//            ViewGroup viewGroup = (ViewGroup) mChildView;
//            viewGroup.addView(footerView);
//        }
    }

    public void setPullUpListener(OnPullUpListener pullUpListener) {
        this.pullUpListener = pullUpListener;
    }

    public interface OnPullUpListener {
        void onLoadMore();
    }

}
