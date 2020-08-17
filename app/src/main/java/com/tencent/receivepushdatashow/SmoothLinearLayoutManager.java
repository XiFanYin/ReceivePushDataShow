package com.tencent.receivepushdatashow;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 改变轮播图的播放速度
 */
public class SmoothLinearLayoutManager extends LinearLayoutManager implements RecyclerView.OnChildAttachStateChangeListener {
    private PagerSnapHelper mPagerSnapHelper;

    private OnViewPagerListener mOnViewPagerListener;

    private int mDrift;//位移，用来判断移动方向
    //构造方法
    public SmoothLinearLayoutManager(Context context) {
        super(context);
    }
    //构造方法
    public SmoothLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        mPagerSnapHelper = new PagerSnapHelper();
    }



    @Override
    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);
        //设置一次只能滑动一个item
        mPagerSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnChildAttachStateChangeListener(this);
    }



    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
                View viewIdle = mPagerSnapHelper.findSnapView(this);
                int positionIdle = getPosition(viewIdle);
                if (mOnViewPagerListener != null ) {
                    mOnViewPagerListener.onPageSelected(positionIdle,positionIdle == getItemCount() - 1,viewIdle);
                }
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                View viewDrag = mPagerSnapHelper.findSnapView(this);
                int positionDrag = getPosition(viewDrag);


                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                View viewSettling = mPagerSnapHelper.findSnapView(this);
                int positionSettling = getPosition(viewSettling);

                break;

        }
    }


    /**
     * 监听竖直方向的相对偏移量
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }


    /**
     * 监听水平方向的相对偏移量
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dx;
        return super.scrollHorizontallyBy(dx, recycler, state);
    }







    @Override
    public void onChildViewAttachedToWindow(@NonNull View view) {
        if (mOnViewPagerListener != null) {
            mOnViewPagerListener.onInitComplete(view);
        }
    }

    @Override
    public void onChildViewDetachedFromWindow(@NonNull View view) {
        if (mDrift >= 0){
            if (mOnViewPagerListener != null) mOnViewPagerListener.onPageRelease(true,getPosition(view),view);
        }else {
            if (mOnViewPagerListener != null) mOnViewPagerListener.onPageRelease(false,getPosition(view),view);
        }
    }








    /**
     * 设置监听
     */
    public void setOnViewPagerListener(OnViewPagerListener listener){
        this.mOnViewPagerListener = listener;
    }






    //改变滑动速度
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller =
                new LinearSmoothScroller(recyclerView.getContext()) {
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return 0.6f; //返回0.2
                    }
                };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }




}