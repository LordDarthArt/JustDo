package tk.lorddarthart.justdoitlist.repository.remote

import tk.lorddarthart.justdoitlist.util.helper.RequestDataCallback

interface RemoteRepository {
    fun init()
    fun accessTheRemoteData(requestDataCallback: RequestDataCallback)
}