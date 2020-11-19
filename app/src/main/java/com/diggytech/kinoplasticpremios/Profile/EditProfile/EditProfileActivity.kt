package com.diggytech.kinoplasticpremios.Profile.EditProfile

//import kotlinx.android.synthetic.main.edit_profile.spinnerBrands
//import kotlinx.android.synthetic.main.edit_profile.spinnerCities
//import kotlinx.android.synthetic.main.edit_profile.spinnerLocations
//import kotlinx.android.synthetic.main.edit_profile.spinnerStates
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.soyadeti.Interface.APIService
import com.bumptech.glide.Glide
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.DashBoard.ProfileResponse
import com.diggytech.kinoplasticpremios.DashBoard.ProfileResponseUserLocation
import com.diggytech.kinoplasticpremios.ImageFilePath
import com.diggytech.kinoplasticpremios.LocationsModel
import com.diggytech.kinoplasticpremios.Login.SignUp.*
import com.diggytech.kinoplasticpremios.Profile.CombineDataModelOfServer
import com.diggytech.kinoplasticpremios.Profile.ViewProfile.ModelProfile
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.model.LocationArryForUserTwo_Three
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.PipelineDraweeController
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import jp.wasabeef.fresco.processors.BlurPostprocessor
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.edit_profile.etContact
import kotlinx.android.synthetic.main.edit_profile.etEmail
import kotlinx.android.synthetic.main.edit_profile.etName
import kotlinx.android.synthetic.main.edit_profile.etcpf
import kotlinx.android.synthetic.main.edit_profile_two.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class EditProfileActivity : AppCompatActivity(), EditProfileContract.View {


    var selectedPostion: Int? = 0
    private lateinit var mPresenter: EditProfilePresenter
    private val GALLERY_IMAGE = 11
    private val CAMERA_IMAGE = 12
    private lateinit var mIdFileName: String
    private var imageFile: File? = null

    private var uri_path: Uri? = null
    private var locationList = mutableListOf<LocationsModel>()

    private var bandStateLocationList = mutableListOf<LocationsModel>()
    private var bandCityLocationList = mutableListOf<LocationsModel>()
    private var bandAllLocationList = mutableListOf<LocationsModel>()


    private var brands = mutableListOf<LocationsModel>()
    private var states = mutableListOf<LocationsModel>()
    private var cities = mutableListOf<LocationsModel>()
    private var locationNames = mutableListOf<LocationsModel>()


    private val GALLERY = 1
    private val CAMERA = 2
    // var isCamera: Boolean? = false

    var Location_value: String = ""
    var profile_image_path: String? = null
    private var inputImageUri: Uri? = null


    var selected_brand_name: String = ""
    var selected_state_name: String = ""
    var selected_city_name: String = ""
    var selected_location_name: String = ""

    lateinit var auth_token: String
    lateinit var user_id: String
    lateinit var user_type: String
    var mAPIService: APIService? = null

    var list_task = ArrayList<ProfileResponseUserLocation>()
    var combine_data_model = ArrayList<CombineDataModelOfServer>()
    //lateinit var selected_location_name: String

    lateinit var active_adapter: EditProfileAdapterTwo
    var selection_multiple_locations = ArrayList<EditModelTwo>();
    var selection_multiple_locations_childe = ArrayList<EditProfileRequest_ChildDataModel>();
    val selected_location = ArrayList<LocationArrayForUserTwo_Three_Profile>()


    /*2nd time ini*/
      var select_brand_name_two: String?=""
     var select_state_name_two: String?=""
     var select_city_name_two: String?=""
     var select_location_name_two: String?=""
     var select_location_id_two: String?=""
     var address_user_one: String?=""

    // var  listWithoutDuplicates=ArrayList<EditModelTwo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        mAPIService = APIService.ApiUtils.apiService
        auth_token = Constants.getPrefs(this)!!.getString(Constants.TOKEN, "")
        user_id = Constants.getPrefs(this)!!.getString(Constants.USER_ID, "")
        user_type = Constants.getPrefs(this)!!.getString(Constants.USER_TYPE, "")

        if (user_type.equals("1")) {
            recyclerview_location_profile.visibility = View.GONE

        } else {
            recyclerview_location_profile.visibility = View.VISIBLE
        }
        mPresenter = EditProfilePresenter(this)
        requestMultiplePermissions()
        mPresenter.getProfileData()

        var user_type = Constants.getPrefs(this)!!.getString(Constants.USER_TYPE, "")
        // var from_where_come = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_TYPESS", "")
        Log.e("USER_TYPE_IS", user_type)
        if (user_type.equals("1")) {


            ll_brand.visibility = View.VISIBLE
            ll_state.visibility = View.VISIBLE
            ll_city.visibility = View.VISIBLE
            ll_location.visibility = View.VISIBLE
            spinnerBrands.visibility = View.GONE
            spinnerStates.visibility = View.GONE
            spinnerCities.visibility = View.GONE
            spinnerLocations.visibility = View.GONE


//            selected_brand_name = txt_spinnerBrands_edit.text.toString().trim()
//            selected_state_name = txt_spinnerStates_edit.text.toString().trim()
//            selected_city_name = txt_spinnerStates_edit.text.toString().trim()
//            selected_location_name = txt_spinnerStates_edit.text.toString().trim()

            // BrandList()

            txt_spinnerBrands_edit.setOnClickListener(View.OnClickListener {
                spinnerBrands.visibility = View.VISIBLE
                spinnerBrands.performClick()
                txt_spinnerBrands_edit.visibility = View.GONE
                img_spinnerBrands_edit.visibility = View.GONE
                // mPresenter.getAllBrands()
                BrandList()
            })

            img_spinnerBrands_edit.setOnClickListener(View.OnClickListener {
                spinnerBrands.visibility = View.VISIBLE
                spinnerBrands.performClick()
                txt_spinnerBrands_edit.visibility = View.GONE
                img_spinnerBrands_edit.visibility = View.GONE
                // mPresenter.getAllBrands()
                BrandList()
            })
            txt_spinnerStates_edit.setOnClickListener(View.OnClickListener {
                spinnerStates.visibility = View.VISIBLE
                spinnerStates.performClick()
                txt_spinnerStates_edit.visibility = View.GONE
                img_spinnerStates_edit.visibility = View.GONE
                // mPresenter.getStateData(model.brand)
                //mPresenter.getStateData(selected_brand_name)

            })
            img_spinnerStates_edit.setOnClickListener(View.OnClickListener {
                spinnerStates.visibility = View.VISIBLE
                spinnerStates.performClick()
                txt_spinnerStates_edit.visibility = View.GONE
                img_spinnerStates_edit.visibility = View.GONE
                // mPresenter.getStateData(selected_brand_name)

            })
            //spinnerStates

            // spinnerCities
            txt_spinnerCities_edit.setOnClickListener(View.OnClickListener {
                spinnerCities.visibility = View.VISIBLE
                spinnerCities.performClick()
                txt_spinnerCities_edit.visibility = View.GONE
                img_spinnerCities_edit.visibility = View.GONE

                //  Toast.makeText(this@EditProfileActivity,"DATA_IS"+ model.state +" "+ model.brand,Toast.LENGTH_LONG).show()
                //mPresenter.getCitydata(selected_state_name, selected_brand_name)
            })
            img_spinnerCities_edit.setOnClickListener(View.OnClickListener {
                spinnerCities.visibility = View.VISIBLE
                spinnerCities.performClick()
                txt_spinnerCities_edit.visibility = View.GONE
                img_spinnerCities_edit.visibility = View.GONE
                // mPresenter.getCitydata(selected_state_name, selected_brand_name)
            })

            // spinnerLocations
            txt_spinnerLocations_edit.setOnClickListener(View.OnClickListener {
                spinnerLocations.visibility = View.VISIBLE
                txt_spinnerLocations_edit.visibility = View.GONE
                img_spinnerLocations_edit.visibility = View.GONE
                //mPresenter.getLocations(selected_city_name, selected_brand_name, 0)
            })
            img_spinnerLocations_edit.setOnClickListener(View.OnClickListener {
                spinnerLocations.visibility = View.VISIBLE
                txt_spinnerLocations_edit.visibility = View.GONE
                img_spinnerLocations_edit.visibility = View.GONE
                // mPresenter.getLocations(selected_city_name, selected_brand_name, 0)
            })


        } else {
            Profilemethod()
            BrandList()
        }


        ivCamera.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
            //showPictureDialog()
        }
    }

    override fun getUserId(): String {
        return Constants.getPrefs(this)!!.getString(Constants.USER_ID, "")!!
    }

    override fun getAuthToken(): String {
        return Constants.getPrefs(this)!!.getString(Constants.TOKEN, "")!!
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
        Constants.setNonTouchableFlags(this@EditProfileActivity)
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
        Constants.clearNonTouchableFlags(this@EditProfileActivity)
    }

    override fun showError(message: String) {
        Constants.showAlert(this@EditProfileActivity, message)
    }

    override fun showErrorMessage(message: String?) {
        Constants.showAlert(this@EditProfileActivity, message!!)
    }


    override fun setProfileData(model: ModelProfile) {
//        ivImage.setImageURI(model.profile_pic)
        etName.setText(model.username)
        if (model.cpf_number.isEmpty()) {
            etcpf.setText("NA")
        } else {
            etcpf.setText(model.cpf_number)
        }
        if (model.phone_number.isEmpty()) {
            etContact.setText("NA")
        } else {
            etContact.setText(model.phone_number)
        }
        if (model.profile_pic.isNotEmpty()) {

            profile_image_path = model.profile_pic
            ivImage.setImageURI(profile_image_path)


        }
        etEmail.setText(model.email)

        txt_spinnerBrands_edit.setText(model.brand)
        txt_spinnerStates_edit.setText(model.state)
        txt_spinnerCities_edit.setText(model.city)
        txt_spinnerLocations_edit.text = model.location
        address_user_one=model.address


        // blurred image
        val postprocessor = BlurPostprocessor(this, 10)
        val imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(model.profile_pic))
            .setPostprocessor(postprocessor)
            .build()

        val controller = Fresco.newDraweeControllerBuilder()
            .setImageRequest(imageRequest)
            .setOldController(ivBack.controller)
            .build() as PipelineDraweeController

        ivBack.controller = controller

        tvSave.setOnClickListener {

            if (user_type.equals("1")) {
                mPresenter.sendEditedData()
            } else {
                mPresenter.sendEditedData_Two()
            }
        }
    }


    override fun getUsername(): String {
        return etName.text.toString().trim()
    }

    override fun getCpf(): String {
        return etcpf.text.toString().trim()
    }

    override fun getContact(): String {
        return etContact.text.toString().trim()
    }

    override fun getImageFile(): File? {
        if (imageFile != null) {
            return imageFile
        }
        return null
    }

    override fun saveRegisterDataToPrefs(data: JSONObject?) {
        Constants.getPrefs(this)!!.edit().putString(Constants.USER_ID, data!!.optString("id"))
            .apply()
        Constants.getPrefs(this)!!.edit().putString(Constants.EMAIL, data.optString("email"))
            .apply()
        Constants.getPrefs(this)!!.edit().putString(Constants.USERNAME, data.optString("username"))
            .apply()
        Constants.getPrefs(this)!!.edit().putString(Constants.PHONE, data.optString("phone_number"))
            .apply()
        Constants.getPrefs(this)!!.edit().putString(Constants.ADDRESS, data.optString("address"))
            .apply()
        Constants.getPrefs(this)!!.edit().putString(Constants.BRAND, data.optString("brand"))
            .apply()
        Constants.getPrefs(this)!!.edit().putString(Constants.CPF, data.optString("cpf_number"))
            .apply()

        Constants.getPrefs(this)!!.edit()
            .putString(Constants.LOCATION, data.optString("address_field")).apply()

        //Constants.getPrefs(this)!!.edit().putString(Constants.LOCATION_ID, data.optString("id")).apply()


        Constants.getPrefs(this)!!.edit()
            .putString(Constants.PROFILE_PIC, data.optString("profile_pic"))
            .apply()
        Constants.getPrefs(this)!!.edit().putString(Constants.STATE, data.optString("state"))
            .apply()
        Constants.getPrefs(this)!!.edit().putString(Constants.CITY, data.optString("city"))
            .apply()

        val returnIntent = Intent()
        setResult(Activity.RESULT_OK, returnIntent)
        Constants.onHideKeyboard(this)
        // Constants.showFinishingAlert(this@EditProfileActivity, "Updated Successfully")
        Constants.showFinishingAlert(this@EditProfileActivity, "atualizado com sucesso")
    }

    private fun showPictureDialog() {
        // val pictureDialogItems = arrayOf("Open gallery", "Open camera")
        val pictureDialogItems = arrayOf("abrir galeria", "tirar foto")
        val mBuilder = AlertDialog.Builder(this@EditProfileActivity)
        // mBuilder.setTitle("Choose an item")
        mBuilder.setTitle("Escolha um:")
        mBuilder.setSingleChoiceItems(
            pictureDialogItems, -1
        )
        { dialogInterface, i ->
            when (i) {
                0 -> {
                    choosePhotoFromGallary()
                }

                1 -> {
                    showLoader()
                    takePhotoFromCamera()
                    showLoader()
                }
            }
            showLoader()
            dialogInterface.dismiss()
            showLoader()
        }
        showLoader()
        val mDialog = mBuilder.create()
        mDialog.show()
        showLoader()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
        showLoader()
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private fun requestMultiplePermissions() {

        Dexter.withActivity(this@EditProfileActivity)
            .withPermissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        //   Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                    }

                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {
                        // show alert dialog navigating to Settings
                        //openSettingsDialog();
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }


            }).withErrorListener { }
            .onSameThread()
            .check()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (resultCode == Activity.RESULT_OK)
