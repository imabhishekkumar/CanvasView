package dev.abhishekkumar.canvasview

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Environment.getExternalStorageDirectory
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Environment
import java.io.File


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val canvasView = findViewById<CanvasView>(R.id.canvasView)
        canvasView.setColorBackground(R.color.colorPrimary)
        canvasView.setColorMarker(R.color.colorAccent)
        canvasView.setStrokeWidth(12f)
        clear.setOnClickListener {
            canvasView.clearView()
        }
        saveBtn.setOnClickListener {
            val filename = "test.png"
            val sd = getExternalStorageDirectory()
            val dest = File(sd, filename)
            canvasView.saveAs(Bitmap.CompressFormat.PNG, 90, dest)
        }

        setImage.setOnClickListener {
            imageView.setImageBitmap(canvasView.getBitmap())
        }

    }
}
