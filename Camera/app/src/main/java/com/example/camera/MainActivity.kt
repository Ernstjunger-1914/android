package com.example.camera

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99
    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_STORAGE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setViews() {
        camera.setOnClickListener {
            openCamera()
        }

        gallery.setOnClickListener {
            openGallery()
        }
    }

    fun openCamera() {
        if(checkPermission(CAMERA_PERMISSION, FLAG_PERM_CAMERA)) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            startActivityForResult(intent, FLAG_REQ_CAMERA)
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)

        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, FLAG_REQ_STORAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                FLAG_REQ_CAMERA -> {
                    if(data?.extras?.get("data") != null) {
                        val bitmap = data?.extras?.get("data") as Bitmap
                        val uri = saveImageFile(newFileName(), "image/jpg", bitmap)
                    }
                }
            }
        }
    }

    fun saveImageFile(filename: String, mimeType: String, bitmap: Bitmap) : Uri? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        try {
            if(uri != null) {
                var descriptor = contentResolver.openFileDescriptor(uri, "w")

                if(descriptor != null) {
                    val fos = FileOutputStream(descriptor.fileDescriptor)

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.close()

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        values.clear()
                        values.put(MediaStore.Images.Media.IS_PENDING, 0)
                        contentResolver.update(uri, values, null, null)
                    }
                }
            }
        } catch (e:java.lang.Exception) {
            Log.e("File", "error = ${e.localizedMessage}")
        }
        return uri
    }

    fun newFileName() : String {
        val sdf = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
        val filename = sdf.format(System.currentTimeMillis())

        return "$filename.jpg"
    }

    fun checkPermission(permissions: Array<out String>, flag: Int): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for(permassion in permissions) {
                if(ContextCompat.checkSelfPermission(this, permassion) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, flag)

                    return false
                }
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            FLAG_PERM_STORAGE -> {
                for(grant in grantResults) {
                    if(grant != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "저장소 권한을 승인하지 않으면 앱을 사용할 수 없습니다.", Toast.LENGTH_LONG).show()

                        finish()
                        return
                    }
                }
                setViews()
            }

            FLAG_PERM_CAMERA -> {
                for(grant in grantResults) {
                    if(grant != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "카메라 권한을 승인하지 않으면 앱을 사용할 수 없습니다.", Toast.LENGTH_LONG).show()

                        return
                    }
                }
                openCamera()
            }
        }
    }
}