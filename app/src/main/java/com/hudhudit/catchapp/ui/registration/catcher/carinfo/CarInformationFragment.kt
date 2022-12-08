package com.hudhudit.catchapp.ui.registration.catcher.carinfo

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CarBrand
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CarModel
import com.hudhudit.catchapp.core.base.BaseFragment
import com.hudhudit.catchapp.databinding.FragmentCarInformationBinding
import com.hudhudit.catchapp.ui.main.MainActivity
import com.hudhudit.catchapp.ui.registration.RegistrationActivity
import com.hudhudit.catchapp.utils.AppConstants
import com.hudhudit.catchapp.utils.AppConstants.Companion.convertToBase64
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream
import java.util.*


@AndroidEntryPoint
class CarInformationFragment : BaseFragment() {

    lateinit var binding: FragmentCarInformationBinding
    private lateinit var registrationActivity: RegistrationActivity
    private val viewModel by viewModels<CarInformationViewModel>()
    private lateinit var carBrands: MutableList<CarBrand>
    private lateinit var carModels: MutableList<CarModel>
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_IMAGE_GALLERY = 101
    private val REQUEST_IMAGE_CAPTURE = 111
    private val LOCATION_CODE = 100
    private val REQUEST_CODE = 110
    var frontImage = ""
    var backImage = ""
    var carBrand = ""
    var carModel = ""
    var latitude = ""
    var longitude = ""
    var token = ""

    companion object{
        var frontImg: Bitmap? = null
        var backImg: Bitmap? = null
        var isFront = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCarInformationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RegistrationActivity) {
            registrationActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseApp.initializeApp(registrationActivity)
        init()
        getToken()
        getCarBrands()
        onClick()
        getCurrentLocation()
    }

    private fun init(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(registrationActivity)
    }

