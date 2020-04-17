package com.demo.demoapp.listview.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.demo.demoapp.R
import com.demo.demoapp.databinding.ActivityDataListBinding
import com.demo.demoapp.listview.viewmodel.DataListViewModel

class DataListActivity: AppCompatActivity() {
    lateinit var mBinding : ActivityDataListBinding
    lateinit var dataListViewModel : DataListViewModel
    lateinit var view: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_list)

        val factory = DataListViewModel.DataListFactory(application, this, mBinding)
        dataListViewModel = ViewModelProviders.of(this@DataListActivity,factory).get(DataListViewModel::class.java)

    }
}