package example.com.rxbtc

import android.util.Log

class Utils{
    companion object {
        fun myLog(throwable: Throwable) {
            Log.e("APP", "Error on " + Thread.currentThread().getName() + ":", throwable)
        }

        fun myLog(stage: String, throwable: Throwable) {
            Log.e("APP", stage + ":" + Thread.currentThread().name + ": error", throwable)
        }

        fun myLog(stage: String, item: String) {
            Log.d("APP", stage + ":" + Thread.currentThread().name + ":" + item)
        }

        fun myLog(stage: String) {
            Log.d("APP", stage + ":" + Thread.currentThread().name)
        }
    }
}
