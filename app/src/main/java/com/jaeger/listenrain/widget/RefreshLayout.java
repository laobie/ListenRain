package com.jaeger.listenrain.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * Created by Jaeger on 15-7-22.
 * RefreshLayout
 */
public class RefreshLayout extends FrameLayout {
    private final static int PULL_DOWN_MODE = 1;
    private final static int PULL_UP_MODE = -1;
    private int Mode = PULL_DOWN_MODE;
    private Context context;
    private OnRefreshListener onRefreshListener;
    private OnLoadListener onLoadListener;
    private View childView;
    private HeaderView headerView;
    private FooterView footerView;


    private boolean mIsRefreshing;
    private ValueAnimator mUpBackAnimator;
    private float mTouchStartY;
    private float mTouchCurY;
    private float PULL_HEIGHT;
    private int HEADER_HEIGHT;

    public RefreshLayout(Context context) {
        this(context, null, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
        PULL_HEIGHT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, context.getResources().getDisplayMetrics());
        HEADER_HEIGHT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
        if (getChildCount() > 1) {
            throw new RuntimeException("you can only attach one child");
        }
        this.post(new Runnable() {
            @Override
            public void run() {
                childView = getChildAt(0);
                addHeaderView();
                addFooterView();
                autoRefresh();


            }
        });

    }

    private void addHeaderView() {
        headerView = new HeaderView(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        params.gravity = Gravity.TOP;
        headerView.setLayoutParams(params);
        addView(headerView);

    }

    private void addFooterView() {
        footerView = new FooterView(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        params.gravity = Gravity.BOTTOM;
        footerView.setLayoutParams(params);
        addView(footerView);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mIsRefreshing) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mTouchCurY = ev.getY();
                float dy = mTouchCurY - mTouchStartY;
                if (childView != null) {
                    if (dy > 0 && !ViewCompat.canScrollVertically(childView, -1)) {
                        return true;
                    }
                    if (dy < 0 && !ViewCompat.canScrollVertically(childView, 1)) {
                        return true;
                    }
                }
                return false;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsRefreshing) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                mIsRefreshing = false;
                mTouchCurY = event.getY();
                float dy = mTouchCurY - mTouchStartY;
                Mode = dy > 0 ? PULL_DOWN_MODE : PULL_UP_MODE;
                dy = Math.min(PULL_HEIGHT * 2, Math.abs(dy));
                int refreshViewHeight = (int) (new DecelerateInterpolator(10).getInterpolation(dy / PULL_HEIGHT / 2) * dy / 2);
                float moveDistance = Mode == PULL_DOWN_MODE ? refreshViewHeight : -refreshViewHeight;
                if (childView != null) {
                    if (Mode == PULL_DOWN_MODE && !isChildViewCanScrollDown()) {
                        childView.setTranslationY(moveDistance);
                        headerView.getLayoutParams().height = refreshViewHeight;
                        headerView.requestLayout();
                    } else if (Mode == PULL_UP_MODE && !isChildViewCanScrollUp()) {
                        childView.setTranslationY(moveDistance);
                        footerView.getLayoutParams().height = refreshViewHeight;
                        footerView.requestLayout();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (childView != null) {
                    float height = Math.abs(childView.getTranslationY());
                    if (height > HEADER_HEIGHT) {
                        switch (Mode) {
                            case PULL_DOWN_MODE:
                                startRefresh(height);
                                break;
                            case PULL_UP_MODE:
                                startLoad(height);
                                break;
                            default:
                                break;
                        }
                    } else {
                        backToAnimator();
                    }
                }
        }

        return super.onTouchEvent(event);
    }


    private void startRefresh(float height) {
        mIsRefreshing = true;
        mUpBackAnimator = ValueAnimator.ofFloat(height, HEADER_HEIGHT);
        mUpBackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                childView.setTranslationY(val);
                headerView.getLayoutParams().height = (int) val;
                headerView.requestLayout();
                if (val == HEADER_HEIGHT) {
                    headerView.startRefresh();
                    if (onRefreshListener != null) {
                        onRefreshListener.onRefreshing();
                    }
                }
            }
        });
        mUpBackAnimator.setDuration(500);
        mUpBackAnimator.start();
    }

    private void startLoad(float height) {
        mIsRefreshing = true;
        height = -height;
        mUpBackAnimator = ValueAnimator.ofFloat(height, -HEADER_HEIGHT);
        mUpBackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                childView.setTranslationY(val);
                footerView.getLayoutParams().height = (int) -val;
                footerView.requestLayout();
                if (val == -HEADER_HEIGHT) {
                    footerView.startRefresh();
                    if (onLoadListener != null) {
                        onLoadListener.onLoading();
                    }
                }
            }
        });
        mUpBackAnimator.setDuration(500);
        mUpBackAnimator.start();


    }

    public void autoRefresh() {
        if (childView != null) {
            mIsRefreshing = true;
            childView.setTranslationY(HEADER_HEIGHT);
            headerView.getLayoutParams().height = HEADER_HEIGHT;
            headerView.requestLayout();
            headerView.startRefresh();
            if (onRefreshListener != null) {
                onRefreshListener.onRefreshing();
            }
        }
    }

    public void finishRefreshing() {
        if (onRefreshListener != null) {
            onRefreshListener.completeRefresh();
        }
        switch (Mode) {
            case PULL_DOWN_MODE:
                headerView.setIsRefresh(false);
                break;
            case PULL_UP_MODE:
                footerView.setRefresh(false);
                break;
        }
        mIsRefreshing = false;
        backToAnimator();

    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public interface OnRefreshListener {
        void onRefreshing();

        void completeRefresh();

    }

    public interface OnLoadListener {
        void onLoading();
    }


    private boolean isChildViewCanScrollDown() {
        return childView != null && ViewCompat.canScrollVertically(childView, -1);
    }

    private boolean isChildViewCanScrollUp() {
        return childView != null && ViewCompat.canScrollVertically(childView, 1);
    }

    private void backToAnimator() {
        mUpBackAnimator = ValueAnimator.ofFloat(childView.getTranslationY(), 0);
        mUpBackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                childView.setTranslationY(val);

                if (Mode == PULL_DOWN_MODE) {
                    headerView.getLayoutParams().height = (int) val;
                    headerView.requestLayout();
                } else {
                    footerView.getLayoutParams().height = (int) -val;
                    footerView.requestLayout();
                }
            }
        });
        mUpBackAnimator.setDuration(500);
        mUpBackAnimator.start();
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }
}
