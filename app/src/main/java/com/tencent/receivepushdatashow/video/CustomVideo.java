package com.tencent.receivepushdatashow.video;

import android.content.Context;
import android.util.AttributeSet;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.tencent.receivepushdatashow.R;

/**
 * 制定一视频播放
 */
public class CustomVideo extends StandardGSYVideoPlayer {


    public CustomVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public CustomVideo(Context context) {
        super(context);
    }

    public CustomVideo(Context context, AttributeSet attrs) {
        super(context, attrs);


    }


    @Override
    protected void init(Context context) {
        super.init(context);
        initView();

    }


    private void initView() {


    }

    //自定义布局
    @Override
    public int getLayoutId() {
        return R.layout.video_custom;
    }

    //自定义手势触摸时间
    @Override
    protected void touchSurfaceMoveFullLogic(float absDeltaX, float absDeltaY) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);
        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false;

        //不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = true;

        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false;
    }

    //进制双击暂停和播放
    @Override
    protected void touchDoubleUp() {
        //super.touchDoubleUp();
        //不需要双击暂停
    }


}
