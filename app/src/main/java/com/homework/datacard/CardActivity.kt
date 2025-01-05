package com.homework.datacard

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CardActivity : AppCompatActivity() {

    private lateinit var tvFirstName: TextView
    private lateinit var tvLastName: TextView
    private lateinit var tvAge: TextView
    private lateinit var ivProfileImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        tvFirstName = findViewById(R.id.tvFirstName)
        tvLastName = findViewById(R.id.tvLastName)
        tvAge = findViewById(R.id.tvAge)
        ivProfileImage = findViewById(R.id.ivProfileImage)

        val firstName = intent.getStringExtra("FIRST_NAME")
        val lastName = intent.getStringExtra("LAST_NAME")
        val birthday = intent.getStringExtra("BIRTHDAY")
        val imageUri = intent.getStringExtra("IMAGE_URI")

        tvFirstName.text = firstName
        tvLastName.text = lastName
        ivProfileImage.setImageURI(Uri.parse(imageUri))

        val ageAndNextBirthday = calculateAgeAndBirthday(birthday.toString())
        tvAge.text = ageAndNextBirthday

        // Добавление меню выхода
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_main, null)
    }

    private fun calculateAgeAndBirthday(birthday: String): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val birthDate = sdf.parse(birthday)

        val calendar = Calendar.getInstance()
        val currentDate = calendar.time

        val age = currentDate.year - birthDate.year
        val ageInMonths = currentDate.month - birthDate.month
        val daysUntilNextBirthday = calculateDaysUntilNextBirthday(birthDate)

        return "Возраст: $age лет и осталось $daysUntilNextBirthday дней до дня рождения"
    }

    private fun calculateDaysUntilNextBirthday(birthDate: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = birthDate
        calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))

        if (calendar.time < Date()) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)
        }

        val daysUntilNextBirthday = (calendar.timeInMillis - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)
        return daysUntilNextBirthday.toInt()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}