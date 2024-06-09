package com.edwinyosua.ceritaapp.ui.activity.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.edwinyosua.ceritaapp.databinding.ActivityDetailBinding
import com.edwinyosua.ceritaapp.network.ApiResult
import com.edwinyosua.ceritaapp.ui.ViewModelFactory

class DetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val detailViewModel: DetailViewModel by viewModels<DetailViewModel> {
        factory
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val idStories = intent.getStringExtra(EXTRA_ID)


        binding.apply {
            if (idStories?.isNotEmpty() == true) {
                detailViewModel.getStoriesDetailById(idStories)
            }

            detailViewModel.storiesResponse.observe(this@DetailActivity) { response ->
                when (response) {
                    is ApiResult.ApiSuccess -> {
                        prgBar.visibility = View.GONE
                        nameDetail.text = response.data.name
                        descDetail.text = response.data.description

                        Glide.with(this@DetailActivity)
                            .load(response.data.photoUrl)
                            .into(imgDetail)
                    }

                    is ApiResult.ApiError -> {
                        prgBar.visibility = View.GONE
                        showToast(response.error)
                    }

                    ApiResult.Loading -> {
                        prgBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    companion object {
        const val EXTRA_ID = "extra_id"
    }
}