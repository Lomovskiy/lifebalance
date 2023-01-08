package com.lomovskiy.lifebalance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = AppScreenFactory(ViewModelProvider(this, AppViewModelsFactory()))
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, PageMain::class.java, null)
                .commit()
        }
    }

}

abstract class Page<S, E>(
    protected val vm: PageViewModel<S, E>
) : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.statesStream.observe(viewLifecycleOwner) { state: S -> renderState(state) }
    }

    abstract fun renderState(state: S)

}

abstract class PageViewModel<S, E>(initialState: S) : ViewModel() {

    protected val statesStreamInternal = MutableLiveData(initialState)
    val statesStream: LiveData<S> = statesStreamInternal

    abstract fun handleEvent(event: E)

}
