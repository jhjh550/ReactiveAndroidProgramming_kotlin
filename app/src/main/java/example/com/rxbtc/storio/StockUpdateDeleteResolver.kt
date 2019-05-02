package example.com.rxbtc.storio

import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery
import example.com.rxbtc.StockUpdate

class StockUpdateDeleteResolver: DefaultDeleteResolver<StockUpdate>() {
    override fun mapToDeleteQuery(entity: StockUpdate): DeleteQuery {
        return DeleteQuery.builder()
                .table(StockUpdateTable.TABLE)
                .where(StockUpdateTable.ID + " = ?")
                .whereArgs(entity.id)
                .build()
    }
}