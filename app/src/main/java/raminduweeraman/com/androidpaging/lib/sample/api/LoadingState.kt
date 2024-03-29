package raminduweeraman.com.androidpaging.lib.sample.api

enum class Status {
    LOADING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState  constructor(
        val status: Status,
        val message: String? = null) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.LOADING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }
}
