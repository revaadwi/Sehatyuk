package com.app.sehatyuk

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class HomeActivity : AppCompatActivity() {

    private lateinit var adapter : MenuAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var menuArrayList : ArrayList<Menu>

    private lateinit var icon : Array<Int>
    private lateinit var title : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        dataInitiaize()
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView = findViewById(R.id.rvHome)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)  
        adapter = MenuAdapter(menuArrayList)
        recyclerView.adapter = adapter

        val cardCheckin = findViewById<CardView>(R.id.check_in_info_2)
        val cardCheckout = findViewById<CardView>(R.id.check_in_info)
        val btnCheckin = findViewById<AppCompatButton>(R.id.btn_check_in)
        val btnCheckout = findViewById<CardView>(R.id.btn_check_out)

        btnCheckin.setOnClickListener {
//            cardCheckin.visibility = CardView.GONE
//            cardCheckout.visibility = CardView.VISIBLE

            barcodeLauncher.launch(ScanOptions())
        }


        btnCheckout.setOnClickListener {
            cardCheckin.visibility = CardView.VISIBLE
            cardCheckout.visibility = CardView.GONE
        }
    }

    private fun dataInitiaize() {
        menuArrayList = arrayListOf()

        icon = arrayOf(
            R.drawable.ic_sertifikat,
            R.drawable.ic_hasil_tes,
            R.drawable.ic_ehac,
            R.drawable.ic_riwayat,
            R.drawable.ic_riwayat_perjalanan,
            R.drawable.ic_teledoketer,
            R.drawable.ic_layanan,
            R.drawable.ic_stastitik,
            R.drawable.ic_daftar_vaksin,
        )

        title = arrayOf(
            "Sertifikat Vaksin",
            "Hasil Tes COVID-19",
            "EHAC",
            "Riwayat Check-In",
            "Aturan Perjalanan",
            "Teledokter",
            "Pelayanan Kesehatan",
            "Statistik COVID-19",
            "Daftar Vaksin"
        )

        for (i in icon.indices){
            val menu = Menu(icon[i], title[i])
            menuArrayList.add(menu)
        }
    }

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents != null) {
            Intent(this, WebViewActivity::class.java).run{
                putExtra("url", result.contents)
                startActivity(this)
            }
        }
    }
}