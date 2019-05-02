package example.com.rxbtc

import android.util.Log
import io.reactivex.functions.Consumer

class ErrorHandler: Consumer<Throwable> {
    companion object {
        val instance = ErrorHandler()
    }

    override fun accept(t: Throwable?) {
        Log.e("App", "Error on "+Thread.currentThread().name+" : ", t)
    }
}