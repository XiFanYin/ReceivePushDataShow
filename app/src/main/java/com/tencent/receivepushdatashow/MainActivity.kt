package com.tencent.receivepushdatashow

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
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


    //模拟推送过来的数据
    lateinit var pushData: ArrayList<ResultData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pushData = arrayListOf(
            ResultData(
                Type.IMAGE,
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1920121267,3981894473&fm=26&gp=0.jpg",
                "这是一个图片"
            ),
            ResultData(
                Type.IMAGE,
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1659012856,346800732&fm=26&gp=0.jpg",
                "这是一个图片"
            ),
            ResultData(
                Type.VIDEO,
                "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=207536&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss",
                "这是一个视频"
            ),
            ResultData(
                Type.TEXT,
                "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=207536&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss",
                "这是一个富文本"
            )
        )

        PagerSnapHelper().attachToRecyclerView(mRecyclerView)
        val menuadapter = MyAdapter(this, pushData)
        mRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView.adapter = menuadapter
        // TODO 1设置无线轮播，2视频播放完成后再去轮播，3.附文本跑马灯，4新来的推送数据，如何做到偷偷改变，5页面滚动，如果当前是视频，停止播放视频


    }


}