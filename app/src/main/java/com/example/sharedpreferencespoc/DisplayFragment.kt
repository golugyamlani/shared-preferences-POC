package com.example.sharedpreferencespoc

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sharedpreferencespoc.databinding.FragmentDisplayBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList

class DisplayFragment : Fragment() {

    var nameList = ArrayList<String>()
    var myList = ArrayList<String>()

    private var _binding: FragmentDisplayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDisplayBinding.inflate(inflater,container,false)
        val view =  binding.root

        binding.btnSubmit.setOnClickListener{
            //saveData()
            val name:String = binding.etName.text.toString()
            nameList.add(name)
            saveArrayList(nameList,"name")
            myList = getArrayList("name")
            println(myList)
            binding.tvDisplay.text = myList.toString()
        }

        return view
    }

    private fun saveData() {
        val name:String = binding.etName.text.toString()
        val sharedPref = activity?.getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.apply{
            putString("name",name)
        }?.apply()
        val savedName: String? = sharedPref?.getString("name",null)
        binding.tvDisplay.text = savedName
    }

    fun saveArrayList(list: ArrayList<String>, key: String) {
        val sharedPref = activity?.getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor?.putString(key, json)
        editor?.apply()
        println("Successful Save")
    }

    fun getArrayList(key: String?): ArrayList<String> {
        val sharedPref = activity?.getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPref?.getString(key.toString(), null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.getType()
        println("Successful Get")
        return gson.fromJson(json, type)
    }
}