package com.kodego.testmobileapp.dialogtest

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kodego.testmobileapp.R
import com.kodego.testmobileapp.databinding.ActivityDialogBinding
import com.kodego.testmobileapp.databinding.CustomAlertDialogBoxBinding

class DialogTestActivity : AppCompatActivity() {

    lateinit var binding: ActivityDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDialogBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnShowCustomDialogBox.setOnClickListener(){
            showCustomAlertDialogBox()
        }

        binding.btnShowBuiltInDialogBox.setOnClickListener(){
            showBuiltInAlertDialogBox()
        }
    }

    private fun showCustomAlertDialogBox() {
        val customDialog : Dialog = Dialog(this)
        var dialogBinding: CustomAlertDialogBoxBinding = CustomAlertDialogBoxBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnDialogYes.setOnClickListener(){
            customDialog.dismiss()
        }

        dialogBinding.btnDialogNo.setOnClickListener(){
            Toast.makeText(applicationContext, "Okay! Enjoy the dialog box!", Toast.LENGTH_SHORT).show()
        }

        customDialog.show()
    }

    private fun showBuiltInAlertDialogBox() {
        AlertDialog.Builder(this).setMessage("Built In Alert Dialog Box")
            .setPositiveButton("Positive Button"){ dialog, item ->
                Toast.makeText(applicationContext, "Positive Button Clicked!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Negative Button"){dialog, item ->
                Toast.makeText(applicationContext, "Negative Button Clicked!", Toast.LENGTH_SHORT).show()
            }.show()
    }
}