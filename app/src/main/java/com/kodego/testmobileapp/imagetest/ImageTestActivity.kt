package com.kodego.testmobileapp.imagetest

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.kodego.testmobileapp.R
import com.kodego.testmobileapp.databinding.ActivityImageTestBinding
import java.util.jar.Manifest

class ImageTestActivity : AppCompatActivity() {
    lateinit var binding: ActivityImageTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityImageTestBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener(){
            showCamera()
        }

        binding.btnGallery.setOnClickListener(){
            showGallery()
        }

        binding.btnDisplayImage.setOnClickListener(){
            displayWebImage()
        }

        binding.imgbSetImage.setOnClickListener(){
            showDataSourceChoicesDialog()
        }


    }



    //handling permission of camera
    private fun showCamera() {
        Dexter.withContext(this).withPermission(
            android.Manifest.permission.CAMERA
        ).withListener(object: PermissionListener{
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //accessing the camera
                cameraLauncher.launch(cameraIntent) //camera will show and captureing the image
//                startActivity(cameraIntent) //camera will show after pressing the camera button
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(applicationContext, "Camera Access Denied", Toast.LENGTH_SHORT).show()
                gotoSettings()
            }

            override fun onPermissionRationaleShouldBeShown(
                request: PermissionRequest?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }

        }).onSameThread().check()
    }

    //handling permission of gallery/external storage
    private fun showGallery() {
        Dexter.withContext(this).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object: PermissionListener{
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)//accessing the gallery and selecting the path of image
                galleryLauncher.launch(galleryIntent)
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(applicationContext, "Gallery Access Denied", Toast.LENGTH_SHORT).show()
                gotoSettings()
            }

            override fun onPermissionRationaleShouldBeShown(
                request: PermissionRequest?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }

        }).onSameThread().check()
    }

    private fun displayWebImage() {

        //Image from Web
        Glide.with(this)
            .load(binding.etImageAddress.text.toString())
            .into(binding.ivDisplayPhoto)
    }

    private fun showDataSourceChoicesDialog() {
        AlertDialog.Builder(this).setMessage("Choose data source of image.")
            .setPositiveButton("Camera"){ dialog, item ->
                showCamera()
            }
            .setNegativeButton("Gallery"){ dialog, item ->
                showGallery()
            }.show()
    }

    //if the user denied the permission, this function will show a dialog to enable the permission manually in phone Settings.
    private fun gotoSettings() {
        AlertDialog.Builder(this).setMessage("it seems your permission has been denied. Go to settings to enable permission.")
            .setPositiveButton("Go to Settings"){ dialog, item ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                var uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel"){ dialog, item ->
                dialog.dismiss()
            }.show()
    }

    //handles images from camera
    val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            result.data?.extras.let{
                val image : Bitmap = result.data?.extras?.get("data") as Bitmap
                binding.ivDisplayPhoto.setImageBitmap(image) //the image that captured will display to image view
            }
        }
    }

    //handles images from external storage/gallery
    val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            result.data?.let {
                val selectedImage = result.data?.data
                binding.ivDisplayPhoto.setImageURI(selectedImage) //the image that selected from external storage/gallery will display to image view
            }
        }

    }
}