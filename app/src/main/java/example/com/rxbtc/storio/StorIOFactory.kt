package example.com.rxbtc.storio

import android.content.Context
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping
import com.pushtorefresh.storio.sqlite.StorIOSQLite
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite
import example.com.rxbtc.StockUpdate

class StorIOFactory {


    companion object {
        private var instance: StorIOSQLite? = null

        fun get(context: Context): StorIOSQLite? {
            if(instance == null){
                val mapper = SQLiteTypeMapping.builder<StockUpdate>()
                        .putResolver(StockUpdatePutResolver())
                        .getResolver(StockUpdateGetResolver())
                        .deleteResolver(StockUpdateDeleteResolver())
                        .build()

                instance = DefaultStorIOSQLite.builder()
                        .sqliteOpenHelper(StorIODBHelper(context))
                        .addTypeMapping(StockUpdate::class.java, mapper)
                        .build()
            }

            return instance
        }
    }
}