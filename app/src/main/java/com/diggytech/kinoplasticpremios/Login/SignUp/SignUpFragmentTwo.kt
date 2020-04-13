package com.diggytech.kinoplasticpremios

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.util.Log
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.soyadeti.Interface.APIService
import com.diggytech.kinoplasticpremios.DashBoard.DashBoardActivity
import com.diggytech.kinoplasticpremios.Login.SignUp.*
import com.diggytech.kinoplasticpremios.Login.SignUp.PhoneNumberTextWatcherTwo
import com.diggytech.kinoplasticpremios.Login.SignUp.SpinnerAdapter
import com.diggytech.kinoplasticpremios.model.LocationArryForUserTwo_Three
//import com.google.firebase.iid.FirebaseInstanceId
import com.imark.cinch.utils.PhoneNumberTextWatcher
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_signup2.*
import kotlinx.android.synthetic.main.activity_signup2.view.*
import kotlinx.android.synthetic.main.activity_signup2.view.txt_spinnerBrands
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SignUpFragmentTwo() : Fragment(), SignUpContract.View {


    private val PICK_IMAGE_REQUEST = 1
    lateinit var kmukouko: String
    /*from here*/
    lateinit var selected_state_spinner: String
    lateinit var selected_city_spinner: String


    lateinit var cpf_modified_value: String


    var selectedPostion: Int? = 0
    var spinner_clicked: Int? = 0

    // val selected_location = ArrayList<String>()
    val selected_location = ArrayList<LocationArryForUserTwo_Three>()


    lateinit var selected_location_name: String

    private var bandLocationList = mutableListOf<LocationsModel>()
    private var bandStateLocationList = mutableListOf<LocationsModel>()
    private var bandCityLocationList = mutableListOf<LocationsModel>()
    private var bandAllLocationList = mutableListOf<LocationsModel>()
    private var locationList = mutableListOf<LocationsModel>()
    private var brands = mutableListOf<LocationsModel>()
    private var states = mutableListOf<LocationsModel>()
    private var cities = mutableListOf<LocationsModel>()
    private var locationNames = mutableListOf<LocationsModel>()
    private var brandLocatioonNames = mutableListOf<BrandLocationsModel>()
    private lateinit var mPresenter: SignUpPresenter
    private lateinit var signup2: LinearLayout
    private lateinit var signup1: RelativeLayout
    private lateinit var btnNext: TextView
    private lateinit var btnSignUp: TextView
    private lateinit var et_funcao: TextView
    private lateinit var cpf_img: ImageView
//    private lateinit var dob: TextInputEditText
    private lateinit var dob: TextView
    private lateinit var datePicker: DatePicker
    private lateinit var v: View
    private val GALLERY_IMAGE = 11
    private val CAMERA_IMAGE = 12

    private lateinit var mIdFileName: String
    private var imageFile: File? = null
    private val GALLERY = 1
    private val CAMERA = 2
    var txt: String? = null

    var sb = StringBuilder()

    private var myPreferences = "myPrefs"
    private var EMPTY = "";
    private var isDelete: Boolean = false

    var count = 0
    var sentence = null

    var cpf_length: Int? = null
    var cpf_value: String? = null

    var languages = arrayOf("English", "French")

    var spinner: Spinner? = null
    lateinit var selected_user_type: String
    lateinit var selected_gender_type: String

    var selected_brand_name: String? = null
    var selected_state_name: String? = null
    var selected_city_name: String? = null
    var selected_location_name_two: String? = null


    var mAPIService: APIService? = null


    /*2nd time ini*/
    lateinit var select_brand_name_two: String
    lateinit var select_state_name_two: String
    lateinit var select_city_name_two: String
    lateinit var select_location_name_two: String
    lateinit var select_location_id_two: String

    lateinit var final_value_cpf: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_sign_up, container, false)
        mAPIService = APIService.ApiUtils.apiService
        //   ButtonCallBack.getInstance(context).setButtonListener(this)

        signup1 = v.findViewById(R.id.signup1)
        signup2 = v.findViewById(R.id.signup2)
        cpf_img = v.findViewById(R.id.cpf_img)
        btnNext = v.findViewById(R.id.btnNext)
        btnSignUp = v.findViewById(R.id.btnSignUp)
        dob = v.findViewById(R.id.dob_TextView)
        //et_funcao = v.findViewById(R.id.et_funcao)
        signup1.visibility = View.VISIBLE
        signup2.visibility = View.GONE
        cpf_img.visibility = View.GONE
        requestMultiplePermissions()
        mPresenter = SignUpPresenter(this)
        txt = "Selecione qualquer valor"
        v.txt_spinnerBrands.setText(txt)
        v.txt_spinnerBrands.text.toString().trim()


        v.spinner_funcao.adapter = TypeOfUsersAdapter(
            context!!,
            listOf(
                TypeOfUsers("Vendedor"), TypeOfUsers("Comprador")
                , TypeOfUsers("gerente")
            )
        )


        v.spinner_funcao.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {

                if (position == 0) {
                    selected_user_type = "1"
                    Log.e("position", "position" + position)

                } else if (position == 1) {
                    selected_user_type = "2"
                    Log.e("position", "position" + position)

                } else if (position == 2) {
                    selected_user_type = "3"
                    Log.e("position", "position" + position)
                }
            }
        }

        //////////

        v.genderSpinner.adapter = TypeOfUsersAdapter(
            context!!,
            listOf(
                TypeOfUsers("Masculina"), TypeOfUsers("Fêmea")
                , TypeOfUsers("De outros")
            )
        )


        v.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {

                if (position == 0) {
                    selected_gender_type = "1"
                    Log.e("position", "position" + position)

                } else if (position == 1) {
                    selected_gender_type = "2"
                    Log.e("position", "position" + position)

                } else if (position == 2) {
                    selected_gender_type = "3"
                    Log.e("position", "position" + position)
                }
            }
        }



        BrandList()

        v.etcpf.addTextChangedListener(PhoneNumberTextWatcher(v.etcpf));
        v.etcpf.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                // cpf_length = s?.toString().length
                cpf_length = s.toString().length

                cpf_value = s.toString().trim()
                Log.e("CPF_VALUE_IS1", cpf_value)


                var sentence = cpf_value
                println("Original sentence: $sentence")
                sentence = sentence?.replace("\\.".toRegex(), "")
                println("After replacement: $sentence")
                Log.e("CPF_VALUE_IS6", sentence)
                sentence = sentence?.replace("\\-".toRegex(), "")
                Log.e("CPF_VALUE_IS7", sentence)
                cpf_modified_value = sentence.toString()

                val sharedPreferences =
                    context?.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
                val editor = sharedPreferences?.edit()

                editor?.putString("CPF_NUMBER", cpf_modified_value)
                editor?.apply()


                val name = sharedPreferences?.getString("CPF_NUMBER", EMPTY)
                Log.e("CPF_NUMBERdasa", name)

                final_value_cpf = name.toString()

                if (cpf_length == 14) {
                    cpf_value?.let { mPresenter.getcpfvalid(it, false) }
                }
                //Log.e("CPF_VALUE_IS_AFTER", final_value_cpf)

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        v.etContact.addTextChangedListener(PhoneNumberTextWatcherTwo(v.etContact));
        // v.etContact.setHint("xx-xxxx-xxxx");
