package com.ehealth.machine.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * 使用的时候应该注意：设置layoutManager必须在所有添加头，尾，还有空布局之前
 * 最好的使用顺序是：创建adapter，设置adapter，设置布局管理者，添加头，添加尾，添加空布局
 *
 *
 */
abstract class BaseAdapter<DATA> : RecyclerView.Adapter<CommonViewHolder> {

    /*上下文*/
    var mContext: Context

    /*单条目布局*/
    private var mLayoutId: Int

    /*列表数据集合*/
    private var mDatas: MutableList<DATA>

    /*打气筒*/
    private val mLayoutInflater: LayoutInflater

    /*传入条目布局，返回布局id*/
    private var mTypeSupport: ((DATA) -> Int)? = null


    /**
     * 单类型条目的构造方法
     */
    constructor(mContext: Context, mLayoutId: Int, mDatas: MutableList<DATA>) {
        this.mContext = mContext
        this.mLayoutId = mLayoutId
        this.mDatas = mDatas
        mLayoutInflater = LayoutInflater.from(mContext)

    }

    /*多类型条目的构造方法*/
    constructor(mContext: Context, mDatas: MutableList<DATA>, value: (data: DATA) -> Int) : this(
        mContext,
        -1,
        mDatas
    ) {
        this.mTypeSupport = value
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        /*如果不等于空，证明是需要多布局*/
        mTypeSupport?.let { mLayoutId = viewType }
        /*实例化item对象*/
        val itemView = mLayoutInflater.inflate(mLayoutId, parent, false)

        return CommonViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        convert(holder, mDatas.get(position % mDatas.size), position % mDatas.size)
    }


    override fun getItemCount(): Int {
        //如果就一个item就不滑动了
        return  Int.MAX_VALUE
    }


    /**
     * 设置新数据
     */
    fun setNewData(mDatas: MutableList<DATA>) {
        this.mDatas = mDatas
        notifyDataSetChanged()
    }


    /**
     * 获取列表数据
     */
    fun getmDatas()  = mDatas


    override fun getItemViewType(position: Int): Int {
        /*多类型布局*/
        mTypeSupport?.let { return it.invoke(mDatas.get(position % mDatas.size)) }
        return super.getItemViewType(position % mDatas.size)


    }


    /*绑定数据,扔给子类去实现*/
    abstract fun convert(holder: CommonViewHolder, data: DATA, position: Int)


}
