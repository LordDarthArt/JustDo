package tk.lorddarthart.justdoitlist.util.helper

import tk.lorddarthart.justdoitlist.BuildConfig

object UsefulTools {
    val isDebug: Boolean
        get() = BuildConfig.DEBUG
}