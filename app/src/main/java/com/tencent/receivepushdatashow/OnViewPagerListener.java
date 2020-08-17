package com.tencent.receivepushdatashow;

import android.view.View;

public interface  OnViewPagerListener {

    /*初始化完成*/
    void onInitComplete(View view);

    /*释放的监听*/
    void onPageRelease(boolean isNext,int position,View view);

    /*选中的监听以及判断是否滑动到底部*/
    void onPageSelected(int position,boolean isBottom,View view);

}
