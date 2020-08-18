package com.tencent.receivepushdatashow

import android.app.Activity
import android.content.Context
import com.bumptech.glide.Glide
import com.ehealth.machine.base.adapter.BaseAdapter
import com.ehealth.machine.base.adapter.CommonViewHolder
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.tencent.receivepushdatashow.utils.isDestroy
import com.tencent.receivepushdatashow.video.CustomVideo


class MyAdapter(context: Context, data: MutableList<ResultData>) :
    BaseAdapter<ResultData>(context, data, {
        when (it.type) {
            Type.IMAGE -> R.layout.item_image
            Type.VIDEO -> R.layout.item_video
        }
    }) {


    val videoView: CustomVideo

    init {
        videoView = CustomVideo(mContext)
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL)
        videoView.setIsTouchWiget(true)
    }


    override fun convert(holder: CommonViewHolder, data: ResultData, position: Int) {

        when (data.type) {
            Type.IMAGE -> {
                if (!isDestroy(mContext as Activity?)) {
                    holder.setImageUrl(
                        R.id.image,
                        data.url,
                        { imageView, url -> Glide.with(mContext).load(url).into(imageView) })
                }
            }

            Type.VIDEO -> {
                if (!isDestroy(mContext as Activity?)) {
                    holder.setImageUrl(
                        R.id.thumbimage,
                        data.thumbImageUrl,
                        { imageView, url -> Glide.with(mContext).load(url).into(imageView) })

                }
            }


        }
    }
}