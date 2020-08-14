package com.tencent.receivepushdatashow

import android.content.Context
import com.bumptech.glide.Glide
import com.ehealth.machine.base.adapter.BaseAdapter
import com.ehealth.machine.base.adapter.CommonViewHolder
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.tencent.receivepushdatashow.video.CustomVideo

class MyAdapter(context: Context, data: MutableList<ResultData>?) :
    BaseAdapter<ResultData>(context, data, {
        when (it.type) {
            Type.IMAGE -> R.layout.itme_image
            Type.VIDEO -> R.layout.item_video
            Type.TEXT -> R.layout.itme_text
        }
    }) {
    override fun convert(holder: CommonViewHolder, data: ResultData, position: Int) {
        when (data.type) {
            Type.IMAGE -> {
                holder.setImageUrl(
                    R.id.image,
                    data.url,
                    { imageView, url -> Glide.with(mContext).load(url).into(imageView) })
            }

            Type.VIDEO -> {
                val video_player = holder.getView<CustomVideo>(R.id.video_player)
                //设置视频
                video_player.setUp(data.url, true, "测试视频")
                //设置拉伸全屏
                GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL)
                //是否可以滑动调整
                video_player.setIsTouchWiget(true)
                //开始播放
                video_player.startPlayLogic()
            }

            Type.TEXT -> {
                holder.setText(R.id.textview,data.description)
            }
        }
    }
}