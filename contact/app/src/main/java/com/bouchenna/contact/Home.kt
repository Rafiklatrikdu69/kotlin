package com.bouchenna.contact


import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import com.google.android.material.textfield.TextInputEditText
const val EXTRA_MESSAGE = ".MESSAGE"

class Home : AppCompatActivity() {
    private lateinit var button: Button
//Main Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        button = findViewById(R.id.button)
        val firstName ="Prenom"

        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, firstName)
            }
            startActivity(intent)

        }
    }
}