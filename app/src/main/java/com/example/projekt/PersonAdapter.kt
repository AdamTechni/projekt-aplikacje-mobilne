package com.example.projekt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.databinding.ItemPersonBinding

class PersonAdapter(private val personList: MutableList<Person>, private val onDeleteClick: (Person) -> Unit) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    inner class PersonViewHolder(val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.tvName.text = "${person.firstName} ${person.lastName}"
            binding.tvDetails.text = "Age: ${person.age}, Height: ${person.height} cm, Weight: ${person.weight} kg"
            binding.btnDelete.setOnClickListener { onDeleteClick(person) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(personList[position])
    }

    override fun getItemCount(): Int = personList.size
}