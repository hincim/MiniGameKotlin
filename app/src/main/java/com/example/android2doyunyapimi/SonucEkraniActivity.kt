package com.example.android2doyunyapimi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.android2doyunyapimi.databinding.ActivitySonucEkraniBinding

class SonucEkraniActivity : AppCompatActivity() {

    private lateinit var tasarim: ActivitySonucEkraniBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasarim = DataBindingUtil.setContentView(this,R.layout.activity_sonuc_ekrani)

        val skor = intent.getIntExtra("skor",0)
        // veri göndermede bir sıkıntı olursa uygulama çökmemesi için varsayılan
        // olarak değeri 0 veririm.
        tasarim.textViewToplamSkor.text = skor.toString()

        val sp = getSharedPreferences("Sonuc",Context.MODE_PRIVATE)
        // "MODE_PRIVATE" önceliği ile kayıt olacak.
        // Sonuc ismiyle kayıt edilicek.
        val enYuksekSkor = sp.getInt("enYuksekSkor",0)
        // OKUMA İŞLEMİNDE BİR SORUN OLURSA 0 EN YÜKSEK SKORUN İÇİNE AKTARILACAKTIR.

        if (skor > enYuksekSkor){
            val editor = sp.edit()
            editor.putInt("enYuksekSkor",skor)
            // kayıt edildi.
            editor.commit()
            // kayıt çalışması için.

            tasarim.textViewEnYuksekSkor.text = skor.toString()
        }else{
            tasarim.textViewEnYuksekSkor.text = enYuksekSkor.toString()
        }

        tasarim.buttonTekrarDene.setOnClickListener {
            startActivity(Intent(this@SonucEkraniActivity,MainActivity::class.java))
        }
    }
}