package com.whn.whn.parallax;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by whn on 2016/12/14.
 * 视差lisView
 */

public class ParallaxListView extends ListView{
    private ImageView ImageView;
    private int maxHeight;
    private int oldHeight;
    private LinearLayout.LayoutParams layoutParams;

    public ParallaxListView(Context context) {
        super(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取imageView,
     */
    public void  setImageView(ImageView imageView){
        this.ImageView = imageView;
        //获取最初的高度
        oldHeight = imageView.getResources().getDimensionPixelSize(R.dimen.header_height);
        //获取原始图片最大高度
        maxHeight = ImageView.getDrawable().getIntrinsicHeight();
    }


    /**
     * listview滑动到头时继续滑动会执行，而且所有的能够滑动的控件都有该方法
     * @param deltaY    表示手指滑动的距离,顶部到头是负值，底部到头是正的
     * @param maxOverScrollY    表示listview到头的时候能够滚动的最大距离
     * @param isTouchEvent    true：是手指拖动到头，false:是靠惯性滑动到头(fling)
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if(isTouchEvent && deltaY<0){
            //顶部用手向下滑动时,改变图片的大小,视差效果/2
            int newHeight = ImageView.getHeight()+ Math.abs(deltaY)/2;
            if(newHeight>maxHeight){
                //放大效果不能大于原图
                newHeight = maxHeight;
            }
            layoutParams = (LinearLayout.LayoutParams) ImageView.getLayoutParams();
            layoutParams.height = newHeight;
            ImageView.setLayoutParams(layoutParams);
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }


    /**
     * 抬手,图片回到初始高度 --- ValueAnimator
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction()== MotionEvent.ACTION_UP){
            ValueAnimator va = ValueAnimator.ofInt(ImageView.getHeight(),oldHeight);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int h = (int) animation.getAnimatedValue();
                    layoutParams.height = h;
                    ImageView.setLayoutParams(layoutParams);
                }
            });

            va.setInterpolator(new OvershootInterpolator());
            va.setDuration(500);
            va.start();


        }
        return super.onTouchEvent(ev);
    }
}
