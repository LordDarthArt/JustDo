package tk.lorddarthart.justdoitlist.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup

interface IBaseFragment {
    fun initBinding(inflater: LayoutInflater, container: ViewGroup?)
    fun initialization()
    fun initListeners()
    fun start()
}