package com.akash.firenotecv2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akash.firenotecv2.R
import com.akash.firenotecv2.databinding.NotesListBinding
import com.akash.firenotecv2.model.NotesModel

class ShowNoteListAdapter(private val notesList: ArrayList<NotesModel>) :
    RecyclerView.Adapter<ShowNoteListAdapter.NotesViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.notes_list, parent, false)
        return NotesViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.binding.tvNotesList.text = currentNote.notes
        holder.binding.tvNotesDesc.text = currentNote.description
        holder.binding.currentTime.text = currentNote.currentTime
        holder.binding.currentDate.text = currentNote.currentDate
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class NotesViewHolder(itemView: View, clickListener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        var binding = NotesListBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}