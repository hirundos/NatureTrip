package com.example.dkdus.triptonature.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dkdus.triptonature.R
import kotlinx.android.synthetic.main.activity_license.*
import java.io.InputStream

class LicenseActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)

        try {
            val readin : InputStream = resources.openRawResource(R.raw.license)
            val b = ByteArray(readin.available())
            readin.read(b)
            val s = String(b)
            License_text.text = s
        } catch (e: Exception) {
        }
    }
}