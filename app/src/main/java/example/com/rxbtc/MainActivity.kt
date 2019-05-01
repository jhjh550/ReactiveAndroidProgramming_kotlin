package example.com.rxbtc

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.pushtorefresh.storio.sqlite.StorIOSQLite
import example.com.rxbtc.bithumb.RetrofitBithumbServiceFactory
import example.com.rxbtc.storio.StorIOFactory
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
                .map(StockUpdate.Companion::create)
                .doOnNext(this::saveStockUpdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { stockUpdate ->
//                    myLog(stockUpdate.toString())

                    val myAdapter = myRecyclerView.adapter as StockDataAdapter
                    myAdapter.add(stockUpdate)
                }


        myRecyclerView.layoutManager = LinearLayoutManager(this)
        myRecyclerView.adapter = StockDataAdapter()
    }

    private fun saveStockUpdate(stockUpdate: StockUpdate){
        myLog("saveStockUpdate", stockUpdate.stockSymbol)

        val storIOSqlite = StorIOFactory.get(this)
        if(storIOSqlite != null){
            storIOSqlite
                    .put()
                    .`object`(stockUpdate)
                    .prepare()
                    .asRxSingle()
                    .subscribe()
        }
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
