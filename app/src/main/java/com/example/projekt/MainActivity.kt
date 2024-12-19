package com.example.projekt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projekt.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Person(val firstName: String, val lastName: String, val age: Int, val height: Int, val weight: Int)

object SharedPreferencesHelper {
    private const val PREFS_NAME = "person_prefs"
    private const val PERSON_LIST_KEY = "person_list"

    fun savePersonList(context: Context, personList: List<Person>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = Gson().toJson(personList)
        editor.putString(PERSON_LIST_KEY, json)
        editor.apply()
    }

    fun loadPersonList(context: Context): MutableList<Person> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(PERSON_LIST_KEY, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<Person>>() {}.type
        return Gson().fromJson(json, type)
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val age = binding.etAge.text.toString().toIntOrNull()
            val height = binding.etHeight.text.toString().toIntOrNull()
            val weight = binding.etWeight.text.toString().toIntOrNull()

            if (validateInputs(firstName, lastName, age, height, weight)) {
                val person = Person(firstName, lastName, age!!, height!!, weight!!)
                val personList = SharedPreferencesHelper.loadPersonList(this)
                personList.add(person)
                SharedPreferencesHelper.savePersonList(this, personList)
                Toast.makeText(this, "Person saved successfully!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnShowList.setOnClickListener {
            val intent = Intent(this, PersonListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInputs(firstName: String, lastName: String, age: Int?, height: Int?, weight: Int?): Boolean {
        when {
            firstName.isEmpty() || lastName.isEmpty() -> showToast("First and last name are required")
            age == null || age <= 0 -> showToast("Age must be a positive number")
            height == null || height !in 50..250 -> showToast("Height must be between 50 and 250 cm")
            weight == null || weight !in 3..200 -> showToast("Weight must be between 3 and 200 kg")
            else -> return true
        }
        return false
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}