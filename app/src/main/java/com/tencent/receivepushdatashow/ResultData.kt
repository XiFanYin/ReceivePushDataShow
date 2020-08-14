package com.tencent.receivepushdatashow


/**
 *type 1表示图片，2表示视频，3表示富文本
 */

data class ResultData(val type: Type, val url: String, val description: String)