package com.homework.datacard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

        private lateinit var etFirstName: EditText
        private lateinit var etLastName: EditText
        private lateinit var etBirthday: EditText
        private lateinit var ivProfileImage: ImageView
        private lateinit var btnSelectImage: Button
        private lateinit var btnSave: Button

        private val PICK_IMAGE = 1
        private var selectedImageUri: Uri? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_main)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            etFirstName = findViewById(R.id.etFirstName)
            etLastName = findViewById(R.id.etLastName)
            etBirthday = findViewById(R.id.etBirthday)
            ivProfileImage = findViewById(R.id.ivProfileImage)
            btnSelectImage = findViewById(R.id.btnSelectImage)
            btnSave = findViewById(R.id.btnSave)

            btnSelectImage.setOnClickListener {
                openGallery()
            }

            btnSave.setOnClickListener {
                saveData()
            }
        }

        private fun openGallery() {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
                selectedImageUri = data?.data
                ivProfileImage.setImageURI(selectedImageUri)
            }
        }

        private fun saveData() {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val birthday = etBirthday.text.toString()

            val intent = Intent(this, CardActivity::class.java).apply {
                putExtra("FIRST_NAME", firstName)
                putExtra("LAST_NAME", lastName)
                putExtra("BIRTHDAY", birthday)
                putExtra("IMAGE_URI", selectedImageUri.toString())
            }
            startActivity(intent)
        }

}