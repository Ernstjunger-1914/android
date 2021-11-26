package com.view.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.cert.CertPath
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.FileProvider.getUriForFile as getUriForFile

class MainActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1   // 카메라 사진 촬영 요청코드
    lateinit var curPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 권한을 확인하는 메소드 수행
        setPermission()

        camera_btn.setOnClickListener {
            // 기본 카메라 앱을 실행하여 사진 촬영
            takeCapture()
        }
    }

    /**
     * 카메라 촬영
     */
    private fun takeCapture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex : IOException) {
                    null
                }
                photoFile?.also { 
                    val photoUri: Uri = getUriForFile(this, "com.view.camera.fileprovider", it)

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    /**
     * 이미지 파일 생성
     */
    private fun createImageFile(): File {
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storeageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storeageDir).apply {
            curPhotoPath = absolutePath
        }
    }

    /**
     * Ted Permission 설정
     */
    private fun setPermission() {
        val permission = object : PermissionListener {
            // 설정한 권한이 허용되었을 때 이 메소드를 수행
            override fun onPermissionGranted() {
                Toast.makeText(this@MainActivity, "권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
            }

            // 설정한 권한이 거부되었을 때 이 메소드를 수행
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@MainActivity, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleMessage("카메라 앱을 사용하혀면 권한을 허용하시오.").setDeniedMessage("[앱 설정] -> [권한] 항목에서 권한을 허용하시오.")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
            .check()
    }

    // startActivityForResult 를 통해 기본 카메라 앱으로부터 받아온 사진 결과 값
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 이미지를 가져왔다면
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val bitmap: Bitmap
            val file = File(curPhotoPath)

            // 안드로이드 버전이 9.0보다 낮다면
            if(Build.VERSION.SDK_INT<28) {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(file))
                iv_profile.setImageBitmap(bitmap)
            } else {
                val decode = ImageDecoder.createSource(this.contentResolver, Uri.fromFile(file))

                bitmap = ImageDecoder.decodeBitmap(decode)
                iv_profile.setImageBitmap(bitmap)
            }
            savePhoto(bitmap)
        }
    }

    /**
     * 갤러리에 저장
     */
    private fun savePhoto(bitmap: Any) {
        val dirPath = Environment.getExternalStorageDirectory().absolutePath + "/Pictures/"
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "${timestamp}.jpeg"
        val dir = File(dirPath)
        
        // 해당 경로에 디렉토리가 없다면
        if(!dir.isDirectory) {
            dir.mkdirs()
        }

        // 실제적인 자동처리 로직
        val out = FileOutputStream(dirPath + fileName)

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        Toast.makeText(this, "사진이 앨범이 저장되었습니다.", Toast.LENGTH_SHORT).show()
    }
}