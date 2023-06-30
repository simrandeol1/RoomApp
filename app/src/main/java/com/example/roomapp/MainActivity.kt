package com.example.roomapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.roomapp.adapter.CustomAdapter
import com.example.roomapp.data.Options
import com.example.roomapp.viewmodel.DataModel
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private val mutableMap = mutableMapOf<String, String>()
    private var selectedProperty: String = ""
    private var selectedRoom: String = ""
    private var selectedOther: String = ""
    private var listOfProperty: MutableList<Options> = mutableListOf()
    private var listOfRoom: List<Options> = listOf()
    private var listOfOther: List<Options> = listOf()
    private lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        root = findViewById(R.id.root)
        val dataStoreManager = DataStoreManager(this)
        val viewModel = ViewModelProvider(this, DataModel.Factory(application,dataStoreManager))[DataModel::class.java]
        val propertySpinner: Spinner = findViewById(R.id.property)
        val roomSpinner: Spinner = findViewById(R.id.rooms)
        val otherSpinner: Spinner = findViewById(R.id.other)
        addSpinnerListeners(propertySpinner, roomSpinner, otherSpinner)

        viewModel.liveFacilitiesList.observe(this){
            listOfProperty = it[0].options
            listOfRoom = it[1].options
            listOfOther = it[2].options
            val propertyAdapter = CustomAdapter(this, listOfProperty)
            propertySpinner.adapter = propertyAdapter
            val roomAdapter = CustomAdapter(this, listOfRoom)
            roomSpinner.adapter = roomAdapter
            val otherAdapter = CustomAdapter(this, listOfOther)
            otherSpinner.adapter = otherAdapter
        }

        viewModel.getListData()
        processData(viewModel)

        val validBtn: Button = findViewById(R.id.validBtn)
        validBtn.setOnClickListener {
            checkValidity()
        }
    }

    private fun addSpinnerListeners(propertySpinner: Spinner, roomSpinner: Spinner, otherSpinner: Spinner){
        propertySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                selectedProperty = "1+${listOfProperty[position].id}"
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        roomSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                selectedRoom = "2+${listOfRoom[position].id}"
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        otherSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                selectedOther = "3+${listOfOther[position].id}"
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
    }
    private fun processData(viewModel: DataModel){
        viewModel.liveExclusionsList.observe(this) { list ->
            list?.let {
                for (obj in list) {
                    mutableMap["${obj[0].facility_id}+${obj[0].options_id}"] = "${obj[1].facility_id}+${obj[1].options_id}"
                }
            }
        }
    }

    private fun checkValidity(): Boolean{
        if(selectedProperty.equals("") || selectedRoom.equals("") || selectedOther.equals("")) {
            Snackbar.make(root, "Please select from all drop down", LENGTH_SHORT).show()
            return false
        }
        if(mutableMap.containsKey(selectedProperty) && mutableMap[selectedProperty].equals(selectedRoom)) {
            Snackbar.make(root, "Invalid Choice", LENGTH_SHORT).show()
            return false
        }
        if(mutableMap.containsKey(selectedRoom) && mutableMap[selectedRoom].equals(selectedOther)) {
            Snackbar.make(root, "Invalid Choice", LENGTH_SHORT).show()
            return false
        }
        if(mutableMap.containsKey(selectedProperty) && mutableMap[selectedProperty].equals(selectedOther)) {
            Snackbar.make(root, "Invalid Choice", LENGTH_SHORT).show()
            return false
        }
        else
            Snackbar.make(root, "Great Choice!", LENGTH_SHORT).show()
        return true
    }
}