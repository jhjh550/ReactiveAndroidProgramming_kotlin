package example.com.rxbtc

import java.io.Serializable
import java.math.BigDecimal
import java.util.*

data class StockUpdate(var stockSymbol: String,
                       var price: Double,
                       var date: Date) {

}