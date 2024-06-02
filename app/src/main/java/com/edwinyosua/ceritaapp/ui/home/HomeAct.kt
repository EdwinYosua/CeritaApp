package com.edwinyosua.ceritaapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.edwinyosua.ceritaapp.R
import com.edwinyosua.ceritaapp.databinding.ActivityHomeBinding
import com.edwinyosua.ceritaapp.ui.ViewModelFactory
import com.edwinyosua.ceritaapp.ui.adapter.LoadingStateAdapter
import com.edwinyosua.ceritaapp.ui.adapter.StoryAdapter
import com.edwinyosua.ceritaapp.ui.main.MainActivity

class HomeAct : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val homeViewModel: HomeViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvStories.layoutManager = LinearLayoutManager(this)
        getStoriesData()
    }

    private fun getStoriesData() {

        val adapter = StoryAdapter()
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        homeViewModel.storiesList.observe(this) {
            adapter.submitData(lifecycle, it)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                homeViewModel.logoutUser()
                val logoutIntent = Intent(this@HomeAct, MainActivity::class.java)
                startActivity(
                    logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}