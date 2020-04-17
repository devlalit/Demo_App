package com.demo.demoapp.listview.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.*
import com.demo.demoapp.R
import com.demo.demoapp.databinding.ActivityDataListBinding
import com.demo.demoapp.listview.model.Response
import com.demo.demoapp.listview.view.activity.DataListActivity
import com.demo.demoapp.repository.ApiServiceFactory


class DataListViewModel(
        @Nullable application: Application, var activity: DataListActivity,
        private var mBinding: ActivityDataListBinding) : AndroidViewModel(application) {
    lateinit var retroRepository: ApiServiceFactory
    lateinit var context : Context

    var liveData: LiveData<Response?>? = null
    init {
        liveData = ApiServiceFactory.getInstance(activity).getLogin()
        observeLoginViewModel()
    }
    fun getObservableLogin(): LiveData<Response?>? {
        return liveData
    }
    fun observeLoginViewModel() {
        try {
            getObservableLogin()?.observe(activity, object : Observer<Response?> {
                override fun onChanged(t: Response?) {
                    if (t !== null) {
                        if (t?.title.toString() .equals("About Canada")  ) {
                            Toast.makeText(activity, "Fetch the data successfully", Toast.LENGTH_SHORT)
                        } else {

                        }
                    } else {

                    }
                }
            })
        } catch (e: Exception) {
            print(e)

        }
    }
    class DataListFactory(
            @param:NonNull @field:NonNull private val application: Application, private val activity: DataListActivity,
            private val mBinding: ActivityDataListBinding) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DataListViewModel(application, activity, mBinding) as T
        }
    }
}