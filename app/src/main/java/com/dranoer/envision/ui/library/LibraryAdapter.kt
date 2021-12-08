package com.dranoer.envision.ui.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dranoer.envision.R
import com.dranoer.envision.data.model.Paragraph

class LibraryAdapter : ListAdapter<Paragraph, LibraryAdapter.LibraryViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        return LibraryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(
            name = current.paragraph
        )
    }

    class LibraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameItemView: TextView = itemView.findViewById(R.id.name)

        fun bind(name: String?) {
            nameItemView.text = name
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
        private val COMPARATOR = object : DiffUtil.ItemCallback<Paragraph>() {
            override fun areItemsTheSame(oldItem: Paragraph, newItem: Paragraph): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Paragraph, newItem: Paragraph): Boolean {
                return oldItem.paragraph == newItem.paragraph
            }
        }
    }
}