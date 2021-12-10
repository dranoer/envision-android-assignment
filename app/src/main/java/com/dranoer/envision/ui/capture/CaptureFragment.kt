package com.dranoer.envision.ui.capture

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.dranoer.envision.R
import com.dranoer.envision.data.remote.Resource
import com.dranoer.envision.databinding.FragmentCaptureBinding
import com.dranoer.envision.ui.SharedViewModel
import com.dranoer.envision.ui.listener.NavigationListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_capture.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class CaptureFragment : Fragment() {
    private var _binding: FragmentCaptureBinding? = null
    private val binding get() = _binding!!

    private var imageCapture: ImageCapture? = null

    val viewModel: SharedViewModel by viewModels()

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaptureBinding.inflate(inflater, container, false)
        val view = binding.root

        startCamera()

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.captureButton.setOnClickListener { takePhoto() }

        return view
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)

                    binding.capturedImage.load(savedUri)
                    binding.capturedImage.isVisible = true
                    binding.progressText.isVisible = true
                    binding.viewFinder.isGone = true
                    binding.captureButton.isGone = true

                    viewModel.postPhoto(photoFile).observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is Resource.Loading -> {
                                binding.progressbar.isVisible = true
                            }
                            is Resource.Success -> {
                                binding.progressbar.isGone = true

                                var pharagraphs = ""
                                for (paragraph in result.data.response.paragraphs) {
                                    pharagraphs += paragraph.paragraph
                                }
                                navigationListener?.openCaptured(pharagraphs)
                            }
                            is Resource.Failure -> {
                                binding.progressbar.isGone = true
                                Log.e(TAG, "${result.exception.message}")
                            }
                        }

                    }
                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, getString(R.string.failed_usecase_binding), exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    fun getOutputDirectory(): File {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else activity?.filesDir!!
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraX"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private var navigationListener: NavigationListener? = null

        @JvmStatic
        fun newInstance(listener: NavigationListener): CaptureFragment {
            navigationListener = listener
            return CaptureFragment()
        }
    }
}