//        {
        if (requestCode == GALLERY) {
            //   isCamera = false

            if (data != null) {
                try {
                    val selectedImageUri = data!!.data
                    ivImage.visibility = View.VISIBLE
                    ivImage.setImageURI(selectedImageUri)
                    hideLoader()
                    // blurred image
                    val postprocessor = BlurPostprocessor(this, 10)
                    val imageRequest =
                        ImageRequestBuilder.newBuilderWithSource(selectedImageUri)
                            .setPostprocessor(postprocessor)
                            .build()

                    val controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(imageRequest)
                        .setOldController(ivBack.controller)
                        .build() as PipelineDraweeController

                    ivBack.controller = controller


                    val idProofUri =
                        ImageFilePath.getPath(this@EditProfileActivity, selectedImageUri)
                    imageFile = File(idProofUri)
                    imageFile = Constants.saveBitmapToFile(imageFile!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else if (requestCode == CAMERA) {
            // isCamera = true
            if (data != null) {
                val thumbnail = data!!.extras!!.get("data") as Bitmap
                saveImage(thumbnail)
                hideLoader()
                Toast.makeText(this@EditProfileActivity, "Image Saved!", Toast.LENGTH_SHORT)
                    .show()

                ivImage.visibility = View.VISIBLE

                val postprocessor = BlurPostprocessor(this, 10)
                val imageRequest = ImageRequestBuilder.newBuilderWithSource(uri_path)
                    .setPostprocessor(postprocessor)
                    .build()

                val controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(imageRequest)
                    .setOldController(ivBack.controller)
                    .build() as PipelineDraweeController

                ivBack.controller = controller

                val idProofUri =
                    ImageFilePath.getPath(this@EditProfileActivity, uri_path)
                imageFile = File(idProofUri)
                imageFile = Constants.saveBitmapToFile(imageFile!!)

            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val selectedImageUri = result.uri

                ivImage.visibility = View.VISIBLE
                ivImage.setImageURI(selectedImageUri)

                hideLoader()
                // blurred image
                val postprocessor = BlurPostprocessor(this, 10)
                val imageRequest =
                    ImageRequestBuilder.newBuilderWithSource(selectedImageUri)
                        .setPostprocessor(postprocessor)
                        .build()

                val controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(imageRequest)
                    .setOldController(ivBack.controller)
                    .build() as PipelineDraweeController

                ivBack.controller = controller


                val idProofUri =
                    ImageFilePath.getPath(this@EditProfileActivity, selectedImageUri)
                imageFile = File(idProofUri)
                imageFile = Constants.saveBitmapToFile(imageFile!!)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY
        )
        // have the object build the directory structure, if needed.
        Log.d("fee", wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists()) {

            wallpaperDirectory.mkdirs()
        }

        try {
            Log.d("heel", wallpaperDirectory.toString())
            val f = File(
                wallpaperDirectory, ((Calendar.getInstance()
                    .getTimeInMillis()).toString() + ".jpg")
            )
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(
                this@EditProfileActivity,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null
            )
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())
            val idProofUri = f.getAbsolutePath()
            imageFile = File(idProofUri)
            uri_path = Uri.fromFile(imageFile);
            ivImage.visibility = View.VISIBLE
            ivImage.setImageURI(uri_path)
            return f.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }


    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }

    /* store and return image result */
    private fun onCaptureImageResult(data: Intent): File? {
        try {
            val thumbnail = data.extras!!.get("data") as Bitmap
            val bytes = ByteArrayOutputStream()
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
            val destination = File(
                Environment.getExternalStorageDirectory(),
                System.currentTimeMillis().toString() + ".jpg"
            )

            val fo: FileOutputStream
            try {
                destination.createNewFile()
                fo = FileOutputStream(destination)

                // Media scanner code
                val uri = Uri.fromFile(destination)
                val scanFileIntent = Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri
                )
                sendBroadcast(scanFileIntent)
                fo.write(bytes.toByteArray())
                fo.close()
                val matrix = Matrix()
                matrix.postRotate(
                    Constants.getCameraPhotoOrientation(
                        this@EditProfileActivity,
                        uri,
                        destination
                    ).toFloat()
                )
                val rotated = Bitmap.createBitmap(
                    thumbnail, 0, 0, thumbnail.width, thumbnail.height,
                    matrix, true
                )
                val url = MediaStore.Images.Media.insertImage(
                    this@EditProfileActivity.contentResolver,
                    rotated,
                    "attachment",
                    null
                )
                val outPutfileUri = Uri.parse(url)
                ivImage.visibility = View.VISIBLE
                ivImage.setImageURI(outPutfileUri)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return destination
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /*profile api*/
    fun Profilemethod() {
        showLoader()
        Log.e("PROFILE_DATA", auth_token + " " + user_id)

        mAPIService!!.Profile(auth_token, user_id).enqueue(object :
            Callback<ProfileResponse> {

            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {

                //  Log.i("", "post submitted to API." + response.body()!!)

                hideLoader()
                if (response.isSuccessful()) {

                    if (response.body()!!.code == 200) {
                        // Toast.makeText( this@EditProfileActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()

                        var user_pic = response.body()!!.data.profile_pic


                        Log.e("PROFILE_PIC", user_pic)
                        if (user_pic.equals(null) || user_pic.equals("")) {
                            ivImage.setImageResource(R.drawable.placeholder_img)
                        } else {
                            // var image_value="https://clientstagingdev.com/kinoplastic_server/public/images/users/"+user_pic
                            Glide.with(this@EditProfileActivity)
                                .load(user_pic)
                                .into(ivImage);

                        }


                        list_task.addAll(response.body()!!.data.user_Location)
                        for (i in 0 until list_task.size) {
                            var model = EditModelTwo()
                            model.brand = list_task.get(i).Brand
                            model.state = list_task.get(i).State
                            model.location = list_task.get(i).Location
                            model.city = list_task.get(i).City
                            model.locationID = list_task.get(i).LocationID
                            selection_multiple_locations.add(model)

                            // selection_multiple_locations.groupBy { it.brand }

                        }


                        recyclerview_location_profile!!.layoutManager = LinearLayoutManager(
                            this@EditProfileActivity,
                            RecyclerView.HORIZONTAL,
                            false
                        )
                        active_adapter = EditProfileAdapterTwo(
                            selection_multiple_locations,
                            this@EditProfileActivity
                        )


                        recyclerview_location_profile?.adapter = active_adapter
                    } else {
//                        Toast.makeText(
//                            this@EditProfileActivity,
//                            response.body()!!.message,
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                hideLoader()
                t.printStackTrace()

            }
        })
    }

    override fun getLocationArray(locationsArray: ArrayList<EditProfileRequest_ChildDataModel>): ArrayList<EditProfileRequest_ChildDataModel> {

        for (i in 0 until selection_multiple_locations.size) {
            var model = EditProfileRequest_ChildDataModel()
            model.brand = selection_multiple_locations[i].brand
            model.location = selection_multiple_locations[i].location
            model.city = selection_multiple_locations[i].city
            model.state = selection_multiple_locations[i].state
            model.locationID = selection_multiple_locations[i].locationID
            selection_multiple_locations_childe.add(model);
            Log.e("SELECTED_POSITION4", selection_multiple_locations_childe.size.toString())
        }
        return selection_multiple_locations_childe
    }

    override fun getUserType(): String {
        return Constants.getPrefs(this)!!.getString(Constants.USER_TYPE, "")!!
    }

    /*for user  one*/
    override fun getBrand(): String {

        if (select_brand_name_two.equals("")) {
            return txt_spinnerBrands_edit.text.toString().trim()
        } else {
            return select_brand_name_two!!
        }

    }

    override fun getState(): String {

        if (select_state_name_two.equals("")) {
            return txt_spinnerStates_edit.text.toString().trim()
        } else {
            return select_state_name_two!!
        }

    }

    //getCity
    override fun getCity(): String{

        if (select_city_name_two.equals("")) {
            return txt_spinnerCities_edit.text.toString().trim()
        } else {
            return select_city_name_two!!
        }

    }

    //getLocation
    override fun getLocation(): String {

        if (select_location_id_two.equals(""))
        {
            return address_user_one!!
        } else {
            return select_location_id_two!!
        }

    }

    /*for brand*/
    fun BrandList() {
        showLoader()
        //  mAPIService!!.Update_Profile(auto_token,user_id,part,name,location).enqueue(object : Callback<UpdateProfileResponse> {
        mAPIService!!.Brand_List().enqueue(object : Callback<BrandListResponse> {

            @SuppressLint("NewApi")
            override fun onResponse(
                call: Call<BrandListResponse>,
                response: Response<BrandListResponse>
            ) {
                //Log.e("", "post submitted to API." + response.body()!!)
                hideLoader()
                if (response.isSuccessful()) {

                    if (response.body()!!.code == 200) {
                        lateinit var body: ArrayList<BrandResponseData>
                        body = response.body()!!.data as ArrayList<BrandResponseData>
                        body.add(0, BrandResponseData("Selecionar marca"))


                        var brandspinnerAdapter =
                            BrandSpinnerAdapter(this@EditProfileActivity, body)

                        spinnerBrands?.adapter = brandspinnerAdapter

                        spinnerBrands.onItemClickListener

                        spinnerBrands.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }

                                override fun onItemSelected(
                                    parent: AdapterView<*>,
                                    view: View,
                                    position: Int,
                                    id: Long
                                ) {
                                    val location_values_array = LocationArryForUserTwo_Three()
                                    var selectedPostion = position

                                    select_brand_name_two = body.get(selectedPostion).brand
                                    location_values_array.setBrand(select_brand_name_two!!)
                                    if (position == 0) {
                                        Log.e("HI", "HI" + position)
                                    } else {
                                        StateList(select_brand_name_two!!)

                                    }
                                }

                            }


                    } else {
                        // Toast.makeText(this@EditProfileActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }

                } else {

                }


            }

            override fun onFailure(call: Call<BrandListResponse>, t: Throwable) {
                hideLoader()
                t.printStackTrace()

            }
        })


    }

    /*for State*/
    fun StateList(brand_value: String) {
        showLoader()

        var get_client_request = StateListRequest(brand_value!!)
        Log.e(" ", "state_request" + get_client_request)

        //  mAPIService!!.Update_Profile(auto_token,user_id,part,name,location).enqueue(object : Callback<UpdateProfileResponse> {
        mAPIService!!.State_List(get_client_request).enqueue(object :
            Callback<StateListResponse> {

            @SuppressLint("NewApi")
            override fun onResponse(
                call: Call<StateListResponse>,
                response: Response<StateListResponse>
            ) {
                //Log.e("", "post submitted to API." + response.body()!!)
                hideLoader()
                if (response.isSuccessful()) {

                    if (response.body()!!.code == 200) {
                        lateinit var body: ArrayList<StateListData>
                        body = response.body()!!.data as ArrayList<StateListData>
                        body.add(0, StateListData("Selecione o nome do estado"))


                        var spinnerAdapter = StateSpinnerAdapter(this@EditProfileActivity, body)

                        spinnerStates?.adapter = spinnerAdapter

                        spinnerStates.onItemClickListener

                        spinnerStates.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }

                                override fun onItemSelected(
                                    parent: AdapterView<*>,
                                    view: View,
                                    position: Int,
                                    id: Long
                                ) {

                                    val location_values_array = LocationArryForUserTwo_Three()
                                    //selectedPostion = position
                                    select_state_name_two = body.get(position).state
                                    location_values_array.setState(select_state_name_two!!)
                                    if (position == 0) {

                                        Log.e("HI", "HI" + position)
                                    } else {

                                        CityList(select_state_name_two!!, select_brand_name_two!!)
                                    }
                                }

                            }
                    } else {
                        // Toast.makeText(this@EditProfileActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }

                } else {
                    // Toast.makeText(context,response.body()!!.message, Toast.LENGTH_SHORT).show()
                }


            }

            override fun onFailure(call: Call<StateListResponse>, t: Throwable) {
                hideLoader()
                t.printStackTrace()

            }
        })


    }

    /*for city*/
    fun CityList(state: String, brand: String) {
        showLoader()

        var get_client_request = CityListRequest(state, brand)
        Log.e("get_client_requestcity", "get_client_request" + get_client_request)

        //  mAPIService!!.Update_Profile(auto_token,user_id,part,name,location).enqueue(object : Callback<UpdateProfileResponse> {
        mAPIService!!.City_List(get_client_request).enqueue(object :
            Callback<CityListResponse> {

            @SuppressLint("NewApi")
            override fun onResponse(
                call: Call<CityListResponse>,
                response: Response<CityListResponse>
            ) {
                //Log.e("", "post submitted to API." + response.body()!!)
                hideLoader()
                if (response.isSuccessful()) {

                    if (response.body()!!.code == 200) {
                        lateinit var body: ArrayList<CityListData>
                        body = response.body()!!.data as ArrayList<CityListData>
                        body.add(0, CityListData("Selecione o nome da cidade"))


                        var spinnerAdapter = CitySpinnerAdapter(this@EditProfileActivity, body)

                        spinnerCities?.adapter = spinnerAdapter

                        spinnerCities?.onItemClickListener

                        spinnerCities?.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }

                                override fun onItemSelected(
                                    parent: AdapterView<*>,
                                    view: View,
                                    position: Int,
                                    id: Long
                                ) {

                                    val location_values_array = LocationArryForUserTwo_Three()
                                    //selectedPostion = position
                                    select_city_name_two = body.get(position).city
                                    location_values_array.setState(select_city_name_two!!)

                                    if (position == 0) {

                                        Log.e("HI", "HI" + position)
                                    } else {

                                        LocationList(
                                            select_city_name_two!!,
                                            select_brand_name_two!!,
                                            user_type
                                        )
                                    }
                                }
                            }
                    } else {
                        Toast.makeText(
                            this@EditProfileActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    // Toast.makeText(context,response.body()!!.message, Toast.LENGTH_SHORT).show()
                }


            }

            override fun onFailure(call: Call<CityListResponse>, t: Throwable) {
                hideLoader()
                t.printStackTrace()

            }
        })


    }

    /*for location*/
    fun LocationList(city: String, brand: String, user_type: String) {
        showLoader()

        var get_client_request = LocationListRequest(city!!, brand!!)
        Log.e("get_client_location", "get_client_request" + get_client_request)

        //  mAPIService!!.Update_Profile(auto_token,user_id,part,name,location).enqueue(object : Callback<UpdateProfileResponse> {
        mAPIService!!.Location_List(get_client_request).enqueue(object :
            Callback<LocationListResponse> {

            @SuppressLint("NewApi")
            override fun onResponse(
                call: Call<LocationListResponse>,
                response: Response<LocationListResponse>
            ) {
                //Log.e("", "post submitted to API." + response.body()!!)
                hideLoader()
                if (response.isSuccessful()) {

                    if (response.body()!!.code == 200) {
                        lateinit var body: ArrayList<LocationListData>
                        body = response.body()!!.data as ArrayList<LocationListData>
                        body.add(0, LocationListData("", "Selecione o nome do local", ""))


                        var spinnerAdapter = SpinnerAdapter(this@EditProfileActivity!!, body)

                        spinnerLocations?.adapter = spinnerAdapter

                        spinnerLocations.onItemClickListener

                        spinnerLocations.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }

                                override fun onItemSelected(
                                    parent: AdapterView<*>,
                                    view: View,
                                    position: Int,
                                    id: Long
                                ) {

                                    val location_values_array =
                                        LocationArrayForUserTwo_Three_Profile()

                                    //selectedPostion = position
                                    select_location_name_two = body.get(position).name
                                    select_location_id_two = body.get(position).id
                                    var selected_location_status = body.get(position).status

                                    if (position == 0) {

                                        Log.e("HI", "HI" + position)
                                    } else {
                                        if (user_type != "1") {
                                            if (selected_location_status.equals("0")) {

                                                Log.e("HI", "HI" + position)

                                                var model = EditModelTwo()
                                                model.brand = select_brand_name_two
                                                model.city = select_city_name_two
                                                model.location = select_location_name_two
                                                model.locationID = select_location_id_two
                                                model.state = select_state_name_two
                                                selection_multiple_locations.add(model)


                                                Log.e(
                                                    "SELECTED_VALUE",
                                                    selection_multiple_locations.toString()
                                                )

                                                recyclerview_location_profile.layoutManager =
                                                    LinearLayoutManager(
                                                        this@EditProfileActivity,
                                                        RecyclerView.HORIZONTAL,
                                                        false
                                                    )
                                                active_adapter =
                                                    EditProfileAdapterTwo(
                                                        selection_multiple_locations,
                                                        this@EditProfileActivity
                                                    )
                                                recyclerview_location_profile.adapter =
                                                    active_adapter
                                                active_adapter.notifyDataSetChanged()
                                            } else {

                                                showErrorMessage("localização não disponível")
                                                var spinnerAdapter =
                                                    SpinnerAdapter.ItemRowHolder(view)
                                                spinnerAdapter.label.setText("Selecione o nome do local")
                                            }
                                        } else {
                                            val location_values_array =
                                                LocationArryForUserTwo_Three()
                                            location_values_array.setNames(select_location_name_two!!)
                                        }

                                    }


                                }


                            }


                    } else {
                        Toast.makeText(
                            this@EditProfileActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    // Toast.makeText(context,response.body()!!.message, Toast.LENGTH_SHORT).show()
                }


            }

            override fun onFailure(call: Call<LocationListResponse>, t: Throwable) {
                hideLoader()
                t.printStackTrace()

            }
        })


    }
}



