package com.example.projekt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekt.databinding.ActivityPersonListBinding

class PersonListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonListBinding
    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val personList = SharedPreferencesHelper.loadPersonList(this)

        personAdapter = PersonAdapter(personList) {
            personList.remove(it)
            SharedPreferencesHelper.savePersonList(this, personList)
            personAdapter.notifyDataSetChanged()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this@PersonListActivity)
        binding.recyclerView.adapter = personAdapter
    }
}