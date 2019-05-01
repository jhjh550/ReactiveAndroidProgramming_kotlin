package example.com.rxbtc.bithumb.json

import com.google.gson.annotations.SerializedName

data class BithumbStockData(
        @SerializedName("opening_price")
        val openingPrice: String,
        @SerializedName("closing_price")
        val closingPrice: String,
        @SerializedName("min_price")
        val minPrice: String,
        @SerializedName("max_price")
        val maxPrice: String,
        @SerializedName("average_price")
        val averagePrice: String,
        @SerializedName("units_traded")
        val unitsTraded: String,
        @SerializedName("volume_1day")
        val volume1day: String,
        @SerializedName("volume_7day")
        val volume7day: String,
        @SerializedName("buy_price")
        val buyPrice: String,
        @SerializedName("sell_price")
        val sellPrice: String,
        @SerializedName("24H_fluctate")
        val hFluctate: String,
        @SerializedName("24H_fluctate_rate")
        val hFluctateRate: String,
        val date: Long
)