package com.tencent.receivepushdatashow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tencent.receivepushdatashow.image.ImageFragment
import com.tencent.receivepushdatashow.video.VideoFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //轮播图效果
//        supportFragmentManager.beginTransaction().add(
//            R.id.container, ImageFragment.getInstance(
//                arrayListOf("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1920121267,3981894473&fm=26&gp=0.jpg",
//                    "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1659012856,346800732&fm=26&gp=0.jpg")
//            )
//        ).commit()



        //视频播放
        supportFragmentManager.beginTransaction().add(
            R.id.container, VideoFragment.getInstance(
                arrayListOf("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1920121267,3981894473&fm=26&gp=0.jpg",
                    "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1659012856,346800732&fm=26&gp=0.jpg")
            )
        ).commit()




    }
}