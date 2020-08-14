package com.tencent.receivepushdatashow

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.tencent.receivepushdatashow.image.GlideImageLoader
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_image.banner
import kotlinx.android.synthetic.main.fragment_video.video_player


class MainActivity : AppCompatActivity() {

    val imageList = arrayListOf(
        "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1920121267,3981894473&fm=26&gp=0.jpg",
        "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1659012856,346800732&fm=26&gp=0.jpg"
    )

    val videoUrl =
        "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=207536&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showImages(imageList)


        pushImage.setOnClickListener {
            cutImages(imageList)
        }

        pushvideo.setOnClickListener {
            cutVideo(videoUrl)
        }


    }

    //显示轮播图
    fun showImages(images: ArrayList<String>) {
        //轮播图效果
        banner.setImageLoader(GlideImageLoader())
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR)
        banner.setImages(images)
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.ZoomOut)
        //设置轮播时间
        banner.setDelayTime(10000)
        banner.start()
    }



    //切换到轮播图
    fun cutImages(images: ArrayList<String>) {
        stopVideo()
        fadeInOut(banner,video_player)
        showImages(images)
    }


    //显示视频
    fun cutVideo(videoUrl: String) {
        //设置视频
        video_player.setUp(videoUrl, true, "测试视频")
        //设置拉伸全屏
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL)
        //是否可以滑动调整
        video_player.setIsTouchWiget(true)
        //设置循环播放
        video_player.isLooping = true
        //设置开始播放监听
        video_player.setVideoAllCallBack(object : GSYSampleCallBack() {
            override fun onPrepared(url: String?, vararg objects: Any?) {
                fadeInOut(video_player,banner)
            }
        })
        //开始播放
        video_player.startPlayLogic()

    }

    //停止视频播放
    fun stopVideo() {
        //停止视频
        GSYVideoManager.releaseAllVideos()

    }


    /**
     * 隐藏和显示动画
     */
    fun fadeInOut(InView: View, OutView: View) {

        val animation: Animation = AlphaAnimation(0f, 1f)
        animation.setDuration(400)
        InView.startAnimation(animation)
        val animation2: Animation = AlphaAnimation(1f, 0f)
        animation2.setDuration(400)
        OutView.startAnimation(animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            //动画完全结束时候调用
            override fun onAnimationEnd(animation: Animation?) {
                InView.visibility = View.VISIBLE
                OutView.visibility = View.GONE
            }

            //动画开始时候调用
            override fun onAnimationStart(animation: Animation?) {
                InView.visibility = View.VISIBLE
                OutView.visibility = View.VISIBLE
            }

            //动画重复时候调用
            override fun onAnimationRepeat(animation: Animation?) {
            }
        })


    }


    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }


    //页面销毁，停止视频播放
    override fun onDestroy() {
        super.onDestroy()
        stopVideo()
    }


}