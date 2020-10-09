package com.example.dkdus.triptonature.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.ui.LicenseActivity
import kotlinx.android.synthetic.main.fragment_setting.*
import java.io.InputStream


class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        license_btn.setOnClickListener {
            var intent = Intent(context, LicenseActivity::class.java)
            startActivity(intent)
        }
    }
}