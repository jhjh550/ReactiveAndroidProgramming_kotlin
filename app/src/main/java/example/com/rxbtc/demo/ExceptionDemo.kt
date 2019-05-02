package example.com.rxbtc.demo


import example.com.rxbtc.ErrorHandler
import example.com.rxbtc.Utils.Companion.myLog
import example.com.rxbtc.Utils

import io.reactivex.Observable
import java.lang.RuntimeException

class ExceptionDemo{
    companion object {
        fun demo0(){
            Observable.just("1")
                    .doOnNext { throw RuntimeException() }
                    .subscribe({ item ->
                        myLog("subscribe123", item)
                    },Utils.Companion::myLog)
        }

        fun demo1(){
            Observable.error<String>(RuntimeException("Crash"))
                    .onExceptionResumeNext(Observable.just("hello exception"))
                    .subscribe({
                        myLog("subscribe", it)
                    },{
                        myLog("subscribe", it)
                    })
        }

        fun demo2(){
            Observable.error<String>(Error("Crash!"))
                    .doOnError(ErrorHandler.instance)
                    .subscribe({item -> myLog("subscribe", item)},
                            { ErrorHandler.instance })
        }
    }
}