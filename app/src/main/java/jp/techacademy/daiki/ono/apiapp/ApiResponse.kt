package jp.techacademy.daiki.ono.apiapp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiResponse(
    @SerializedName("results")
    var results: Results
):Serializable

data class Results(
    @SerializedName("shop")
    var shop: List<Shop>
):Serializable

data class Shop(
    @SerializedName("id")
    var id: String,
    @SerializedName("coupon_urls")
    var couponUrls: CouponUrls,
    @SerializedName("logo_image")
    var logoImage: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("address")
    var address: String

):Serializable

data class CouponUrls(
    @SerializedName("pc")
    var pc: String,
    @SerializedName("sp")
    var sp: String
):Serializable