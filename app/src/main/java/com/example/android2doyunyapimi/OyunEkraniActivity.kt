package com.example.android2doyunyapimi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android2doyunyapimi.databinding.ActivityOyunEkraniBinding
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.floor


class OyunEkraniActivity : AppCompatActivity() {

    // Pozisyonlar
    private var anakarakterX = 0.0f
    private var anakarakterY = 0.0f
    private var siyahkareX = 0.0f
    private var siyahkareY = 0.0f
    private var sariDaireX = 0.0f
    private var sariDaireY = 0.0f
    private var kirmiziUcgenX = 0.0f
    private var kirmiziUcgenY = 0.0f

    // Boyutlar
    private var ekranGenisligi = 0
    private var ekranYuksekligi = 0
    private var anakarakterGenisligi = 0
    private var anakarakterYuksekligi = 0


    // Kontroller
    private var dokunmaKontrol = false
    private var baslangicKontrol = false

    private val timer = Timer()

    private var skor = 0
    private lateinit var tasarim: ActivityOyunEkraniBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasarim = DataBindingUtil.setContentView(this,R.layout.activity_oyun_ekrani)

        tasarim.siyahkare.x = -800.0f
        tasarim.siyahkare.y = -800.0f
        tasarim.saridaire.x = -800.0f
        tasarim.saridaire.y = -800.0f
        tasarim.kirmiziucgen.x = -800.0f
        tasarim.kirmiziucgen.y = -800.0f

        // nesneleri uygulama ilk çalıştığında dışarı aldım.

