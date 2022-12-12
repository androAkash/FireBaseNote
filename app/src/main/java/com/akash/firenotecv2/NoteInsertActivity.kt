package com.akash.firenotecv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.akash.firenotecv2.databinding.ActivityNoteInsertBinding
import com.akash.firenotecv2.model.NotesModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class NoteInsertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteInsertBinding
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteInsertBinding.inflate(layoutInflater)

        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Notes")

        binding.btnSave.setOnClickListener {
            saveNotesToDatabase()
        }
        binding.back.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveNotesToDatabase(){
        binding.edNote.text.toString()
        binding.edDescription.text.toString()
        val currentTime:String = SimpleDateFormat("HH:mm a", Locale.getDefault()).format(Date())
        val currentDate:String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        if (binding.edNote.text.toString().isEmpty()){
            binding.edNote.error = "Please fill up the space"
        }
        if (binding.edDescription.text.toString().isEmpty()){
            binding.edDescription.error = "Please fill up the space"
        }

        val notesId = dbRef.push().key!!

        val notes = NotesModel(notesId,binding.edNote.text.toString(),binding.edDescription.text.toString(),currentTime,currentDate)

        dbRef.child(notesId).setValue(notes)
            .addOnCompleteListener {
                Toast.makeText(this, "Note inserted successfully", Toast.LENGTH_SHORT).show()

                binding.edNote.text.clear()
                binding.edDescription.text.clear()

            }.addOnFailureListener { err->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
}