package com.dranoer.envision.ui.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dranoer.envision.R
import com.dranoer.envision.data.model.OcrEntity

class LibraryAdapter : ListAdapter<OcrEntity, LibraryAdapter.LibraryViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        return LibraryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(
            date = current.date,
        )
    }

    class LibraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val dateItemView: TextView = itemView.findViewById(R.id.dateText)

        fun bind(date: String) {
            dateItemView.text = date
        }

        companion object {
            fun create(parent: ViewGroup): LibraryViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return LibraryViewHolder(view)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<OcrEntity>() {
            override fun areItemsTheSame(oldItem: OcrEntity, newItem: OcrEntity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: OcrEntity, newItem: OcrEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}