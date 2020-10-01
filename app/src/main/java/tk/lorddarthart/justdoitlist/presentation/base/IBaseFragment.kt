package tk.lorddarthart.justdoitlist.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup

interface IBaseFragment {
    fun initialization(inflater: LayoutInflater, container: ViewGroup?)
    fun initBinding(inflater: LayoutInflater, container: ViewGroup?)
    fun initListeners()
    fun start()
}