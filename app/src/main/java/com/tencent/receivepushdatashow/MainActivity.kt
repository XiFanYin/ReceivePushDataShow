package com.tencent.receivepushdatashow

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
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
import java.text.FieldPosition


class MainActivity : AppCompatActivity() {
    //图片显示时间
    val ImageDelayed = 10000L

    //模拟推送过来的数据
    var pushData: ArrayList<ResultData> = arrayListOf(
        ResultData(
            Type.VIDEO,
            "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=207536&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss",
            "这是一个视频",
            "http://img.kaiyanapp.com/86eb064764210ae5df100284aa40920f.png?imageMogr2/quality/60/format/jpg"
        ),
        ResultData(
            Type.IMAGE,
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1920121267,3981894473&fm=26&gp=0.jpg",
            "这是一个图片"
        )


    )
    var pushData2: ArrayList<ResultData> = arrayListOf(
        ResultData(
            Type.VIDEO,
            "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=198308&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss",
            "这是一个视频",
            "http://img.kaiyanapp.com/5046e6f8e43e66cbeec1d73dfd7025af.png?imageMogr2/quality/60/format/jpg"
        ),

        ResultData(
            Type.IMAGE,
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1659012856,346800732&fm=26&gp=0.jpg",
            "这是一个图片"
        )

    )

    //创建视频播放器
    lateinit var videoView: CustomVideo

    //布局管理器
    private lateinit var layoutManager: SmoothLinearLayoutManager

    //创建adapter
    private lateinit var myAdapter: MyAdapter

    //创建主线程handler消息
    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            mRecyclerView.smoothScrollToPosition(
                layoutManager.findFirstVisibleItemPosition() + 1
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //创建视频播放器对象
        creatVideoView()

        //创建adapter
        myAdapter = MyAdapter(this, pushData)
        //创建布局管理器
        layoutManager = SmoothLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = myAdapter
        //设置条目滚动监听
        layoutManager.setOnViewPagerListener(object : OnViewPagerListener {
            override fun onInitComplete(view: View?) {
                // 如果第一个是视频
                if (myAdapter.getItemViewType(0) == R.layout.item_video) {
                    //获取视频url
                    val url = myAdapter.getmDatas().get(0).url
                    //设置视频播放路径
                    videoView.setUp(url, true, "")
                    //设置视频监听
                    videoView.setVideoAllCallBack(object : GSYSampleCallBack() {
                        override fun onStartPrepared(url: String?, vararg objects: Any?) {
                            //列表刷新的时候防止视频正在播放，导致错误
                            removeParent()
                        }

                        override fun onPrepared(url: String?, vararg objects: Any?) {
                            //发送延迟切换条目消息
                            mHandler.sendEmptyMessageDelayed(
                                100,
                                GSYVideoManager.instance().duration
                            )
                            //添加播放器到布局
                            (view as ViewGroup).addView(videoView)
                        }

                        override fun onAutoComplete(url: String?, vararg objects: Any?) {
                            //播放完成，移除播放器，防止黑屏
                            removeParent()
                        }

                    })
                    //开始播放
                    videoView.startPlayLogic()

                } else {
                    //发送图片延迟消息
                    mHandler.sendEmptyMessageDelayed(200, ImageDelayed)
                }

            }

            override fun onPageRelease(isNext: Boolean, position: Int, view: View?) {

            }

            override fun onPageSelected(position: Int, isBottom: Boolean, view: View?) {
                //获取选择的布局url
                val url = myAdapter.getmDatas().get(position % myAdapter.getmDatas().size).url
                //如果是视频
                if (myAdapter.getItemViewType(position) == R.layout.item_video) {
                    //移除视频播放器
                    removeParent()
                    //播放视频
                    videoView.setUp(url, true, "")
                    //设置视频监听
                    videoView.setVideoAllCallBack(object : GSYSampleCallBack() {
                        override fun onPrepared(url: String?, vararg objects: Any?) {
                            mHandler.removeCallbacksAndMessages(null)
                            mHandler.sendEmptyMessageDelayed(
                                100,
                                GSYVideoManager.instance().duration
                            )
                            (view as ViewGroup).addView(videoView)
                        }

                        override fun onAutoComplete(url: String?, vararg objects: Any?) {
                            //播放完成，移除播放器，防止黑屏
                            removeParent()
                        }

                    })
                    //播放视频
                    videoView.startPlayLogic()

                } else {
                    //如果当前页面显示的是图片，但是视频还在播放就停止播放视频
                    if (videoView.isInPlayingState) {
                        removeParent()
                    }
                    mHandler.removeCallbacksAndMessages(null)
                    mHandler.sendEmptyMessageDelayed(200, ImageDelayed)
                }

            }
        })



        pushImage.setOnClickListener {
            myAdapter.setNewData(pushData2)
        }


    }






    //创建视频播放器对象
    private fun creatVideoView() {
        //创建视频播放器
        videoView = CustomVideo(this)
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL)
        videoView.setIsTouchWiget(false)
    }

    //停止播放器，移除父类
    fun removeParent() {
        if (videoView.isInPlayingState) {
            GSYVideoManager.releaseAllVideos()
        }
        val parent: ViewParent? = videoView.getParent()
        if (parent != null && parent is FrameLayout) {
            parent.removeView(videoView)
        }
    }


    override fun onResume() {
        super.onResume()
        videoView.onVideoResume()
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