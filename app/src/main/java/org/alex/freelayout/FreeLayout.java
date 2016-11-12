package org.alex.freelayout;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 作者：Alex
 * 时间：2016/11/11 16:16
 * 简述：
 * 子控件加上 android:tag="false" 表示不可以拖动
 * 子控件加上 android:tag="true" 表示可任意拖动
 */
public class FreeLayout extends RelativeLayout {
    private ViewDragHelper viewDragHelper;
    private int width;
    private int height;

    public FreeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
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
            release4BackBorder(releasedChild);
        }

        /**
         * 当captureview的位置发生改变时回调
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

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
            return "true".equals(child.getTag());
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
            return top;
        }
    }

    /**
     * 回到 边沿处
     */
    private void release4BackBorder(View releasedChild) {
        int left = releasedChild.getLeft();
        int right = releasedChild.getRight();
        int top = releasedChild.getTop();
        int bottom = releasedChild.getBottom();
        if ((top >= 0) && (left >= 0) && (right <= width) && (bottom <= height)) {
            return;
        }
        int finalLeft = width - releasedChild.getWidth();
        int finalTop = height - releasedChild.getHeight();
        /*
        * 实际情况证明，这里必须是区分 8 种情况，不能是4种情况，
        * 否则在 飞速滑动的时候，子控件飞出左上角、右上角、左下角、右下角，
        * 不会被拉回来的
        * */
        if ((top >= 0) && (left < 0) && (right <= width) && (bottom <= height)) {
            /*左*/
            viewDragHelper.smoothSlideViewTo(releasedChild, 0, top);
        } else if ((top < 0) && (left > 0) && (right <= width) && (bottom <= height)) {
            /*上*/
            viewDragHelper.smoothSlideViewTo(releasedChild, left, 0);
        } else if ((top >= 0) && (left >= 0) && (right > width) && (bottom <= height)) {
            /*右*/
            viewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, top);
        } else if ((top >= 0) && (left >= 0) && (right <= width) && (bottom > height)) {
            /*下*/
            viewDragHelper.smoothSlideViewTo(releasedChild, left, finalTop);
        } else if ((top < 0) && (left < 0) && (right <= width) && (bottom <= height)) {
            /*左上*/
            viewDragHelper.smoothSlideViewTo(releasedChild, 0, 0);
        } else if ((top < 0) && (left >= 0) && (right > width) && (bottom <= height)) {
            /*右上*/
            viewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, 0);
        } else if ((top >= 0) && (left < 0) && (right <= width) && (bottom > height)) {
            /*左下*/
            viewDragHelper.smoothSlideViewTo(releasedChild, 0, finalTop);
        } else if ((top >= 0) && (left >= 0) && (right > width) && (bottom > height)) {
            /*右下*/
            viewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, finalTop);
        }
        ViewCompat.postInvalidateOnAnimation(FreeLayout.this);
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    /**
     * 数据转换: dp---->px
     */
    public float dp2px(@FloatRange(from = 0) float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    /**
     * sp转px
     */
    public int sp2px(@FloatRange(from = 0) float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
