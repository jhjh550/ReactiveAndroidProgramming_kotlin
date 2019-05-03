package example.com.rxbtc

import example.com.rxbtc.bithumb.json.BithumbOneStockResult
import twitter4j.Status
import java.math.BigDecimal
import java.util.*

data class StockUpdate(var stockSymbol: String,
                       var price: BigDecimal,
                       var date: Date,
                       var twitterStatus:String = "") {
    var id: Int = 0

    fun isTwitterStatusUpdate(): Boolean{
        return twitterStatus.isNotEmpty()
    }

    companion object {
        fun create(item: BithumbOneStockResult) : StockUpdate{
            return StockUpdate(
                    "BTC",//item.data.stockSymbol,
                    BigDecimal(item.data.closingPrice),
                    Date(item.data.date))
        }

        fun create(status: Status): StockUpdate {
            return StockUpdate("", BigDecimal.ZERO, status.getCreatedAt(), status.text)
        }
    }

}