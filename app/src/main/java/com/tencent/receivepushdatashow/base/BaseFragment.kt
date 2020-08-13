package com.tencent.receivepushdatashow.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    lateinit var mActivity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initListener()

        getSerivceData()
    }


    /*布局*/
    abstract fun getLayoutId(): Int

     abstract fun initListener()


    abstract fun getSerivceData()

}