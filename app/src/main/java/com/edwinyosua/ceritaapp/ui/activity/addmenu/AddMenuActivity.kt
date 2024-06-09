package com.edwinyosua.ceritaapp.ui.activity.addmenu

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.edwinyosua.ceritaapp.R
import com.edwinyosua.ceritaapp.databinding.ActivityAddMenuBinding
import com.edwinyosua.ceritaapp.network.ApiResult
import com.edwinyosua.ceritaapp.ui.ViewModelFactory
import com.edwinyosua.ceritaapp.ui.activity.home.HomeActivity
import com.edwinyosua.ceritaapp.utils.reduceFileImg
import com.edwinyosua.ceritaapp.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddMenuActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddMenuBinding
    private var currentImg: Uri? = null
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val addMenuVm: AddMenuViewModel by viewModels {
        factory
    }

    private val reqPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImg = uri
            showImg()
        } else {
            Log.d("Photo Picker", "No Media Selected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            reqPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.apply {
            galleryBtn.setOnClickListener { startGallery() }

            postBtn.setOnClickListener {
                if (edtAddDesc.text.toString().isEmpty()) {
                    showToast(getString(R.string.desc_warning))
                    return@setOnClickListener
                }
                uploadImg()
                addMenuVm.uploadResponse.observe(this@AddMenuActivity) { response ->
                    when (response) {
                        is ApiResult.ApiSuccess -> {
                            showToast(response.data.message.toString())
                            val intentHome = Intent(this@AddMenuActivity, HomeActivity::class.java)
                            startActivity(
                                intentHome.addFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                            Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                                )
                            )
                        }

                        is ApiResult.ApiError -> {
                            showToast(response.error)
                        }

                        ApiResult.Loading -> {
                            prgBar.visibility = View.VISIBLE
                            galleryBtn.isEnabled = false
                            postBtn.isEnabled = false
                            cancelBtn.isEnabled = false
                        }
                    }
                }
            }

            cancelBtn.setOnClickListener {
                val cancelIntent = Intent(this@AddMenuActivity, HomeActivity::class.java)
                startActivity(
                    cancelIntent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    )
                )
            }
        }
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this, REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImg() {
        currentImg?.let {
            Log.d("Img Uri", "show Image : $it")
            binding.previewImg.setImageURI(it)
        }
    }

    private fun uploadImg() {
        currentImg?.let { uri ->
            val imgFile = uriToFile(uri, this).reduceFileImg()
            Log.d("Img File", "Show Img : ${imgFile.path}")
            binding.apply {
                val reqBodyDesc =
                    edtAddDesc.text.toString().toRequestBody("text/plain".toMediaType())
                val reqImg = imgFile.asRequestBody("image/jpeg".toMediaType())

                val multiBody = MultipartBody.Part.createFormData(
                    "photo",
                    imgFile.name,
                    reqImg
                )
                addMenuVm.addStories(multiBody, reqBodyDesc, null, null)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

}