        tasarim.cl.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(p0: View?, event: MotionEvent?): Boolean {

                /*Log.e("Yükseklik2",(tasarim.cl.height).toString())
                Log.e("Genişlik2",(tasarim.cl.width).toString())
                // tasarım ile ilgili yükseklik ve genişlik bilgilerini
                // onTouch metodu içerisinde öğrenebiliriz. */


                if (baslangicKontrol){
                    if (event?.action == MotionEvent.ACTION_DOWN){
                        // "ACTION_DOWN" ekrana dokunduğumuzu tespit edebildiğimiz
                        // yapı.
                        Log.e("MotionEvent","ACTION_DOWN : Ekrana dokundu.")
                        dokunmaKontrol = true
                    }
                    if (event?.action == MotionEvent.ACTION_UP){
                        // "ACTION_DOWN" ekranı bıraktığımızı tespit edebildiğimiz
                        // yapı.
                        Log.e("MotionEvent","ACTION_UP : Ekranı bıraktı.")
                        dokunmaKontrol = false
                    }
                }else{

                    baslangicKontrol = true

                    tasarim.textViewOyunBasla.visibility = View.INVISIBLE
                    // oyun başadığında yazı gider.

                    anakarakterX = tasarim.anakarakter.x
                    anakarakterY = tasarim.anakarakter.y
                    // ekrana dokunduğum anda anakrakterin x ve y değerlerini aldım.

                    anakarakterGenisligi = tasarim.anakarakter.width
                    anakarakterYuksekligi = tasarim.anakarakter.height
                    // ekrana dokunduğum anda anakrakterin h ve w değerlerini aldım.

                    ekranGenisligi = tasarim.cl.width
                    ekranYuksekligi = tasarim.cl.height
                    // ekranın boyutlarını aldım.

                    timer.schedule(0,20){
                        // 0 değeri gecikmedir.
                        // 30 ise hangi aralıklarla çalışacağını belirttiğimiz yer.

                        Handler(Looper.getMainLooper()).post {
                            // android sayfası üzerindeki görsel nesneler üzerinde
                            // değişiklik yapmamızı sağlıyor.
                            // timer bir kere çalışır ama burası her zaman çalışır.

                            anakarakterHareketEttirme()
                            cisimleriHareketEttirme()
                            carpismaKontrol()
                        }
                    }
                }
                return true
            }
            // ekrana dokunuldu mu bırakıldı mı bunu anlarız.
            // cl constraitlaouttır.
        })
    }

    fun anakarakterHareketEttirme(){

        val anakarakterHiz = ekranYuksekligi/60.0f
        // ekran yüksekliğine göre oranlarım.

        if (dokunmaKontrol){
            anakarakterY -= anakarakterHiz
            // yukarı doğru gider.
        }else{
            anakarakterY += anakarakterHiz
            // aşağı doğru gider.
        }

        if (anakarakterY<= 0.0f){
            anakarakterY = 0.0f
        }

        if (anakarakterY >= ekranYuksekligi - anakarakterYuksekligi){
            anakarakterY = (ekranYuksekligi - anakarakterYuksekligi).toFloat()
        }
        // ana karakterin ekranın dışına taşmasını engellerim.

        tasarim.anakarakter.y = anakarakterY
        // artışı ve azalışı görmüş oluruz.
    }

    fun cisimleriHareketEttirme(){
        siyahkareX -= ekranGenisligi/44.0f
        sariDaireX -= ekranGenisligi/54.0f
        kirmiziUcgenX -= ekranGenisligi/36.0f
        // cismin ne kadar hızlı gittiğini burdan ayarlayabiliriz.
        if (siyahkareX < 0.0f){
            siyahkareX = ekranGenisligi + 20.0f
            // siyah karenin ekranın dışına taşmasını engellerim.
            siyahkareY = floor(Math.random()*ekranYuksekligi).toFloat()
            // ekran yüksekliği arasında rastgele bir değer üretir.
        }
        tasarim.siyahkare.x = siyahkareX
        tasarim.siyahkare.y = siyahkareY
        // yeni konumlarını girerim siyah karenin.

        if (sariDaireX < 0.0f){
            sariDaireX = ekranGenisligi + 20.0f
            sariDaireY = floor(Math.random()*ekranYuksekligi).toFloat()
        }
        tasarim.saridaire.x = sariDaireX
        tasarim.saridaire.y = sariDaireY

        if (kirmiziUcgenX < 0.0f){
            kirmiziUcgenX = ekranGenisligi + 20.0f
            kirmiziUcgenY = floor(Math.random()*ekranYuksekligi).toFloat()
        }
        tasarim.kirmiziucgen.x = kirmiziUcgenX
        tasarim.kirmiziucgen.y = kirmiziUcgenY
    }

    fun carpismaKontrol(){
        val saridaireMerkezX = sariDaireX + tasarim.saridaire.width/2.0f
        val saridaireMerkezY = sariDaireY + tasarim.saridaire.height/2.0f

        if (0.0f <= saridaireMerkezX && saridaireMerkezX <= anakarakterGenisligi
            && anakarakterY <= saridaireMerkezY
            && saridaireMerkezY <= anakarakterY + anakarakterYuksekligi){

            skor += 20
            sariDaireX = -10.0f
            // saridaire ana karaktere çarptığı zaman ekranın hemen dışına alırım.
        }

        val kirmiziucgenMerkezX = kirmiziUcgenX + tasarim.kirmiziucgen.width/2.0f
        val kirmiziucgenMerkezY = kirmiziUcgenY + tasarim.kirmiziucgen.height/2.0f

        if (0.0f <= kirmiziucgenMerkezX && kirmiziucgenMerkezX <= anakarakterGenisligi
            && anakarakterY <= kirmiziucgenMerkezY
            && kirmiziucgenMerkezY <= anakarakterY + anakarakterYuksekligi){

            skor += 50
            kirmiziUcgenX = -10.0f
            // kırmızı üçgen ana karaktere çarptığı zaman ekranın hemen dışına alırım.
        }

        val siyahkareMerkezX = siyahkareX + tasarim.siyahkare.width/2.0f
        val siyahkareMerkezY = siyahkareY + tasarim.siyahkare.height/2.0f

        if (0.0f <= siyahkareMerkezX && siyahkareMerkezX <= anakarakterGenisligi
            && anakarakterY <= siyahkareMerkezY
            && siyahkareMerkezY <= anakarakterY + anakarakterYuksekligi){

            siyahkareX = -10.0f
            // siyah kare ana karaktere çarptığı zaman ekranın hemen dışına alırım.

            timer.cancel()
            // siyah kare çarptığı zaman oyunu durdururum.

            val intent = Intent(this@OyunEkraniActivity,SonucEkraniActivity::class.java)
            intent.putExtra("skor",skor)
            startActivity(intent)
            finish()
        }
        tasarim.textViewSkor.text = skor.toString()
    }
}