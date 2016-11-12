package org.alex.draggingpanellayout;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.alex.daggerviewhelper.R;


/**
 * 作者：Alex
 * 时间：2016年11月12日
 * 简述：
 */

public class DraggingPanelLayout extends RelativeLayout {
    private View draggerView;
    private int draggerViewHeight;
    private ViewDragHelper viewDragHelper;
    /* 距离顶部最小的距离*/
    private static int minMarginTop;

    public DraggingPanelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        minMarginTop = (int) dp2px(32);
        ViewDragHelperCallback dragHelperCallback = new ViewDragHelperCallback();
        viewDragHelper = ViewDragHelper.create(this, 1.0F, dragHelperCallback);
        /*跟踪左边界拖动*/
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    private final class ViewDragHelperCallback extends ViewDragHelper.Callback {

        /**
         * 手指释放的时候回调
         */
        public void onViewReleased(final View releasedChild, float xvel, float yvel) {

        }

        /**
         * 当captureview的位置发生改变时回调
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            /*往上拖动 dy 为负*/
            LayoutParams draggerViewLayoutParams = (LayoutParams) draggerView.getLayoutParams();
            draggerViewLayoutParams.topMargin = draggerViewLayoutParams.topMargin + dy;
            draggerView.setLayoutParams(draggerViewLayoutParams);

        }


        /**
         * true的时候会锁住当前的边界，false则unLock。
         */
        public boolean onEdgeLock(int edgeFlags) {
            return false;
        }

        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        /**
         * 尝试捕获子view， 返回true表示允许。
         *
         * @param child     尝试捕获的view
         * @param pointerId 指示器id？
         */
        public boolean tryCaptureView(View child, int pointerId) {
            return child == draggerView;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        /**
         * 处理水平方向上的拖动，返回值就是最终确定的移动的位置。
         * 实际上就是判断如果这个坐标在layout之内 那我们就返回这个坐标值。
         * 除此之外就是如果你的layout设置了padding的话，
         * 也可以让子view的活动范围在padding之内的.
         *
         * @param child 被拖动到view
         * @param left  移动到达的x轴的距离
         * @param dx    建议的移动的x距离
         */
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }


        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (top > getHeight() - draggerViewHeight) {
                /*底部边界*/
                top = getHeight() - draggerViewHeight;
            } else if (top < minMarginTop) {
                /*顶部边界*/
                top = minMarginTop;
            }
            return top;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        draggerView = findViewById(R.id.dpl_dragger);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        draggerViewHeight = draggerView.getMeasuredHeight();
    }

    /**
     * 数据转换: dp---->px
     */
    public float dp2px(@FloatRange(from = 0) float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }
}
