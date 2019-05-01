package example.com.rxbtc

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class StockDataAdapter : RecyclerView.Adapter<StockUpdateViewHolder>(){
    val PRICE_FORMAT = DecimalFormat("#0.00")
    val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")

    var data = ArrayList<StockUpdate>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = StockUpdateViewHolder(parent)

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: StockUpdateViewHolder, position: Int) {
        data[position].let { item ->
            with(holder){
                stockItemSymbol.text = item.stockSymbol
                stockItemPrice.text = PRICE_FORMAT.format(item.price)
                stockItemDate.text = dateFormat.format(item.date)
            }
        }
    }

    fun add(newItem: StockUpdate){
        for(item in data){
            if(item.stockSymbol.equals(newItem.stockSymbol)){
                if(item.price == newItem.price)
                    return
                break
            }
        }

        data.add(newItem)
        notifyItemInserted(data.size - 1)
    }
}