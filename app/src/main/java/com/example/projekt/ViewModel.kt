package com.example.projekt

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val personList = mutableListOf<Person>()

    fun addPerson(person: Person) {
        personList.add(person)
    }

    fun getPersonList(): List<Person> {
        return personList
    }

    fun removePerson(person: Person) {
        personList.remove(person)
    }
}