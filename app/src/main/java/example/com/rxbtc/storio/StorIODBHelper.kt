package example.com.rxbtc.storio

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



class StorIODBHelper(context: Context):
        SQLiteOpenHelper(context, "reactivestocks.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(StockUpdateTable.createTableQuery())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}