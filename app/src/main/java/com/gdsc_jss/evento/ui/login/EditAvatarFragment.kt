package com.gdsc_jss.evento.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.gdsc_jss.evento.R
import com.gdsc_jss.evento.databinding.FragmentEditAvatarBinding
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.models.SignUpBody
import com.gdsc_jss.evento.util.handleErrorsWithSnackbar
import com.gdsc_jss.evento.viewmodels.SignUpViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class EditAvatarFragment(var signUpBody: SignUpBody) : Fragment() {

    private val viewModel: SignUpViewModel by activityViewModels()
    private lateinit var binding: FragmentEditAvatarBinding
    private var imgpath: String? = null
    var displayImage: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAvatarBinding.inflate(layoutInflater, container, false)

        initViews()
        setClicks()
        observers()

        Timber.d(signUpBody.toString())

        return binding.root
    }

    private fun observers() {
        viewModel.signUpUser.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Timber.d(response.toString())
                    binding.registerBtn.text = "SignUp"
                    binding.registerBtn.isEnabled = true

                    activity?.supportFragmentManager?.popBackStack()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.login_frame, SignInFragment())?.commit()
                }
                is Resource.Loading -> {
                    binding.registerBtn.text = "Loading.."
                    binding.registerBtn.isEnabled = false
                }
                is Resource.Error -> {
                    binding.registerBtn.text = "SignUp"
                    binding.registerBtn.isEnabled = true
                    handleErrorsWithSnackbar(binding.root, response.msg)
                }
            }
        }
    }

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

        binding.registerBtn.setOnClickListener {
            if (displayImage != null) {
                signUpBody = signUpBody.copy(image = displayImage!!)
                Timber.d(signUpBody.toString())
            }
            Timber.d(signUpBody.toString())
            viewModel.signUp(signUpBody)
        }

    }


    private fun initViews() {
        binding.displayName.text = signUpBody.name.toString()
        binding.displayEmail.text = signUpBody.email.toString()
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
                binding.registerBtn.apply {
                    text = "Sign up"
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
                binding.registerBtn.apply {
                    text = "Sign up"
                    isEnabled = true
                }
            }
            init {
                binding.registerBtn.apply {
                    text = "Uploading image..."
                    isEnabled = false
                }
            }

            override fun onStart(requestId: String?) {
                binding.registerBtn.apply {
                    text = "Uploading image..."
                    isEnabled = false
                }
            }
        }).dispatch()
    }
}
