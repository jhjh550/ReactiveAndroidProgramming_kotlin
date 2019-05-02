package example.com.rxbtc

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.pushtorefresh.storio.sqlite.queries.Query
import example.com.rxbtc.Utils.Companion.myLog
import example.com.rxbtc.bithumb.RetrofitBithumbServiceFactory
import example.com.rxbtc.bithumb.json.BithumbOneStockResult
import example.com.rxbtc.demo.ExceptionDemo
import example.com.rxbtc.storio.StockUpdateTable
import example.com.rxbtc.storio.StorIOFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ExceptionDemo.demo2()

        RxJavaPlugins.setErrorHandler(ErrorHandler.instance)




        val bithumbService = RetrofitBithumbServiceFactory().create()
        Observable.interval(0,5, TimeUnit.SECONDS)
                .flatMap {
                    //bithumbService.getTicker("BTC").toObservable()
                    Observable.error<BithumbOneStockResult>(RuntimeException("Oops"))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { error ->
                    myLog("doOnError", "error")
                    Toast.makeText(this, "We could't reach internet - falling back to local data",
                            Toast.LENGTH_SHORT).show()
                }


                .subscribeOn(Schedulers.io())
                .map(StockUpdate.Companion::create)
                .doOnNext(this::saveStockUpdate)
//                .onExceptionResumeNext{
//                    StorIOFactory.get(this).get()
//                            .listOfObjects(StockUpdate::class.java)
//                            .withQuery(Query.builder()
//                                    .table(StockUpdateTable.TABLE)
//                                    .orderBy("date DESC")
//                                    .limit(50)
//                                    .build()
//                            ).prepare()
//                            .asRxObservable()
//                            .take(1)
//                            .flatMap(Observable::fromIterable)
//                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ stockUpdate ->
                    val myAdapter = myRecyclerView.adapter as StockDataAdapter
                    myAdapter.add(stockUpdate)

                    no_data_available.visibility = View.GONE
                }, { error ->
                    if(myRecyclerView.adapter?.itemCount == 0 )
                        no_data_available.visibility = View.VISIBLE
                })


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
}
