package com.example.drawingapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.drawingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var currentPaintImageButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.drawingView.setBrushSize(10f)

        binding.brushIb.setOnClickListener{
            showBrushSizeDialog()
        }

        currentPaintImageButton = binding.llSelectColor[1] as ImageButton
        currentPaintImageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed))

    }

    private fun showBrushSizeDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.brush_size_dialog)
        brushDialog.setTitle("Brush size")
        val smallBtn = brushDialog.findViewById<ImageButton>(R.id.small_brush_btn)
        val mediumBtn = brushDialog.findViewById<ImageButton>(R.id.medium_brush_btn)
        val largeBtn = brushDialog.findViewById<ImageButton>(R.id.large_brush_btn)

        smallBtn.setOnClickListener(){
            binding.drawingView.setBrushSize(10f)
            brushDialog.dismiss()
        }

        mediumBtn.setOnClickListener(){
            binding.drawingView.setBrushSize(15f)
            brushDialog.dismiss()
        }

        largeBtn.setOnClickListener(){
            binding.drawingView.setBrushSize(20f)
            brushDialog.dismiss()
        }

        brushDialog.show()
    }

    fun onColorClicked(view: View){
        if(view != currentPaintImageButton){
            val imageBtn = view as ImageButton
            val colorTag = imageBtn.tag.toString()
            binding.drawingView.setColor(colorTag)

            imageBtn.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
            )

            currentPaintImageButton.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_normal)
            )

            currentPaintImageButton = imageBtn
        }
    }
}