package dev.abhishekkumar.canvasview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import kotlin.math.abs
import android.graphics.Bitmap
import java.io.FileOutputStream
import java.io.IOException
import java.io.File


private var STROKE_WIDTH = 12f

class CanvasView : View {
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var colorBackground = ResourcesCompat.getColor(resources, R.color.colorBackground, null)

    fun setColorBackground(color: Int) {
        colorBackground = ResourcesCompat.getColor(resources, color, null)
    }

    private var drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)

    fun setColorMarker(color: Int) {
        drawColor = ResourcesCompat.getColor(resources, color, null)
        setPaint()
    }

    fun setStrokeWidth(width: Float) {
        STROKE_WIDTH = width
        setPaint()
    }

    fun getBitmap(): Bitmap {
        return extraBitmap
    }

    fun setBitmap(bitmap: Bitmap) {
        extraBitmap = bitmap
    }

    fun saveAs(format: Bitmap.CompressFormat, quality: Int, destination: File) {
        try {
            val fileOutputStream = FileOutputStream(destination)
            fileOutputStream.use { out ->
                extraBitmap.compress(format, quality, out)

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun clearView() {
        extraCanvas.drawColor(colorBackground)
    }

    private fun setPaint(): Paint {
        return Paint().apply {
            color = drawColor
            isAntiAlias = true
            isDither = true
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = STROKE_WIDTH
        }
    }

    private var path = Path()
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f
    private var currentX = 0f
    private var currentY = 0f

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private fun touchMove() {
        val dx = abs(motionTouchEventX - currentX)
        val dy = abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            extraCanvas.drawPath(path, setPaint())
        }
        invalidate()
    }

    private fun touchUp() {
        path.reset()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        motionTouchEventX = event!!.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true

    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(colorBackground)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawBitmap(extraBitmap, 0f, 0f, null)
    }


}



