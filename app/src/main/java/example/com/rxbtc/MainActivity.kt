package example.com.rxbtc

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import example.com.rxbtc.bithumb.RetrofitBithumbServiceFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bithumbService = RetrofitBithumbServiceFactory().create()
        Observable.interval(0,5, TimeUnit.SECONDS)
                .flatMap {
                    bithumbService.getTicker("BTC").toObservable()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data ->
                    myLog(data.toString())

                    val myAdapter = myRecyclerView.adapter as StockDataAdapter
                    myAdapter.add( StockUpdate.create("BTC", data.data))
                }


        myRecyclerView.layoutManager = LinearLayoutManager(this)
        myRecyclerView.adapter = StockDataAdapter()
    }


    private fun myLog(throwable: Throwable) {
        Log.e("APP", "Error", throwable)
    }

    private fun myLog(stage: String, item: String) {
        Log.d("APP", stage + ":" + Thread.currentThread().name + ":" + item)
    }

    private fun myLog(stage: String) {
        Log.d("APP", stage + ":" + Thread.currentThread().name)
    }
}
