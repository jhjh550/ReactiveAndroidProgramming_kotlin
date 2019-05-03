package example.com.rxbtc.storio

import android.database.Cursor
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver
import example.com.rxbtc.StockUpdate
import java.math.BigDecimal
import java.util.*

class StockUpdateGetResolver: DefaultGetResolver<StockUpdate>() {
    override fun mapFromCursor(cursor: Cursor): StockUpdate {
        val id = cursor.getInt( cursor.getColumnIndexOrThrow(StockUpdateTable.ID))
        val dateLong = cursor.getLong( cursor.getColumnIndexOrThrow(StockUpdateTable.DATE))
        val priceLong = cursor.getLong( cursor.getColumnIndexOrThrow(StockUpdateTable.PRICE))
        val stockSymbol = cursor.getString( cursor.getColumnIndexOrThrow(StockUpdateTable.STOCK_SYMBOL))
        val twitterStatus = cursor.getString( cursor.getColumnIndexOrThrow(StockUpdateTable.TWITTER_STATUS))

        val date = Date(dateLong)
        val price = BigDecimal(priceLong)
        val stockUpdate = StockUpdate(stockSymbol, price, date, twitterStatus)
        stockUpdate.id = id

        return stockUpdate
    }
}