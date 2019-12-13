package devdon.com.painter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import java.io.OutputStream

class PaintBoard(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var paint: Paint
    private var bitmap: Bitmap
    private var mCanvas: Canvas


    private var startX:Float = 0f
    private var startY:Float = 0f

    init {
        // bitmap
        val width = Resources.getSystem().displayMetrics.widthPixels
        bitmap = Bitmap.createBitmap(width, 800, Bitmap.Config.ARGB_8888)

        // Canvas
        mCanvas = Canvas(bitmap)
        mCanvas.drawColor(Color.WHITE)

        // Paint
        paint = Paint()
        paint.setColor(Color.BLACK)

        paint.setStrokeWidth(30F)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawBitmap(bitmap, 0f, 0f, paint)
    }

    //draw on the canvas
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val stopX = event.x
                val stopY = event.y

                mCanvas.drawLine(startX, startY, stopX, stopY, paint)
                startX = event.x
                startY = event.y

                // call onDraw

                invalidate()
            }
        }
        return true
    }

    //clean the canvas
    fun Clean(): Unit{
        mCanvas.drawColor(Color.WHITE);
        Log.e("touch", "clean.");
    }

    fun pencolorchange(changecolor : String)
    {
        Log.e("string", changecolor);
        paint.setColor(Color.parseColor(changecolor));
    }

    fun pen_size_change(i : Int)
    {
        paint.setStrokeWidth(i.toFloat())
    }

    @SuppressLint("WrongThread")
    fun saveBitmap(stream: OutputStream) {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    }
}