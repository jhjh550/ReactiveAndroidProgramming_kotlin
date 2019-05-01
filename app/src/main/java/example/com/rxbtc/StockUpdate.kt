package example.com.rxbtc

import example.com.rxbtc.bithumb.json.BithumbStockData
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

data class StockUpdate(var stockSymbol: String,
                       var price: BigDecimal,
                       var date: Date) {

    companion object {
        fun create(stockSymbol: String, data: BithumbStockData) : StockUpdate{
            return StockUpdate(stockSymbol, BigDecimal(data.closingPrice), Date(data.date))
        }
    }

}