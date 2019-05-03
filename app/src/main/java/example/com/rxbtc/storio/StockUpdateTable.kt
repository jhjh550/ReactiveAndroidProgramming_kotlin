package example.com.rxbtc.storio

import android.util.Log

class StockUpdateTable {

    companion object {
        val TABLE = "stock_updates"
        val ID = "_id"
        val STOCK_SYMBOL = "stock_symbol"
        val PRICE = "price"
        val DATE = "date"
        val TWITTER_STATUS = "twitter_status"


        fun createTableQuery(): String{
            val str = "create table "+ TABLE+" ("+
                    ID+" integer primary key autoincrement, "+
                    STOCK_SYMBOL+" text not null, "+
                    PRICE+" long not null, "+
                    DATE+" long not null, " +
                    TWITTER_STATUS + " text null)";

            Log.d("query", str)
            return str
        }
    }


}