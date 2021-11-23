package com.example.lock

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_file_ex.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.jar.Manifest
import android.Manifest.permission

class FileExActivity : AppCompatActivity() {
    val filename = "data_save.txt"
    val MT_PERMISSION_REQUEST = 999
    var granted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_ex)

        checkPermission()

        // 저장 버튼이 클릭되었을 때
        saveButton.setOnClickListener {
            // texField의 현재 text를 가져온다.
            val text = textField.text.toString()

            when {
                // text가 비어있는 경우, 오류 메세지를 보여준다.
                TextUtils.isEmpty(text) -> {
                    Toast.makeText(applicationContext, "텍스트가 비어있습니다.", Toast.LENGTH_LONG).show()
                }

                !isExternalStorageWritable() -> {
                    Toast.makeText(applicationContext, "외부 저장장치가 없습니다.", Toast.LENGTH_LONG).show()
                }

                else -> {
                    saveToExternalCustomDirectory(text)
                    //saveToExternalStorage(text, filename)
                    //saveToInnerStorage(text, filename)
                }
            }
        }

        // 불러오기 버튼이 클릭되었을 때
        loadButton.setOnClickListener {
            try {
                textField.setText(loadFromExternalCustomDirectory())
                //textField.setText(loadFromExternalStorage(filename))
                //textField.setText(loadFromInnerStorage(filename))
            } catch (e: FileNotFoundException) {
                Toast.makeText(applicationContext, "저장된 텍스트가 없습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun saveToInnerStorage(text: String, filename: String) {
        // 내부 저장소의 전달된 파일 이름의 파일 출력 스트림을 가져온다.
        val fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE)

        // 파일 출력 스트림에 text를 Byte로 변환하여 write, 그 후에는 파일 출력 스트림을 닫는다.
        fileOutputStream.write(text.toByteArray())
        fileOutputStream.close()
    }

    fun loadFromInnerStorage(filename: String): String {
        // 내부 저장소의 전달된 파일 이름의 파일 입력 스트림을 가져온다.
        val fileInputStream = openFileInput(filename)

        // 파일의 저장된 내용을 읽어 String 형태로 불러온다.
        return fileInputStream.reader().readText()
    }

    fun isExternalStorageWritable(): Boolean {
        when {
            // 외부 저장장치 상태가 MEDIA_MOUNTED면 사용 가능
            Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED -> return true
            else -> return false
        }
    }

    fun getAppDatFileFromExternalStorage(filename: String): File {
        // KITKAT 버전부터는 앱 전용 디렉토리의 디렉토리 타입 상수인 Environment.DIRECTORY_DOCUMENTS를 지원
        val dir = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        } else {
            // 하위 버전에서는 직접 디렉토리 이름 지정
            File(Environment.getExternalStorageDirectory().absolutePath + "/Documents")
        }
        // 디렉토리 경로 중 없는 디렉토리가 있다면 생성
        dir?.mkdirs()
        return File("${dir!!.absolutePath}${File.separator}${filename}")
    }

    fun saveToExternalStorage(text: String, filename: String) {
        val fileOutputStream = FileOutputStream(getAppDatFileFromExternalStorage(filename))
        fileOutputStream.write(text.toByteArray())
        fileOutputStream.close()
    }

    fun loadFromExternalStorage(filename: String): String {
        return FileInputStream(getAppDatFileFromExternalStorage(filename)).reader().readText()
    }

    fun checkPermission() {
        val permissionCheck = ContextCompat.checkSelfPermission(this@FileExActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        when {
            permissionCheck != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(this@FileExActivity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), MT_PERMISSION_REQUEST)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            MT_PERMISSION_REQUEST -> {
                when {
                    grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                        // 권한 요청 성공
                        granted = true
                    } else -> {
                    // 사용자가 권한을 허용하지 않음
                    granted = false
                }
                }
            }
        }
    }

    fun saveToExternalCustomDirectory(text: String, filepath: String = "/sdcard/data.txt") {
        when {
            // 권한이 있는 경우
            granted -> {
                // 파라미터로 전달받은 경로의 파일의 출력 스트림 객체 생성
                val fileOutputStream = FileOutputStream(File(filepath))

                fileOutputStream.write(text.toByteArray())
                fileOutputStream.close()
            } else -> {
            Toast.makeText(applicationContext, "권한이 없습니다.", Toast.LENGTH_SHORT).show()
        }
        }
    }

    fun loadFromExternalCustomDirectory(filepath: String = "/sdcard/data.txt"): String {
        when {
            granted -> {
                return FileInputStream(File(filepath)).reader().readText()
            } else -> {
            Toast.makeText(applicationContext, "권한이 없습니다.",Toast.LENGTH_SHORT).show()
            return ""
        }
        }
    }
}