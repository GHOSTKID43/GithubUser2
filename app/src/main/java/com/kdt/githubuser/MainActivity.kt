package com.kdt.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.kdt.githubuser.data.remote.ApiClient
import com.kdt.githubuser.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private  lateinit var  binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        GlobalScope.launch(Dispatchers.IO){
            launch(Dispatchers.Main){
                flow {
                    val response = ApiClient
                        .githubService
                        .getUserGithub()

                    emit(response)
                }.onStart {
                    binding.progressBar.isVisible = true
                }.onCompletion {
                    binding.progressBar.isVisible = false
                }.catch {
                    Toast.makeText(this@MainActivity, it.message.toString(),Toast.LENGTH_SHORT
                    ).show()
                    Log.e("Error",it.message.toString())
                }.collect{
                    adapter.setData(it)
                }
            }

        }
    }
}