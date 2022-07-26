package com.example.drawingapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.drawingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.drawingView.setBrushSize(10f)

        binding.brushIb.setOnClickListener{
            showBrushSizeDialog()
        }

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
}