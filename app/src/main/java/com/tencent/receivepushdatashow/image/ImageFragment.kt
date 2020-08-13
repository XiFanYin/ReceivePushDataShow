package com.tencent.receivepushdatashow.image

import android.os.Bundle
import com.tencent.receivepushdatashow.R
import com.tencent.receivepushdatashow.base.BaseFragment
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment : BaseFragment() {
    lateinit var imageList: ArrayList<String>

    //伴生对象
    companion object {
        fun getInstance(imageList: ArrayList<String>): ImageFragment {
            val fragment = ImageFragment()
            val bundle = Bundle()
            bundle.putSerializable("imageList", imageList)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageList = arguments?.getStringArrayList("imageList") as ArrayList<String>
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_image
    }

    override fun initListener() {
        banner.setImageLoader(GlideImageLoader())
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR)
        banner.setImages(imageList)
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.ZoomOut)
        //设置轮播时间
        banner.setDelayTime(10000)
        banner.start()
    }


    override fun getSerivceData() {}
}