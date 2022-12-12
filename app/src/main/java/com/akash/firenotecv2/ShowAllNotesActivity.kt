package com.akash.firenotecv2


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.firenotecv2.adapters.ShowNoteListAdapter
import com.akash.firenotecv2.databinding.ActivityShowAllNotesBinding
import com.akash.firenotecv2.model.NotesModel
import com.google.firebase.database.*

class ShowAllNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowAllNotesBinding
    private lateinit var notesList: ArrayList<NotesModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowAllNotesBinding.inflate(layoutInflater)

        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.setHasFixedSize(true)

        setContentView(binding.root)

        notesList = arrayListOf<NotesModel>()

        getNotes()

        binding.btnBackAllNotes.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun getNotes() {
        dbRef = FirebaseDatabase.getInstance().getReference("Notes")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notesList.clear()
                if (snapshot.exists()) {
                    for (noteSnap in snapshot.children){
                        val noteData = noteSnap.getValue(NotesModel::class.java)
                        notesList.add(noteData!!)
                    }
                    val notesAdapter = ShowNoteListAdapter(notesList)
                    binding.rvNotes.adapter = notesAdapter

                    notesAdapter.setOnItemClickListener(object : ShowNoteListAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@ShowAllNotesActivity, NotesDetailActivity::class.java)

                            //putExtra
                            intent.putExtra("notesId",notesList[position].notesId)
                            intent.putExtra("notes",notesList[position].notes)
                            intent.putExtra("description",notesList[position].description)
                            intent.putExtra("currentTime",notesList[position].currentTime)
                            startActivity(intent)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}