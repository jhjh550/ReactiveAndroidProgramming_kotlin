package example.com.rxbtc

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.stock_updat_item.view.*
import java.text.DecimalFormat

class StockUpdateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.stock_updat_item, parent, false)
) {
    val stockItemSymbol = itemView.stock_item_symbol
    val stockItemPrice = itemView.stock_item_price
    val stockItemDate = itemView.stock_item_date

}