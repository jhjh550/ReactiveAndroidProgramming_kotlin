package example.com.rxbtc.storio

import android.content.Context
import android.database.Cursor
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping
import com.pushtorefresh.storio.sqlite.StorIOSQLite
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite
import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver
import com.pushtorefresh.storio.sqlite.operations.get.GetResolver
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery
import example.com.rxbtc.StockUpdate

class StorIOFactory {


    companion object {
        private var instance: StorIOSQLite? = null

        fun get(context: Context): StorIOSQLite? {
            if(instance == null){
                val mapper = SQLiteTypeMapping.builder<StockUpdate>()
                        .putResolver(StockUpdatePutResolver())
                        .getResolver(createGetResolver())
                        .deleteResolver(createDeleteResolver())
                        .build()

                instance = DefaultStorIOSQLite.builder()
                        .sqliteOpenHelper(StorIODBHelper(context))
                        .addTypeMapping(StockUpdate::class.java, mapper)
                        .build()
            }

            return instance
        }

        private fun createGetResolver(): GetResolver<StockUpdate> {
            return object: DefaultGetResolver<StockUpdate>(){
                override fun mapFromCursor(cursor: Cursor): StockUpdate {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
        }

        private fun createDeleteResolver(): DefaultDeleteResolver<StockUpdate>{
            return object: DefaultDeleteResolver<StockUpdate>(){
                override fun mapToDeleteQuery(`object`: StockUpdate): DeleteQuery {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
        }
    }
}