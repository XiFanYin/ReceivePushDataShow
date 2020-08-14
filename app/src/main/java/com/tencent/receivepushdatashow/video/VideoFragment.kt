package com.tencent.receivepushdatashow.video

import android.os.Bundle
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.tencent.receivepushdatashow.R
import com.tencent.receivepushdatashow.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragment : BaseFragment() {


    //伴生对象
    companion object {
        fun getInstance(imageList: ArrayList<String>): VideoFragment {
            val fragment = VideoFragment()
            val bundle = Bundle()
            bundle.putSerializable("video", imageList)
            fragment.arguments = bundle
            return fragment
        }
    }





    override fun getLayoutId() = R.layout.fragment_video

    override fun initListener() {
        val source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
        video_player.setUp(source1, true, "测试视频")
        //增加封面
        val imageView = ImageView(mActivity)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.setImageResource(R.mipmap.ic_launcher)
        video_player.setThumbImageView(imageView)

        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL)
        //是否可以滑动调整
        video_player.setIsTouchWiget(true)
        //开始播放
        video_player.startPlayLogic()
        video_player.isLooping = true




    }

    override fun getSerivceData() {

    }


    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }


}