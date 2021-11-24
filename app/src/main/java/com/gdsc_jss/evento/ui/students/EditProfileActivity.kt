package com.gdsc_jss.evento.ui.students

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.gdsc_jss.evento.R
import com.gdsc_jss.evento.databinding.ActivityEditProfileBinding
import com.gdsc_jss.evento.network.AuthResource
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.models.UpdateUserBody
import com.gdsc_jss.evento.network.models.UserResponse
import com.gdsc_jss.evento.util.handleErrorsWithSnackbar
import com.gdsc_jss.evento.viewmodels.UserViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: UserViewModel by viewModels()
    var imgpath: String? = null
    var displayImage: String = ""
    var previousImage: String = ""
    var collegeId: String = ""
    var password: String = ""

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers()
        setClicks()

    }

    @DelicateCoroutinesApi
    private fun setClicks() {
        binding.edit.setOnClickListener {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        binding.updateBtn.setOnClickListener {
            val updateUserBody = UpdateUserBody(
                branch = binding.branchText.text.toString(),
                name = binding.nameText.text.toString(),
                image = if (displayImage.isEmpty()) {
                    previousImage
                } else {
                    displayImage
                },
                phone = binding.mobileText.text.toString(),
                email = binding.emailText.text.toString(),
                section = binding.sectionText.text.toString(),
                year = binding.yearSpinner.selectedItem.toString().toInt(),
                collegeId = collegeId
            )

            Timber.d(updateUserBody.toString())

            viewModel.updateUser(updateUserBody).observe(this) { response ->
                when (response) {
                    is Resource.Success -> {
                        binding.updateBtn.text = "Update"
                        binding.updateBtn.isEnabled = true
                        viewModel.getUser()
                        finish()
                    }
                    is Resource.Loading -> {
                        binding.updateBtn.text = "Updating.."
                        binding.updateBtn.isEnabled = false
                    }
                    is Resource.Error -> {
                        binding.updateBtn.text = "Update"
                        binding.updateBtn.isEnabled = true
                        handleErrorsWithSnackbar(binding.root, response.msg)
                    }
                }
            }
        }
    }

    private fun observers() {
        UserViewModel.user.observe(this) { response ->
            when (response) {
                is AuthResource.Authenticated -> {
                    Timber.d(response.user.toString())
                    initViews(response.user)
                }
                else -> {

                }
            }
        }
    }

    private fun initViews(user: UserResponse) {
        if (user.image?.isNotBlank() == true) {
            previousImage = user.image
            collegeId = user.collegeId
            Glide.with(binding.root).load(user.image).into(binding.avatar)
        } else {
            if (user.gender == "Female") {
                binding.avatar.setImageResource(R.drawable.avatar)
            }
        }

        binding.nameText.setText(user.name)
        binding.emailText.setText(user.email ?: "")
        binding.mobileText.setText(user.phone ?: "")
        binding.branchText.setText(user.branch)
        binding.sectionText.setText(user.section)

        val yearList = arrayOf(1, 2, 3, 4)
        val yearAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, yearList)
        binding.yearSpinner.adapter = yearAdapter

        binding.yearSpinner.setSelection(user.year - 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data

            Glide.with(binding.root).load(fileUri).into(binding.avatar)

            //You can get File object from intent

            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent

            val filePath: String = ImagePicker.getFilePath(data)!!

            imgpath = filePath

            uploadToCloudinary(filePath)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            handleErrorsWithSnackbar(binding.root, ImagePicker.getError(data))
        } else {
            handleErrorsWithSnackbar(binding.root, "Something went wrong")
        }
    }

    private fun uploadToCloudinary(filepath: String) {
        MediaManager.get().upload(filepath).callback(object :
            UploadCallback {
            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                binding.updateBtn.apply {
                    text = "Update"
                    isEnabled = true
                }
                displayImage = (resultData?.get("url") as String?).toString()
                Timber.d(resultData.toString())
            }

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {

            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                handleErrorsWithSnackbar(binding.root, "$error")
                binding.updateBtn.apply {
                    text = "Update"
                    isEnabled = true
                }
            }
            init {
                binding.updateBtn.apply {
                    text = "Uploading image..."
                    isEnabled = false
                }
            }

            override fun onStart(requestId: String?) {
                binding.updateBtn.apply {
                    text = "Uploading image..."
                    isEnabled = false
                }
            }
        }).dispatch()
    }
}