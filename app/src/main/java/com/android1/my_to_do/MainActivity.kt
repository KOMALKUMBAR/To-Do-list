package com.android1.my_to_do

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android1.my_to_do.Adpter.ToDoListAdpter
import com.android1.my_to_do.VIewModel.ToDoListViewModel
import com.android1.my_to_do.databinding.ActivityMainBinding
import com.android1.my_to_do.model.Todo
import java.util.Objects

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: ToDoListViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var toDoListAdpter: ToDoListAdpter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        // Sample data for the spinner
        val items = listOf("All", "Completed", "Not Completed")

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, items)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        binding.spinner.adapter = adapter

        // set a listener to handle item selection
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                filterBySpinner(selectedItem)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called to notify you that, within `s`, the `count` characters
                // beginning at `start` are about to be replaced by new text with length `after`.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                val text = s.toString()
                filterToDoList(text)
                // Do something with the updated text//eda
            }
        })

    }

    private fun filterBySpinner(selectedItem: String) {
       // toDoListAdpter.
        var listSpinner:List<Todo> = viewModel.filterBySpinnerToDoList(selectedItem)
        toDoListAdpter.setTodoList(listSpinner)
        toDoListAdpter.notifyDataSetChanged()
    }

    private fun filterToDoList(query:String) {
       // val query = binding.editTextSearch.text.toString().trim()

        var list:List<Todo> = viewModel.filterToDoList(query)
        toDoListAdpter.setTodoList(list)
        toDoListAdpter.notifyDataSetChanged()
    }

    private  fun initRecyclerView(){
        toDoListAdpter = ToDoListAdpter()
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = toDoListAdpter
        }
        viewModel = ViewModelProvider(this)[ToDoListViewModel::class.java]
        viewModel.makeAPICall()

        viewModel.observeMovieLiveData().observe(this, Observer { todoList ->
            binding.prograssBar.visibility=View.GONE
            toDoListAdpter.setTodoList(todoList.todos)
        })
    }
}