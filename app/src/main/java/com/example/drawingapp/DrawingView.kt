package com.example.drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet ) : View(context, attrs) {

    private lateinit var myDrawPath: CustomPath
    private lateinit var myCanvasBitmap: Bitmap
    private lateinit var myDrawPaint: Paint
    private lateinit var myCanvasPaint: Paint
    private lateinit var myCanvas: Canvas

    private var myBrushSize: Float = 0f
    private var myColor = Color.BLACK

    init {
        setUpDrawing()
    }

    private fun setUpDrawing() {
        myDrawPaint = Paint()
        myDrawPath = CustomPath(myColor, myBrushSize)

        myDrawPaint.color = myColor
        myDrawPaint.style = Paint.Style.STROKE
        myDrawPaint.strokeJoin = Paint.Join.ROUND
        myDrawPaint.strokeCap = Paint.Cap.ROUND

        myCanvasPaint = Paint(Paint.DITHER_FLAG)

        myBrushSize = 20f

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        myCanvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        myCanvas = Canvas(myCanvasBitmap)
    }

    // TODO: change Canvas to Canvas? in constructor if its fails
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(myCanvasBitmap,0f,0f,myCanvasPaint)

        myDrawPaint.strokeWidth = myDrawPath.brushTickness
        myDrawPaint.color = myDrawPath.color

        canvas?.drawPath(myDrawPath, myDrawPaint)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val touchX = event?.x
        val touchY = event?.y

        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                myDrawPath.color = myColor
                myDrawPath.brushTickness = myBrushSize

                myDrawPath.reset()
                myDrawPath.moveTo(touchX!! , touchY!!)
            }
            MotionEvent.ACTION_MOVE -> {
                myDrawPath.lineTo(touchX!!, touchY!!)
            }

            MotionEvent.ACTION_UP ->{
                myDrawPath = CustomPath(myColor,myBrushSize)
            }
            else -> return false
            }

            invalidate()
            return true
    }

    inner class CustomPath(var color: Int, var brushTickness: Float) : Path()

}