package com.ramazanaltun.calculatemain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.ramazanaltun.calculatemain.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)
        viewBinding.btnCalcul.setOnClickListener {
            Log.d("ramazan","clicked")

        }
    }



}