package tk.lorddarthart.justdoitlist.util.helper

import java.lang.Exception

class RequestDataCallback(
    val onSuccess: () -> Unit,
    val onFailure: (e: Exception) -> Unit,
    val onEmpty: () -> Unit
)