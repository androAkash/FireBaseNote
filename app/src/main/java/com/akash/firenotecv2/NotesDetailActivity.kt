package com.akash.firenotecv2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.akash.firenotecv2.databinding.ActivityNotesDetailBinding
import com.akash.firenotecv2.model.NotesModel
import com.google.firebase.database.FirebaseDatabase
import soup.neumorphism.NeumorphButton
import soup.neumorphism.NeumorphFloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class NotesDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotesDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.imgBtnEdit.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("notesId").toString(),
                intent.getStringExtra("notes").toString(),
            )
        }

        binding.imgBtnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("notesId").toString()
            )
        }

        binding.back.setOnClickListener {
            val intent = Intent(this,ShowAllNotesActivity::class.java)
            startActivity(intent)
            finish()
        }

        setValuesToView()
    }

    private fun setValuesToView() {
        binding.tvNotesList.text = intent.getStringExtra("notes")
        binding.tvNotesDesc.text = intent.getStringExtra("description")
    }

    private fun openUpdateDialog(
        notesId:String,
        notes:String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_notes_dialog,null)

        mDialog.setView(mDialogView)

        val et_updateNotes = mDialogView.findViewById<EditText>(R.id.ed_update_note)
        val et_updateDescription = mDialogView.findViewById<EditText>(R.id.ed_update_description)
        val btnUpdate = mDialogView.findViewById<Button>(R.id.btn_update)
        val btnBack = mDialogView.findViewById<NeumorphFloatingActionButton>(R.id.back2)

        et_updateNotes.setText(intent.getStringExtra("notes").toString())
        et_updateDescription.setText(intent.getStringExtra("description").toString())

        val currentTime:String = SimpleDateFormat("HH:mm a", Locale.getDefault()).format(Date())
        val currentDate:String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        mDialog.setTitle("Updating $notes Notes")


        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener {
            updateNotesData(
                notesId,
                et_updateNotes.text.toString(),
                et_updateDescription.text.toString(),
                currentTime,
                currentDate
            )
            Toast.makeText(applicationContext, "Notes Data Updated", Toast.LENGTH_SHORT).show()

            //settingUp TextViews for Updating
            binding.tvNotesDesc.text = et_updateDescription.text.toString()
            binding.tvNotesList.text = et_updateNotes.text.toString()


            alertDialog.dismiss()
        }

        btnBack.setOnClickListener {
            val intent = Intent(this,ShowAllNotesActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun updateNotesData(
        id:String,
        notesList:String,
        notesDescription:String,
        currentTime:String,
        currentDate:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Notes").child(id)
        val notesInfo = NotesModel(id,notesList,notesDescription,currentTime,currentDate)
        dbRef.setValue(notesInfo)
    }

    private fun deleteRecord(id: String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Notes").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Notes Deleted", Toast.LENGTH_SHORT).show()

            val intent = Intent(this,ShowAllNotesActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener { error->
            Toast.makeText(this, "Deleting Error${error.message}", Toast.LENGTH_SHORT).show()
        }
    }
}