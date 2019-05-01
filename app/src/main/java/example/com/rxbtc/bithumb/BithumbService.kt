package example.com.rxbtc.bithumb

import example.com.rxbtc.bithumb.json.BithumbOneStockResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface BithumbService {

    // https://api.bithumb.com/public/ticker/BTC
    @GET("ticker/{currency}")
    fun getTicker(@Path("currency") currency: String) : Single<BithumbOneStockResult>

}