//        Selection.setSelection(v.etContact.getText(), v.etContact.getText().length);
//        v.etContact.addTextChangedListener(object : TextWatcher {
//            private var mFormatting: Boolean = false // a flag that prevents stack overflows.
//            private var mAfter: Int = 0
//            override fun afterTextChanged(s: Editable)
//            {
//                if (!mFormatting) {
//                    mFormatting = true;
//                    // using US formatting.
//                    if(mAfter != 0) // in case back space ain't clicked.
//                        PhoneNumberUtils.formatNumber(s,PhoneNumberUtils.getFormatTypeForLocale(Locale.US));
//                    mFormatting = false;
//                }
//                // v.etContact.setText("$" +   v.etContact.getText().toString());
//
////                if(!s.toString().startsWith("+55")){
////                    //v.etContact.setText("+55");
////                    Selection.setSelection( v.etContact.getText(),  v.etContact.getText().length);
////                }
//
//            }
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, after: Int) {
//                mAfter = after;
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//        })

        clickListeners()

//        dob.text = SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            dob.text = sdf.format(cal.time)

        }

        dob.setOnClickListener {
            DatePickerDialog(activity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

//        dob.setOnClickListener(View.OnClickListener {
//
//            val calender = Calendar.getInstance()
//            val year = calender.get(Calendar.YEAR)
//            val month = calender.get(Calendar.MONTH)
//            val day = calender.get(Calendar.DAY_OF_MONTH)
//
//            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//                // Display Selected date in Toast
//                Toast.makeText(activity, """$dayOfMonth - ${monthOfYear + 1} - $year""", Toast.LENGTH_LONG).show()
//
//            }, year, month, day)
//            dpd.show()
//
//        })

        return v
    }


    fun show_toasr() {
        Toast.makeText(context, "TOUCHED", Toast.LENGTH_LONG).show()
    }

    override fun showRecyclerView() {
        recyclerview_location.visibility = View.VISIBLE
    }

    override fun showRecyclerViewgone() {

        recyclerview_location.visibility = View.GONE

    }

    private fun clickListeners() {
        btnNext.setOnClickListener {
            if (activity != null && isAdded) {
                mPresenter.getDetailsOfScreen1(activity)
            }
        }

        v.ivCamera.setOnClickListener {
            selectImage()
        }
        v.ivBack.setOnClickListener {
            signup1.visibility = View.VISIBLE
            signup2.visibility = View.GONE
        }

    }
//
//    override fun movetoscreen2(
//        username: String,
//        cpf: String,
//        contact: String,
//        image: File?,
//        user_type: String
//    ) {
//        signup1.visibility = View.GONE
//        signup2.visibility = View.VISIBLE
//        btnSignUp.setOnClickListener {
//            if (activity != null && isAdded) {
//                if (user_type.equals("1")) {
//                    mPresenter.getDetailsOfScreen2(
//                        username,
//                        cpf,
//                        contact,
//                        image,
//                        activity,
//                        selected_user_type,
//                        selected_gender_type,
//                        dob
//                    )
//                } else {
//                    mPresenter.getDetailsOfScreen2_UserType(
//                        username,
//                        cpf,
//                        contact,
//                        image,
//                        activity,
//                        selected_user_type,
//                        selected_gender_type,
//                        dob
//                    )
//                }
//            }
//        }
//    }



    override fun movetoscreen2(
        username: String,
        cpf: String,
        contact: String,
        image: File?,
        user_type: String
    ) {
        signup1.visibility = View.GONE
        signup2.visibility = View.VISIBLE
        btnSignUp.setOnClickListener {
            if (activity != null && isAdded) {
                if (user_type.equals("1")) {
                    mPresenter.getDetailsOfScreen2(
                        username,
                        cpf,
                        contact,
                        image,
                        activity,
                        selected_user_type
                    )
                }
                else {
                    mPresenter.getDetailsOfScreen2_UserType(
                        username,
                        cpf,
                        contact,
                        image,
                        activity,
                        selected_user_type
                    )
                }
            }
        }

    }


    override fun getUserIdD(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString(Constants.USER_ID, "")!!
        }
        return ""
    }


    override fun getUsername(): String {
        return v.etName.text.toString().trim()
    }

    override fun getUserType(): String {
        //selected_user_type
        return selected_user_type!!
    }

    override fun getCpfTwo(): String {
        val sharedPreferences = context?.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("CPF_NUMBER", EMPTY)
        v.etcpf.setText(name)
        Log.e("CPF_NUMBER", name)
        Log.e("CPF_NUMBER", v.etfcm.text.toString().trim())
        return final_value_cpf
    }


    override fun getContact(): String {
        return v.etContact.text.toString().trim()
    }

    override fun gefcmtoken(): String {
        val sharedPreferences = context?.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("FCM_TOKEN_VALUE", EMPTY)
        v.etfcm.setText(name)
        Log.e("TOKEN_IS_SIGN_UP", name)
        Log.e("TOKEN_IS_SIGN_UP22", v.etfcm.text.toString().trim())
        return v.etfcm.text.toString().trim()
    }

    override fun showError(message: String) {
        if (activity != null && isAdded) {
            Constants.showAlert(activity!!, message)// 12 aug
        }
    }


    override fun getDeviceType(): String {
        return Constants.DEVICE_TYPE
    }

    override fun getDeviceToken(): String {
        if (activity != null && isAdded) {
            return Constants.getDeviceId(activity!!)
        }
        return ""
    }

    override fun getGender(): String {
        return selected_gender_type!!
    }

    override fun getDOB(): String {
        return dob.text.toString().trim()
    }


    override fun getLocationArray(locationsArray: ArrayList<SignUpRequest_ChlidDataModel>): ArrayList<SignUpRequest_ChlidDataModel> {
        var selection_multiple_locations = ArrayList<SignUpRequest_ChlidDataModel>();
        selected_location
        for (i in 0 until selected_location.size) {
            var model = SignUpRequest_ChlidDataModel()
            model.brand = selected_location[i].brand_value
            model.location = selected_location.get(i).location_name_value
            model.city = selected_location.get(i).city_value
            model.state = selected_location.get(i).state_value
            model.locationID = selected_location.get(i).id_value
            selection_multiple_locations.add(model);
            Log.e("SELECTED_POSITION4", selection_multiple_locations.size.toString())
        }
        return selection_multiple_locations
    }


    override fun getBrand(): String {


        if (v.spinnerBrands.selectedItemPosition == 0) {
            Toast.makeText(context, "Select brand for registration", Toast.LENGTH_SHORT)
                .show()
        }
//        else {
//          selected_location_name_two
//        }
        return select_brand_name_two!!

    }

    override fun getState(): String {
        if (v.spinnerStates.selectedItemPosition == 0) {
            Toast.makeText(context, "Select value brand for registration", Toast.LENGTH_SHORT)
                .show()
        }
//        else
//        {
//select_state_name_two
//
//
//        }
        return select_state_name_two

    }

    override fun getCity(): String {
        if (v.spinnerCities.selectedItemPosition == 0) {
            Toast.makeText(context, "Select city for registration", Toast.LENGTH_SHORT)
                .show()
        }
//        else {
//            select_city_name_two
//        }
        return select_city_name_two!!
    }


    /*get location for user type 1*/
    override fun getLocation(): String {

        if (v.spinnerLocations.selectedItemPosition == 0) {
            Toast.makeText(context, "Select Location for registration", Toast.LENGTH_SHORT)
                .show()
        }
//        else {
//            selected_location_name_two
//        }

        return select_location_id_two


    }

    override fun getEmail(): String {

        return v.etEmail.text.toString().trim()
    }

    override fun getPassword(): String {
        return v.etPassword.text.toString().trim()
    }

    override fun getConfirmedPassword(): String {
        return v.etConfirmPassword.text.toString().trim()
    }

    override fun showLoader() {
        if (activity != null && isAdded) {
            v.progressBar.visibility = View.VISIBLE
            Constants.setNonTouchableFlags(activity!!)
        }
    }

    override fun showImage() {
        if (activity != null && isAdded) {
            v.cpf_img.visibility = View.VISIBLE
            //Constants.setNonTouchableFlags(activity!!)


        }
    }

    override fun hideLoader() {
        if (activity != null && isAdded) {
            v.progressBar.visibility = View.GONE
            Constants.clearNonTouchableFlags(activity!!)
        }
    }

    override fun saveRegisterDataToPrefs(data: JSONObject?, token: String?) {
        if (activity != null && isAdded) {
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.TOKEN, token).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.USER_ID, data!!.optString("id")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.EMAIL, data.optString("email")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.USERNAME, data.optString("username")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.PHONE, data.optString("phone_number")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.ADDRESS, data.optString("address")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.BRAND, data.optString("brand")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.CPF, data.optString("cpf_number")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.LOCATION, data.optString("address_field")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.PROFILE_PIC, data.optString("profile_pic")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.STATE, data.optString("state")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.CITY, data.optString("city"))
                .apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.USER_TYPE, data.optString("user_type")).apply()

            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.GENDER, data.optString("gender")).apply()

            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.DOB, data.optString("dob")).apply()

            Constants.getPrefs(activity!!)!!.edit().putBoolean(Constants.loggedIn, true).apply()
        }

        if (activity != null && isAdded) {
            val intent = Intent(activity, DashBoardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun saveRegisterDataToPrefs_UserTypeTwo(data: JSONObject?, token: String?) {
        if (activity != null && isAdded) {
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.TOKEN, token).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.USER_ID, data!!.optString("id")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.EMAIL, data.optString("email")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.USERNAME, data.optString("username")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.PHONE, data.optString("phone_number")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.ADDRESS, data.optString("address")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.CPF, data.optString("cpf_number")).apply()
            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.PROFILE_PIC, data.optString("profile_pic")).apply()
            // Constants.getPrefs(activity!!)!!.edit().putStringSet(Constants.USER_LOCATION, data.optString("profile_pic")).apply()

            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.USER_TYPE, data.optString("user_type")).apply()

            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.GENDER, data.optString("gender")).apply()

            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.DOB, data.optString("dob")).apply()


            Constants.getPrefs(activity!!)!!.edit().putBoolean(Constants.loggedIn, true).apply()
        }

        if (activity != null && isAdded) {
            val intent = Intent(activity, DashBoardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun saveUerIdToPrefs(data: JSONObject?, user_idd: String?) {
        if (activity != null && isAdded) {

            Constants.getPrefs(activity!!)!!.edit()
                .putString(Constants.USER_ID, data!!.optString("id")).apply()
        }


    }


    override fun showErrorMessage(message: String) {
        if (activity != null && isAdded) {
            Constants.showAlert(activity!!, message!!)//12 aug
        }
    }


    override fun showErrorMessageTwo(message: String) {
        if (activity != null && isAdded) {
            Constants.showAlertTwo(activity!!, message!!)//12 aug

            v.etcpf.setText("")
            v.cpf_img.visibility = View.GONE
        }
    }

    private fun selectImage() {
        if (activity != null && isAdded) {
            val pictureDialogItems = arrayOf("abrir galeria", "tirar foto")
            val mBuilder = AlertDialog.Builder(activity!!)
            mBuilder.setTitle("Escolha um:")
            mBuilder.setSingleChoiceItems(
                pictureDialogItems, -1
            )
            { dialogInterface, i ->
                when (i) {
                    0 -> {
                        chooseImageFromGallery()
                    }

                    1 -> {
                        showLoader()
                        takeImageFromCamera()
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
    }


    fun chooseImageFromGallery() {

        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)

    }


    private fun takeImageFromCamera() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
        showLoader()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        hideLoader()
//        if (resultCode == Activity.RESULT_OK)
//         {
        if (requestCode == GALLERY) {
            if (data != null) {
                try {
                    val selectedImageUri = data!!.data
                    Log.e("", "SELECTED_IMAGE_URI" + selectedImageUri)
                    v.ivImage.visibility = View.VISIBLE
                    v.ivImage.setImageURI(selectedImageUri)
                    val idProofUri = ImageFilePath.getPath(activity, selectedImageUri)
                    imageFile = File(idProofUri)
                    imageFile = Constants.saveBitmapToFile(imageFile!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            v.ivImage!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()

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
                context,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null
            )
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())
            val idProofUri = f.getAbsolutePath()
            imageFile = File(idProofUri)
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
                activity!!.sendBroadcast(scanFileIntent)
                fo.write(bytes.toByteArray())
                fo.close()
                val matrix = Matrix()
                matrix.postRotate(
                    Constants.getCameraPhotoOrientation(
                        activity!!,
                        uri,
                        destination
                    ).toFloat()
                )
                val rotated = Bitmap.createBitmap(
                    thumbnail, 0, 0, thumbnail.width, thumbnail.height,
                    matrix, true
                )
                val url = MediaStore.Images.Media.insertImage(
                    activity!!.contentResolver,
                    rotated,
                    "attachment",
                    null
                )
                val outPutfileUri = Uri.parse(url)
                v.ivImage.visibility = View.VISIBLE
                v.ivImage.setImageURI(outPutfileUri)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return destination
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


    override fun getImageFile(): File? {
        if (imageFile != null) {
            return imageFile!!
        }
        return null
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private fun requestMultiplePermissions() {
        if (activity != null && isAdded) {
            Dexter.withActivity(activity!!)
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
    }

    override fun validcpf() {


    }

//    override fun movetoscreen2(
//        username: String,
//        cpf: String,
//        contact: String,
//        image: File?,
//        user_type: String
//    ) {
//
//    }


    /*for brand*/
    fun BrandList() {
        showLoader()


        //  mAPIService!!.Update_Profile(auto_token,user_id,part,name,location).enqueue(object : Callback<UpdateProfileResponse> {
        mAPIService!!.Brand_List().enqueue(object :
            Callback<BrandListResponse> {

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
                        // v.spinnerLocations

                        var brandspinnerAdapter = BrandSpinnerAdapter(context!!, body)

                        v.spinnerBrands?.adapter = brandspinnerAdapter

                        v.spinnerBrands.onItemClickListener

                        v.spinnerBrands.onItemSelectedListener =
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
                                    location_values_array.setBrand(select_brand_name_two)
                                    if (position == 0) {
                                        Log.e("HI", "HI" + position)
                                    } else {
                                        StateList(select_brand_name_two)

                                    }
                                }

                            }


                    } else {
                        Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT)
                            .show()
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
                        // v.spinnerLocations

                        var spinnerAdapter = StateSpinnerAdapter(context!!, body)

                        v.spinnerStates?.adapter = spinnerAdapter

                        v.spinnerStates.onItemClickListener

                        v.spinnerStates.onItemSelectedListener =
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
                                    location_values_array.setState(select_state_name_two)
                                    if (position == 0) {

                                        Log.e("HI", "HI" + position)
                                    } else {

                                        CityList(select_state_name_two, select_brand_name_two)
                                    }
                                }

                            }
                    } else {
                        Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT)
                            .show()
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
                        // v.spinnerLocations

                        var spinnerAdapter = CitySpinnerAdapter(context!!, body)

                        v.spinnerCities?.adapter = spinnerAdapter

                        v.spinnerCities?.onItemClickListener

                        v.spinnerCities?.onItemSelectedListener =
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
                                    location_values_array.setState(select_city_name_two)

                                    if (position == 0) {

                                        Log.e("HI", "HI" + position)
                                    } else {

                                        LocationList(
                                            select_city_name_two,
                                            select_brand_name_two,
                                            selected_user_type
                                        )
                                    }
                                }
                            }
                    } else {
                        Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT)
                            .show()
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

        var get_client_request = LocationListRequest(city!!, brand!!, user_type)
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
                        // v.spinnerLocations

                        var spinnerAdapter = SpinnerAdapter(context!!, body)

                        v.spinnerLocations?.adapter = spinnerAdapter

                        v.spinnerLocations.onItemClickListener

                        v.spinnerLocations.onItemSelectedListener =
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
                                    select_location_name_two = body.get(position).name
                                    select_location_id_two = body.get(position).id
                                    var selected_location_status = body.get(position).status

                                    if (position == 0) {

                                        Log.e("HI", "HI" + position)
                                    } else {
                                        if (selected_user_type != "1") {
                                            if (selected_location_status.equals("0")) {

                                                Log.e("HI", "HI" + position)

                                                location_values_array.setId(select_location_id_two)
                                                location_values_array.setNames(
                                                    select_location_name_two
                                                )
                                                location_values_array.setBrand(select_brand_name_two)
                                                location_values_array.setCity(select_city_name_two)
                                                location_values_array.setStatus(
                                                    selected_location_status
                                                )
                                                location_values_array.setState(select_state_name_two)
                                                selected_location.add(location_values_array)

                                                recyclerview_location.visibility =
                                                    View.VISIBLE
                                                Log.e("CLICKED11", "CLICKED11")
                                                recyclerview_location.layoutManager =
                                                    LinearLayoutManager(
                                                        context,
                                                        RecyclerView.HORIZONTAL,
                                                        false
                                                    )
                                                recyclerview_location.adapter =
                                                    Location_Adapter(
                                                        selected_location,
                                                        context!!
                                                    )
                                            } else {


                                                showErrorMessage("localização não disponível")
                                                var spinnerAdapter =
                                                    SpinnerAdapter.ItemRowHolder(view)
                                                spinnerAdapter.label.setText("Selecione o nome do local")


                                            }
                                        }

                                    }
                                }


                            }


                    } else {
                        Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT)
                            .show()
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

    fun deleteText() {

    }

}
