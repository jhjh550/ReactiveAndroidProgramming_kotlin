package example.com.rxbtc.storio

import android.content.ContentValues
import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver
import com.pushtorefresh.storio.sqlite.queries.InsertQuery
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery
import example.com.rxbtc.StockUpdate

class StockUpdatePutResolver: DefaultPutResolver<StockUpdate>() {
    override fun mapToInsertQuery(`object`: StockUpdate): InsertQuery {
        return InsertQuery.builder()
                .table(StockUpdateTable.TABLE)
                .build()
    }

    override fun mapToUpdateQuery(entity: StockUpdate): UpdateQuery {
        return UpdateQuery.builder()
                .table(StockUpdateTable.TABLE)
                .where(StockUpdateTable.ID + " = ? ")
                .whereArgs(entity.id)
                .build()
    }

    override fun mapToContentValues(entity: StockUpdate): ContentValues {
        val values = ContentValues()
        values.put(StockUpdateTable.ID, entity.id)
        values.put(StockUpdateTable.STOCK_SYMBOL, entity.stockSymbol)
        values.put(StockUpdateTable.PRICE, entity.price.toLong())
        values.put(StockUpdateTable.DATE, entity.date.time)

        return values
    }
}