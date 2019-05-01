package example.com.rxbtc

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRecyclerView.layoutManager = LinearLayoutManager(this)
        myRecyclerView.adapter = StockDataAdapter()

        Observable.just(
                StockUpdate("google", 12.43, Date()),
                StockUpdate("apple", 654.1, Date()),
                StockUpdate("twtr", 1.43, Date())
        ).subscribe {
            val myAdapter = myRecyclerView.adapter as StockDataAdapter
            myAdapter.add(it)
        }



    }
}
