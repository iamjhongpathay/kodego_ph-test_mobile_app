package com.kodego.testmobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kodego.testmobileapp.databinding.ActivityMainBinding
import com.kodego.testmobileapp.dialogtest.DialogTestActivity
import com.kodego.testmobileapp.imagetest.ImageTestActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnImageTest.setOnClickListener(){
            var intent = Intent(this, ImageTestActivity::class.java)
            startActivity(intent)
        }

        binding.btnDialogTest.setOnClickListener(){
            var intent = Intent(this, DialogTestActivity::class.java)
            startActivity(intent)
        }
    }
}