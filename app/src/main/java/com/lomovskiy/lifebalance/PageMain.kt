package com.lomovskiy.lifebalance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class PageMain(vm: PageMainViewModel) : Page<PageMain.State, PageMain.Event>(vm), View.OnClickListener {

    private lateinit var labelCount: TextView
    private lateinit var buttonAdd: Button
    private lateinit var buttonRemove: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_main, container, false).also {
            labelCount = it.findViewById(R.id.label_count)
            buttonAdd = it.findViewById(R.id.button_add)
            buttonRemove = it.findViewById(R.id.button_remove)
            buttonAdd.setOnClickListener(this)
            buttonRemove.setOnClickListener(this)
        }
    }

    override fun renderState(state: State) {
        labelCount.text = state.count.toString()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_add -> {
                vm.handleEvent(Event.Add)
            }
            R.id.button_remove -> {
                vm.handleEvent(Event.Remove)
            }
        }
    }

    sealed interface Event {
        object Add : Event
        object Remove : Event
    }

    data class State(val count: Int) {

        companion object {

            fun empty(): State {
                return State(0)
            }

        }

    }

}

class PageMainViewModel : PageViewModel<PageMain.State, PageMain.Event>(PageMain.State.empty()) {

    override fun handleEvent(event: PageMain.Event) {
        when (event) {
            PageMain.Event.Add -> {
                val currentState: PageMain.State? = statesStream.value
                statesStreamInternal.value = currentState?.copy(count = currentState.count + 1)
            }
            PageMain.Event.Remove -> {
                val currentState: PageMain.State? = statesStream.value
                statesStreamInternal.value = currentState?.copy(count = currentState.count - 1)
            }
        }
    }

}
