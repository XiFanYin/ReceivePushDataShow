package com.tencent.receivepushdatashow.video

import android.media.MediaMetadataRetriever
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
        //清理缓存
        GSYVideoManager.instance().clearAllDefaultCache(mActivity)

        val source1 = "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=207536&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss"
        //设置视频第一帧变成封面
        val media = MediaMetadataRetriever()
        media.setDataSource(source1,HashMap())
        val frameAtTime = media.getFrameAtTime()
        val imageView = ImageView(mActivity)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.setImageBitmap(frameAtTime)
        video_player.setThumbImageView(imageView)
        //设置视频
        video_player.setUp(source1, true, "测试视频")
        //设置拉伸全屏
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL)
        //是否可以滑动调整
        video_player.setIsTouchWiget(true)
        //设置循环播放
        video_player.isLooping = true
        //开始播放
        video_player.startPlayLogic()



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