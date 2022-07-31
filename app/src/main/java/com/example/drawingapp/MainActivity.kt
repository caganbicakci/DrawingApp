package com.example.drawingapp

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.drawingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var currentPaintImageButton: ImageButton

    private val openGalleryLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                binding.ivBackground.setImageURI(result.data?.data)
            }
        }

    private var requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value

                if (isGranted) {
                    Toast.makeText(
                        this@MainActivity,
                        "Permission granted now you can access the gallery.",
                        Toast.LENGTH_SHORT
                    ).show()

                    val imagePickerIntent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )

                    openGalleryLauncher.launch(imagePickerIntent)

                } else {
                    if (permissionName == Manifest.permission.READ_EXTERNAL_STORAGE) {
                        Toast.makeText(
                            this@MainActivity,
                            "Permission denied.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.drawingView.setBrushSize(10f)

        binding.brushIb.setOnClickListener {
            showBrushSizeDialog()
        }

        currentPaintImageButton = binding.llSelectColor[1] as ImageButton
        currentPaintImageButton.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.pallet_pressed
            )
        )

        binding.imagePickerIb.setOnClickListener {
            requestStoragePermission()
        }

        binding.undoBtn.setOnClickListener {
            binding.drawingView.undoPaint()
        }

    }

    private fun showBrushSizeDialog() {
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.brush_size_dialog)
        brushDialog.setTitle("Brush size")
        val smallBtn = brushDialog.findViewById<ImageButton>(R.id.small_brush_btn)
        val mediumBtn = brushDialog.findViewById<ImageButton>(R.id.medium_brush_btn)
        val largeBtn = brushDialog.findViewById<ImageButton>(R.id.large_brush_btn)

        smallBtn.setOnClickListener() {
            binding.drawingView.setBrushSize(10f)
            brushDialog.dismiss()
        }

        mediumBtn.setOnClickListener() {
            binding.drawingView.setBrushSize(15f)
            brushDialog.dismiss()
        }

        largeBtn.setOnClickListener() {
            binding.drawingView.setBrushSize(20f)
            brushDialog.dismiss()
        }

        brushDialog.show()
    }

    fun onColorClicked(view: View) {
        if (view != currentPaintImageButton) {
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

    private fun showRationalDialog(
        title: String,
        message: String
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            showRationalDialog("Drawing App", "Drawing App needs to access your external storage")
        } else {
            requestPermission.launch(
                (arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                    // TODO: Add writing external storage permission
                ))
            )
        }
    }

}