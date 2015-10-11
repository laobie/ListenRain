package com.jaeger.listenrain.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Jaeger on 15/10/10.
 * ListenRain
 */
public class FooterView extends View {
    private static float PULL_HEIGHT;
    private Context context;
    private Status status;
    private Path mPath;
    private Paint mPaint;
    private Paint textPaint;

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    private boolean refresh;
    private static int COLOR_GRAY = 0xFF262626;
    private static int COLOR_WHITE = 0xFFffffff;


    private enum Status {
        PULL_DOWN,
        DRAG_DOWN,
        REL_DRAG,
        REFRESH;

        @Override
        public String toString() {
            switch (this) {
                case PULL_DOWN:
                    return "pull down";
                case DRAG_DOWN:
                    return "drag down";
                default:
                    return "unknown state";
            }
        }
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FooterView(Context context) {
        super(context);
        init(context);
    }


    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        PULL_HEIGHT = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(COLOR_GRAY);
        mPath = new Path();

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(25f);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(COLOR_WHITE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            if (getHeight() > PULL_HEIGHT) {
                status = Status.DRAG_DOWN;
            } else {
                status = Status.PULL_DOWN;
            }
            if (isRefresh()) {
                status = Status.REFRESH;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (status) {
            case PULL_DOWN:
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
                break;
            case DRAG_DOWN:
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
                canvas.drawText("放开加载更多", (float) getWidth() / 2, (float) (getHeight() / 2), textPaint);
                break;
            case REFRESH:
                drawRefreshing(canvas, getRefRation());
                invalidate();
//                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
                break;

        }
    }


    private void drawRefreshing(Canvas canvas, float ration) {
        int n = (int) (ration * 3);
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < n + 1; i++) {
            temp.append(".");
        }
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        canvas.drawText("正在加载" + temp.toString(), (float) getWidth() / 2, (float) (getHeight() / 2), textPaint);
    }

    private long mRefStart;
    private long REFRESH_DUR = 2000;

    private float getRefRation() {
        if (refresh) {
            return ((System.currentTimeMillis() - mRefStart) % REFRESH_DUR) / (float) REFRESH_DUR;
        } else {
            status = status.REL_DRAG;
            return 1;
        }

    }

    public void startRefresh() {
        refresh = true;
        status = Status.REFRESH;
        mRefStart = System.currentTimeMillis();
        requestLayout();

    }

}
