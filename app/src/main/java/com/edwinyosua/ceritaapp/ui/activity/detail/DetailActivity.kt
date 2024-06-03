package com.edwinyosua.ceritaapp.ui.activity.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edwinyosua.ceritaapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}