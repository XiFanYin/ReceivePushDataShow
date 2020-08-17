package com.tencent.receivepushdatashow

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.tencent.receivepushdatashow.video.CustomVideo
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    //模拟推送过来的数据
    lateinit var pushData: ArrayList<ResultData>

    //创建视频播放器
    lateinit var videoView: CustomVideo

    private var mCurPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //模拟数据
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
                "这是一个视频",
                "http://img.kaiyanapp.com/86eb064764210ae5df100284aa40920f.png?imageMogr2/quality/60/format/jpg"
            )
        )
        //创建adapter
        val menuadapter = MyAdapter(this, pushData)
        val layoutManager = SmoothLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = menuadapter

        //创建视频播放器
        videoView = CustomVideo(this)
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL)
        videoView.setIsTouchWiget(false)

        layoutManager.setOnViewPagerListener(object : OnViewPagerListener {
            override fun onInitComplete() {
                //说明第一个是视频
            }

            override fun onPageRelease(isNext: Boolean, position: Int, view: View?) {

                if (menuadapter.getItemViewType(position) == R.layout.item_video) {
                    GSYVideoManager.releaseAllVideos()
                    val parent: ViewParent = videoView.getParent()
                    if (parent is FrameLayout) {
                        parent.removeView(videoView)
                    }
                }
            }

            override fun onPageSelected(position: Int, isBottom: Boolean, view: View?) {
                if (mCurPos!=position &&menuadapter.getItemViewType(position) == R.layout.item_video) {
                    videoView.setUp(pushData.get(position % pushData.size).url, true, "")
                    videoView.startPlayLogic()
                    videoView.setVideoAllCallBack(object : GSYSampleCallBack() {
                        override fun onPrepared(url: String?, vararg objects: Any?) {
                            (view as ViewGroup).addView(videoView)
                        }

                    })
                }

                mCurPos = position
            }
        })

        //设置每隔几秒执行一次
//        val scheduledExecutorService: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
//        scheduledExecutorService.scheduleAtFixedRate( {
//            mRecyclerView.smoothScrollToPosition(
//                layoutManager.findFirstVisibleItemPosition() + 1
//            )
//        }, 2000, 2000, TimeUnit.MILLISECONDS)
//


    }


    override fun onPause() {
        super.onPause()
        videoView.onVideoPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()

    }

    override fun onBackPressed() {
        //释放所有
        videoView.setVideoAllCallBack(null)
        super.onBackPressed()
    }


}