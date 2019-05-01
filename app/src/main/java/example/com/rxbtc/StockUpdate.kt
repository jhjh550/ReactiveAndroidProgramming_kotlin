package example.com.rxbtc

import example.com.rxbtc.bithumb.json.BithumbOneStockResult
import java.math.BigDecimal
import java.util.*

data class StockUpdate(var stockSymbol: String,
                       var price: BigDecimal,
                       var date: Date) {
    var id: Int = 0

    companion object {
        fun create(item: BithumbOneStockResult) : StockUpdate{
            return StockUpdate(
                    "BTC",//item.data.stockSymbol,
                    BigDecimal(item.data.closingPrice),
                    Date(item.data.date))
        }
    }

}