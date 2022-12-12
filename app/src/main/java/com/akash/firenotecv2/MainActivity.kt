package com.akash.firenotecv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akash.firenotecv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnAddNotes.setOnClickListener {
            val intent = Intent(this,NoteInsertActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnShowNotes.setOnClickListener {
            val intent = Intent(this, ShowAllNotesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}