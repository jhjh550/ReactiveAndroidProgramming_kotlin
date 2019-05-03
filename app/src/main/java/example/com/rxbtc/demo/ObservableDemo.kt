package example.com.rxbtc.demo

import android.util.Log
import example.com.rxbtc.Utils
import example.com.rxbtc.Utils.Companion.myLog
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class ObservableDemo {
    companion object {
        fun demo0(){
            Observable.create<Int>{
                try {
                    it.onNext(1)
                    it.onNext(2)
                }catch (e:Exception){
                    e.printStackTrace()
                }finally {
                    it.onComplete()
                }

            }.subscribe { Log.d("hello world",it.toString())}
        }

        fun demo1(){
            Observable.zip(
                    Observable.just("One", "Two", "Three")
                            .doOnDispose { myLog("ziptest", "just-onDispose") }
                            .doOnTerminate { myLog("ziptest", "just-onTerminate") },
                    Observable.interval(1, TimeUnit.SECONDS)
                            .doOnDispose { myLog("ziptest", "interval-onDispose") }
                            .doOnTerminate { myLog("ziptest", "interval-onTerminate") },
                    BiFunction { number:String, interval:Long -> number+" - "+interval })
                            .doOnDispose { myLog("ziptest", "zip-onDispose") }
                            .doOnTerminate { myLog("ziptest", "zip-onTerminate") }
                    .subscribe { myLog("ziptest", it) }

        }

        fun demo2(){
            Observable.combineLatest(
                    Observable.just("One", "Two", "Three"),
                    Observable.interval(1, TimeUnit.SECONDS),
                    BiFunction { number:String, interval:Long -> number+" - "+interval }
            ).subscribe { myLog("ziptest", it) }
        }

        fun demo3(){
            Observable.concat(
                    //Observable.just(1,2),
                    //Observable.just(3,4)
                    Observable.interval(3, TimeUnit.SECONDS),
                    Observable.just(100,200)

            ).subscribe { myLog("concat", it.toString()) }
        }

        fun demo4(){
            Observable.merge(
                    Observable.interval(3, TimeUnit.SECONDS),
                    Observable.just(100,200)

            ).subscribe { myLog("merge", it.toString()) }
        }


    }
}