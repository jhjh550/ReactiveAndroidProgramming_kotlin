package example.com.rxbtc

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import example.com.rxbtc.Utils.Companion.myLog
import example.com.rxbtc.bithumb.RetrofitBithumbServiceFactory
import example.com.rxbtc.storio.StorIOFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import twitter4j.*
import twitter4j.conf.Configuration
import twitter4j.conf.ConfigurationBuilder
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MainActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ExceptionDemo.demo2()

        RxJavaPlugins.setErrorHandler(ErrorHandler.instance)
        bithumbInit()


    }

    fun ob(){
        Observable.create<String> { emitter ->
            btn_start_mock_activity.setOnClickListener {
                emitter.onNext("hello world")
            }

            emitter.setCancellable{
                btn_start_mock_activity.setOnClickListener(null)
            }
        }.subscribe {
            btn_start_mock_activity.text = it
        }
    }

    fun observeTwitterStream(configuration: Configuration, filterQuery: FilterQuery) : Observable<Status>{
        return Observable.create { emitter ->

            val statusListener = object: StatusListener{
                override fun onStatus(status: Status?) {
                    myLog(status!!.user.name+" : "+status!!.text)
                    emitter.onNext(status)
                }

                override fun onException(ex: Exception?) {
                    if(ex != null) {
                        ex.printStackTrace()
                        emitter.onError(ex)
                    }
                }

                override fun onTrackLimitationNotice(numberOfLimitedStatuses: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onStallWarning(warning: StallWarning?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDeletionNotice(statusDeletionNotice: StatusDeletionNotice?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onScrubGeo(userId: Long, upToStatusId: Long) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
            val twitterStream = TwitterStreamFactory(configuration).instance
//            twitterStream.addListener(statusListener)// https://codeday.me/ko/qa/20190501/430010.html
            Twitter4jFixer.addListener(twitterStream, statusListener)
            twitterStream.filter(filterQuery)
            emitter.setCancellable{ twitterStream.shutdown() }

        }
    }

    fun bithumbInit(){

        val configuration = ConfigurationBuilder()
                    .setDebugEnabled(true)
                    .setOAuthConsumerKey(" ")
                    .setOAuthConsumerSecret(" ")
                    .setOAuthAccessToken(" ")
                    .setOAuthAccessTokenSecret(" ")
                    .build()

            val filterQuery = FilterQuery().track("bitcoin").language("en")

        val bithumbService = RetrofitBithumbServiceFactory().create()
        Observable.merge(
                Observable.interval(0,10, TimeUnit.SECONDS)
                        .flatMap { bithumbService.getTicker("BTC").toObservable() }
                        .map(StockUpdate.Companion::create),

                observeTwitterStream(configuration, filterQuery)
                        .sample(2000, TimeUnit.MILLISECONDS)
                        .map(StockUpdate.Companion::create)
        )

                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { error ->
                    myLog("doOnError", "error")
                    Toast.makeText(this, "We could't reach internet - falling back to local data",
                            Toast.LENGTH_SHORT).show()
                }
                .subscribeOn(Schedulers.io())

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
                    myRecyclerView.smoothScrollToPosition(0)

                    no_data_available.visibility = View.GONE
                }, { error ->
                    if(myRecyclerView.adapter?.itemCount == 0 )
                        no_data_available.visibility = View.VISIBLE
                })


        myRecyclerView.layoutManager = LinearLayoutManager(this.applicationContext)
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
