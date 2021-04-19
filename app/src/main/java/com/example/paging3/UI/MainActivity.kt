package com.example.paging3.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.paging3.Adapter.DogsAdapter
import com.example.paging3.Adapter.LoaderStateAdapter
import com.example.paging3.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val mainViewModel:MainViewModel by viewModels()
    @Inject
     lateinit var dogsAdapter: DogsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerview()
        lifecycleScope.launchWhenStarted {
            mainViewModel.getAllDogs.collectLatest { response->
                binding.apply {
                   progressBar.isVisible=false
                    recyclerview.isVisible=true
                }
                dogsAdapter.submitData(response)
            }
        }
    }

    private fun initRecyclerview() {
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@MainActivity,2)
                adapter = dogsAdapter.withLoadStateHeaderAndFooter(
                    header = LoaderStateAdapter { dogsAdapter :: retry},
                    footer = LoaderStateAdapter{dogsAdapter :: retry}
                )
            }
        }
    }
}




//aa230118-74d4-4f21-b6a5-37058cfaa771