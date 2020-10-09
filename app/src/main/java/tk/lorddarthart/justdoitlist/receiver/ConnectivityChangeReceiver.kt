package tk.lorddarthart.justdoitlist.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import tk.lorddarthart.justdoitlist.util.helper.longToast
import java.net.URL

class ConnectivityChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (!checkInternet()) {
            context?.longToast { "Сеть недоступна" }
        } else {
        }
    }

    private fun checkInternet(): Boolean {
        return try {
            val ipAddr = "http://46.231.213.38/zzt/ru_RU/"
            val url = URL(ipAddr)
            true
        } catch (e: Exception) {
            false
        }
    }
}