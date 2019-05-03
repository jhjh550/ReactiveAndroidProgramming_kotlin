package example.com.rxbtc.demo

import android.util.Log
import io.reactivex.Observable

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


    }
}