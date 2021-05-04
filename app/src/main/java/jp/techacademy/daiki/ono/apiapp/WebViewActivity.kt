package jp.techacademy.daiki.ono.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.recycler_favorite.*

class WebViewActivity: AppCompatActivity(){
    private val viewPagerAdapter by lazy { ViewPagerAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val sitems = intent.getSerializableExtra(KEY_SHOP)
        val fitems = intent.getSerializableExtra(KEY_F_SHOP)
        //var onClickDeleteFavorite: ((Shop) -> Unit)? = null

        var count = 0
        //urlを取得し画面表示
        if (sitems is Shop) {
            val url =
                if (sitems.couponUrls.sp.isNotEmpty()) sitems.couponUrls.sp else sitems.couponUrls.pc
            webView.loadUrl(url)
            val id = sitems.id
            if (FavoriteShop.findBy(id) == null) {
                button1.text = "お気に入りに登録する"
                count = 3
            } else {
                button1.text = "お気に入りを削除する"
                count = 2
            }
        } else if (fitems is FavoriteShop) {
            val url = fitems.url
            webView.loadUrl(url)
            val id = fitems.id
            if (FavoriteShop.findBy(id) == null) {
                button1.text = "お気に入りに登録する"
                count = 3
            } else {
                button1.text = "お気に入りを削除する"
                count = 2
            }
        } else {

        }



        button1.setOnClickListener {
            if (count % 2 == 1) {//お気に入り登録機能
                button1.text = "お気に入りを削除する"
                //favoriteImageView.setImageResource(R.drawable.ic_star)
                if(sitems is Shop){
                    FavoriteShop.insert(FavoriteShop().apply {
                        id = sitems.id
                        name = sitems.name
                        imageUrl = sitems.logoImage
                        url = if (sitems.couponUrls.sp.isNotEmpty()) sitems.couponUrls.sp else sitems.couponUrls.pc
                        address=sitems.address
                    })
                }else if(fitems is FavoriteShop){
                    FavoriteShop.insert(FavoriteShop().apply {
                        id = fitems.id
                        name = fitems.name
                        imageUrl = fitems.imageUrl
                        url = fitems.url
                        address=fitems.address
                    })
                }
            } else {//お気に入り削除機能
                button1.text = "お気に入りを登録する"
                if(sitems is Shop){
                    val id =sitems.id
                    AlertDialog.Builder(this)
                        .setTitle(R.string.delete_favorite_dialog_title)
                        .setMessage(R.string.delete_favorite_dialog_message)
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            //deleteFavorite(id)
                            FavoriteShop.delete(id)
                        }
                        .setNegativeButton(android.R.string.cancel) { _, _ ->}
                        .create()
                        .show()
                }else if(fitems is FavoriteShop){
                    val id =fitems.id
                    AlertDialog.Builder(this)
                        .setTitle(R.string.delete_favorite_dialog_title)
                        .setMessage(R.string.delete_favorite_dialog_message)
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            //deleteFavorite(id)
                            FavoriteShop.delete(id)
                        }
                        .setNegativeButton(android.R.string.cancel) { _, _ ->}
                        .create()
                        .show()

                }else{

                }
               // favoriteImageView.setImageResource(R.drawable.ic_star_border)

            }
            count++
        }
    }



    companion object {
        private const val KEY_F_SHOP = "key_f_shop"
        private const val KEY_SHOP = "key_shop"
        private const val VIEW_PAGER_POSITION_API = 0
        private const val VIEW_PAGER_POSITION_FAVORITE = 1
        fun start(activity: Activity, shop: Shop) {
            //val url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
            //activity.startActivity(Intent.getSerializableExtra(KEY_SHOP,shop))
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_SHOP,shop))
        }
        fun start(activity: Activity,favoriteShop: FavoriteShop) {
            //val url = favoriteShop.url
            activity.startActivity(Intent(activity,WebViewActivity::class.java).putExtra(KEY_F_SHOP,favoriteShop))
        }
    }


}