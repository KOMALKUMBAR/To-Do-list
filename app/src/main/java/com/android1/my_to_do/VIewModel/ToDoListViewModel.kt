package com.android1.my_to_do.VIewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android1.my_to_do.Api.ApiInterface
import com.android1.my_to_do.Api.RetrofitHelper
import com.android1.my_to_do.model.JsonData
import com.android1.my_to_do.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ToDoListViewModel:ViewModel(){
    private var toDoListViewModel = MutableLiveData<JsonData>()

    fun  makeAPICall(){

        RetrofitHelper.api.getJson().enqueue(object  : Callback<JsonData>{
            override fun onResponse(call: Call<JsonData>, response: Response<JsonData>) {
                if (response.body()!=null){
                    toDoListViewModel.value = response.body()!!
                }
                else{
                    return
                }
            }
            override fun onFailure(call: Call<JsonData>, t: Throwable) {
                Log.d("TAG",t.message.toString())
            }
        })

    }

    fun observeMovieLiveData() : LiveData<JsonData> {
        return toDoListViewModel
    }

    fun filterToDoList(query: String):List<Todo> {
        return if (query.isEmpty()) {
                toDoListViewModel.value?.todos ?: emptyList()
        } else {
                toDoListViewModel.value?.todos?.filter {
                    it.todo.contains(query, ignoreCase = true)
                } ?: emptyList()
        }
    }

    fun filterBySpinnerToDoList(selectedItem: String): List<Todo> {
        return if (selectedItem.equals("Not Completed")) {
            toDoListViewModel.value?.todos?.filter {
                it.completed.equals(false)
            } ?: emptyList()
        } else if (selectedItem.equals("Completed")) {
            toDoListViewModel.value?.todos?.filter {
                it.completed.equals(true)
            } ?: emptyList()
        }else{
            toDoListViewModel.value?.todos ?: emptyList()
        }
    }
}
