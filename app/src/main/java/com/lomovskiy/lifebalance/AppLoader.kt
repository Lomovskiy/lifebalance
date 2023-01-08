package com.lomovskiy.lifebalance

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppLoader : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}

class AppViewModelsFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when (modelClass) {
            PageMainViewModel::class.java -> {
                return PageMainViewModel() as T
            }
            else -> {
                return super.create(modelClass)
            }
        }
    }

//    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//        return createInternal(modelClass, extras)
//    }
//
//    private fun <T : ViewModel> createInternal(modelClass: Class<T>, extras: CreationExtras?): T {
//        if (extras == null) {
//            return super.create(modelClass)
//        } else {
//            return super.create(modelClass, extras)
//        }
//    }

}

class AppScreenFactory(
    private val viewModelProvider: ViewModelProvider
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        when (className) {
            PageMain::class.java.name -> {
                return PageMain(viewModelProvider[PageMainViewModel::class.java])
            }
            else -> {
                return super.instantiate(classLoader, className)
            }
        }
    }

}
