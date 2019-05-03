package example.com.rxbtc

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.stock_updat_item.view.*
import java.text.DecimalFormat

class StockUpdateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.stock_updat_item, parent, false)
) {
    val stockItemSymbol = itemView.stock_item_symbol
    val stockItemPrice = itemView.stock_item_price
    val stockItemDate = itemView.stock_item_date
    val twitterStatus = itemView.stock_item_twitter_status

    fun setIsStatusUpdate(twitterStatusUpdate: Boolean){
        if (twitterStatusUpdate == true) {
            twitterStatus.visibility = View.VISIBLE
            stockItemPrice.visibility = View.GONE
            stockItemSymbol.visibility = View.GONE
        }else{
            twitterStatus.visibility = View.GONE
            stockItemPrice.visibility = View.VISIBLE
            stockItemSymbol.visibility = View.VISIBLE
        }
    }

}