    private fun onClick(){
        binding.background.setOnClickListener { registrationActivity.hideKeyboard() }
        binding.navigateBack.setOnClickListener { findNavController().popBackStack() }
//        binding.carBrandSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
//                carBrand = carBrands[i].id
//                getCarModels(carBrand)
//            }
//
//            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
//        }
//        binding.carModelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
//                carModel = carModels[i].id
//            }
//
//            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
//        }
        binding.chooseFrontCarId.setOnClickListener {
            isFront = true
            imagePickerPopUp()
        }
        binding.chooseBackCarId.setOnClickListener {
            isFront = false
            imagePickerPopUp()
        }
        binding.signUpBtn.setOnClickListener { checkValidation() }
        binding.carBrandSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                carBrand = carBrands[position].id
                getCarModels(carBrand)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })
        binding.carModelSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                carModel = carModels[position].id
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })
    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task: Task<String?> ->
                if (!task.isSuccessful) {
                    Log.w(
                        "FAILED",
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    return@addOnCompleteListener
                }
                token = task.result!!
            }
    }

    private fun getCarBrands(){
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getCarBrands()
        viewModel.carBrandsStatus.observe(viewLifecycleOwner){
            when (it.status){
                Resource.Status.SUCCESS -> {
//                    binding.progressBar.visibility = View.GONE
                    carBrands = it.data!!.results
                    val carBrandTitles: ArrayList<String> = ArrayList()
                    for (brand in carBrands){
                        carBrandTitles.add(brand.name)
                    }
                    setSpinner(binding.carBrandSpinner, carBrandTitles)
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    //Toast.makeText(registrationActivity, "error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getCarModels(carBrandId: String){
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getCarModels(carBrandId)
        viewModel.carModelsStatus.observe(viewLifecycleOwner){
            when (it.status){
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    carModels = it.data!!.results
                    val carModelTitles: ArrayList<String> = ArrayList()
                    for (model in carModels){
                        carModelTitles.add(model.name)
                    }
                    setSpinner(binding.carModelSpinner, carModelTitles)
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    //Toast.makeText(registrationActivity, "error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun imagePickerPopUp() {
        val alertView: View = LayoutInflater.from(context).inflate(R.layout.image_picker_dialog, null)
        val alertBuilder = AlertDialog.Builder(context).setView(alertView).show()
        alertBuilder.show()
        alertBuilder.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val camera: LinearLayoutCompat = alertView.findViewById(R.id.camera)
        val gallery: LinearLayoutCompat = alertView.findViewById(R.id.gallery)

        camera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(registrationActivity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(
                    registrationActivity, arrayOf(
                        Manifest.permission.CAMERA),
                    REQUEST_CODE
                )
            }else{
                dispatchTakePictureIntent()
            }
            alertBuilder.dismiss()
        }
        gallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
            alertBuilder.dismiss()
        }

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    private fun checkValidation(){
        val plateNumber = binding.plateNumberEdt.text.toString()
        when{
            plateNumber.isEmpty() -> {
                binding.plateNumberEdt.error = resources.getString(R.string.empty_plate_number)
            }
            frontImage.isEmpty() -> {
                Toast.makeText(registrationActivity, resources.getString(R.string.upload_front_image), Toast.LENGTH_SHORT).show()
            }
            backImage.isEmpty() -> {
                Toast.makeText(registrationActivity, resources.getString(R.string.upload_back_image), Toast.LENGTH_SHORT).show()
            }
            else -> {
                createAccount(plateNumber)
            }
        }
    }

    private fun createAccount(plateNumber: String){
        binding.progressBar.visibility = View.VISIBLE
        AppConstants.catcherSignUp.fcm_token = token
        AppConstants.catcherSignUp.car_brand_id = carBrand
        AppConstants.catcherSignUp.car_image_back = backImage
        AppConstants.catcherSignUp.car_image_front = frontImage
        AppConstants.catcherSignUp.car_model_id = carModel
        AppConstants.catcherSignUp.latitude = latitude
        AppConstants.catcherSignUp.longitude = longitude
        AppConstants.catcherSignUp.plate_number = plateNumber
        viewModel.createAccount(AppConstants.catcherSignUp)
        viewModel.createAccountStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    AppConstants.userType = "1"
                    AppConstants.catcherUser = it.data!!
                    saveUserToSharedPreferences()
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
//                    Toast.makeText(registrationActivity, "Signed up failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setSpinner(spinner: Spinner, titles: MutableList<String>){
        val sortAdapter: ArrayAdapter<*> = ArrayAdapter(registrationActivity, R.layout.spinner_item, titles)
        sortAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinner.adapter = sortAdapter
    }

    private fun getCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(
                registrationActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                registrationActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(registrationActivity){ task ->
            val location: Location? = task.result
            if (location != null){
                latitude = ""+location.latitude
                longitude = ""+location.longitude
            }else{
                Toast.makeText(registrationActivity, "Null", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            registrationActivity, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_CODE
        )
    }

    private fun saveUserToSharedPreferences() {
        val sharedPreferences =
            registrationActivity.getSharedPreferences(AppConstants.SHARED_PREF_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(AppConstants.ID_KEY, AppConstants.catcherUser.results.id)

        val gson = Gson()
        val json = gson.toJson(AppConstants.catcherUser)
        editor.putString(AppConstants.USER_KEY, json)
        editor.putString(AppConstants.TYPE_KEY, "1")
        editor.apply()
        val intent = Intent(registrationActivity, MainActivity:: class.java)
        startActivity(intent)
        registrationActivity.finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            if (isFront){
                frontImg = imageBitmap
                binding.frontCarId.setImageBitmap(frontImg)
                binding.frontCarIdLayout.visibility = View.GONE
                frontImage = convertToBase64(frontImg!!)!!
            }else{
                backImg = imageBitmap
                binding.backCarId.setImageBitmap(backImg)
                binding.backCarIdLayout.visibility = View.GONE
                backImage = convertToBase64(backImg!!)!!
            }
        }else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK){
            val inputStream: InputStream = requireContext().contentResolver.openInputStream(data!!.data!!)!!
            val imageBitmap = BitmapFactory.decodeStream(inputStream)
            if (isFront){
                binding.frontCarId.setImageBitmap(imageBitmap)
                binding.frontCarIdLayout.visibility = View.GONE
                frontImage = convertToBase64(imageBitmap)!!
            }else{
                binding.backCarId.setImageBitmap(imageBitmap)
                binding.backCarIdLayout.visibility = View.GONE
                backImage = convertToBase64(imageBitmap)!!
            }
        }else if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            dispatchTakePictureIntent()
        }
    }

    override fun onResume() {
        super.onResume()
        if (frontImg != null){
            binding.frontCarId.setImageBitmap(frontImg)
            binding.frontCarIdLayout.visibility = View.GONE
            frontImage = convertToBase64(frontImg!!)!!
        }
        if (backImg != null){
            binding.backCarId.setImageBitmap(backImg)
            binding.backCarIdLayout.visibility = View.GONE
            backImage = convertToBase64(backImg!!)!!
        }
    }
}