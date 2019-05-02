package example.com.rxbtc

import android.os.Bundle
import android.util.Log
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class MockActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mock)

        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose { Log.d("MockActivity", "Disposed!") }
                .compose(bindToLifecycle())
                .subscribe()
    }
}
