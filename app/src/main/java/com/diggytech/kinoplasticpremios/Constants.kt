package com.diggytech.kinoplasticpremiosimport com.google.gson.Gsonimport android.annotation.SuppressLintimport android.app.Activityimport android.app.AlertDialogimport android.content.Contextimport android.content.SharedPreferencesimport android.graphics.Bitmapimport android.graphics.BitmapFactoryimport android.graphics.Colorimport android.graphics.Typefaceimport android.media.ExifInterfaceimport android.net.Uriimport android.provider.Settingsimport android.util.Base64import android.util.Logimport android.view.Gravityimport android.view.Viewimport android.view.ViewGroupimport android.view.WindowManagerimport android.view.inputmethod.InputMethodManagerimport android.widget.LinearLayoutimport android.widget.Spinnerimport android.widget.TextViewimport com.google.gson.reflect.TypeToken//import com.google.gson.Gson//import com.google.gson.reflect.TypeTokenimport okhttp3.Interceptorimport okhttp3.OkHttpClientimport java.io.ByteArrayOutputStreamimport java.io.Fileimport java.io.FileInputStreamimport java.io.FileOutputStreamimport java.util.concurrent.TimeUnitimport java.util.regex.Patternclass Constants {    companion object {        const val DEVICE_ID: String = "device_id"        const val SOCIAL_TYPE: String = "social_type"        var loggedIn: String = "logged_in"        const val DEVICE_TYPE: String = "android"        const val LocationsArray: String = "locations_array"//       val BASE_URL: String = "http://68.183.74.38:8008/joao/api/"//       val BASE_URL: String = "https://clientstagingdev.com/joao/api/"       val BASE_URL: String = "https://conselho.fashion/api/"       //val BASE_URL: String = "https://platinumitmedia.com/kinoplastic_server/api/"       //val BASE_URL: String = "https://diggy.tech/api/"        //val BASE_URL: String = "https://clientstagingdev.com/kinoplastic_server/api/"        // register constants        const val mStripeCustomerId: String = "stripe_cust_id"        const val TOKEN: String = "token"        const val FCMTOKEN: String = "fcmtoken"        const val USER_ID: String = "user_id"        const val EMAIL: String = "email"        const val USERNAME: String = "user_name"        const val PHONE: String = "phone_number"        const val ADDRESS: String = "address"        const val BRAND: String = "brand"        const val STATE: String = "state"        const val CITY: String = "city"        const val CPF: String = "cpf"        const val LOCATION: String = "location"        const val PROFILE_PIC: String = "profile_pic"        const val LANGUAGE: String = "language"        const val COUNTRY: String = "country"        const val USER_TYPE: String = "user_type_value"        const val GENDER: String = "gender"        const val DOB: String = "dob"        fun getPrefs(context: Context): SharedPreferences? {            try {                return context.getSharedPreferences("ProfileResponseData", Context.MODE_PRIVATE)            } catch (e: Exception) {                e.printStackTrace()            }            return null        }        fun getHttpClient(userId: String, authorizationToken: String): OkHttpClient {            val httpClient = OkHttpClient.Builder()                .connectTimeout(90, TimeUnit.SECONDS)                .readTimeout(90, TimeUnit.SECONDS).writeTimeout(90, TimeUnit.SECONDS)            httpClient.interceptors().add(Interceptor { chain ->                val original = chain.request()                // Request customization: add request headers                val requestBuilder = original.newBuilder()                    .header("userId", userId)                    .header("AuthorizationToken", authorizationToken)                    .method(original.method(), original.body())                val request = requestBuilder.build()                chain.proceed(request)            })            return httpClient.build()        }        fun getCameraPhotoOrientation(context: Context, imageUri: Uri, imagePath: File): Int {            var rotate = 0            try {                context.contentResolver.notifyChange(imageUri, null)                // File imageFile = new File(imagePath);                val exif = ExifInterface(imagePath.absolutePath)                val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)                when (orientation) {                    ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270                    ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180                    ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90                }                Log.i("RotateImage", "Exif orientation: $orientation")                Log.i("RotateImage", "Rotate value: $rotate")            } catch (e: Exception) {                e.printStackTrace()            }            return rotate        }        // method for bitmap to base64        fun encodeTobase64(image: Bitmap): String {            val baos = ByteArrayOutputStream()            image.compress(Bitmap.CompressFormat.PNG, 100, baos)            val b = baos.toByteArray()            val imageEncoded = Base64.encodeToString(b, Base64.DEFAULT)            Log.d("Image Log:", imageEncoded)            return imageEncoded        }        // method for base64 to bitmap        fun decodeBase64(input: String): Bitmap {            val decodedByte = Base64.decode(input, 0)            return BitmapFactory                .decodeByteArray(decodedByte, 0, decodedByte.size)        }        fun showAlert(context: Activity, message: String) {            if (!(context).isFinishing) {                val builder = AlertDialog.Builder(context)                val title = TextView(context)                title.text = "Kitoplastic"                title.setPadding(10, 30, 10, 10)                title.gravity = Gravity.CENTER                title.setTextColor(Color.BLACK)                title.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD)                title.textSize = 20f                builder.setCustomTitle(title)                    .setMessage(message)                    .setCancelable(true)                    .setPositiveButton(                        "Ok"                    ) { dialog, id ->                        dialog.dismiss()                    }                val alert = builder.create()                alert.show()                val messageView = alert.findViewById<TextView>(android.R.id.message)                messageView.setTextColor(context.resources.getColor(R.color.colorBlack))                messageView.textSize = 19f                messageView.setPadding(50, 50, 50, 50)                messageView.gravity = Gravity.CENTER                val positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE)                val positiveButtonLL = positiveButton.layoutParams as LinearLayout.LayoutParams                positiveButton.setTextColor(context.resources.getColor(R.color.colorBlack))                positiveButtonLL.gravity = Gravity.CENTER                positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT                positiveButton.layoutParams = positiveButtonLL            }        }        fun showAlertTwo(context: Activity, message: String) {            if (!(context).isFinishing) {                val builder = AlertDialog.Builder(context)                val title = TextView(context)                title.text = "Kitoplastic"                title.setPadding(10, 30, 10, 10)                title.gravity = Gravity.CENTER                title.setTextColor(Color.BLACK)                title.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD)                title.textSize = 20f                builder.setCustomTitle(title)                    .setMessage(message)                    .setCancelable(true)                    .setPositiveButton(                        "Ok"                    ) { dialog, id ->                        dialog.dismiss()                    }                val alert = builder.create()                alert.show()                val messageView = alert.findViewById<TextView>(android.R.id.message)                messageView.setTextColor(context.resources.getColor(R.color.colorBlack))                messageView.textSize = 19f                messageView.setPadding(50, 50, 50, 50)                messageView.gravity = Gravity.CENTER                val positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE)                val positiveButtonLL = positiveButton.layoutParams as LinearLayout.LayoutParams                positiveButton.setTextColor(context.resources.getColor(R.color.colorBlack))                positiveButtonLL.gravity = Gravity.CENTER                positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT                positiveButton.layoutParams = positiveButtonLL            }        }        fun showFinishingAlert(context: Activity, message: String) {            if (!(context).isFinishing) {                val builder = AlertDialog.Builder(context)                val title = TextView(context)                title.text = "Kitoplastic"                title.setPadding(10, 60, 10, 10)                title.gravity = Gravity.CENTER                title.setTextColor(Color.BLACK)                title.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD)                title.textSize = 20f                builder.setCustomTitle(title)                    .setMessage(message)                    .setCancelable(false)                    .setPositiveButton(                        "Ok"                    ) { dialog, id ->                        dialog.dismiss()                        context.finish()                    }                val alert = builder.create()                alert.show()                val messageView = alert.findViewById<TextView>(android.R.id.message)                messageView.gravity = Gravity.CENTER                val positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE)                val positiveButtonLL = positiveButton.layoutParams as LinearLayout.LayoutParams                positiveButtonLL.gravity = Gravity.CENTER                positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)                positiveButton.layoutParams = positiveButtonLL            }        }        @SuppressLint("HardwareIds")        fun getDeviceId(context: Context): String {            return Settings.Secure.getString(                context.contentResolver,                Settings.Secure.ANDROID_ID            )            // return getPrefs(context)!!.getString(DEVICE_ID, "")        }        fun isEmailValid(email: String): Boolean {            var isValid = false            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)            val matcher = pattern.matcher(email)            if (matcher.matches()) {                isValid = true            }            return isValid        }        fun setNonTouchableFlags(activity: Activity) {            activity.window?.setFlags(                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE            )        }        fun clearNonTouchableFlags(mActivity: Activity) {            mActivity.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)        }        fun onHideKeyboard(activity: Activity) {            try {                val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager                var view = activity.currentFocus                if (view == null) {                    view = View(activity)                }                imm.hideSoftInputFromWindow(view.windowToken, 0)            } catch (e: Exception) {                e.printStackTrace()            }        }        fun onShowKeyboard(activity: Activity) {            try {                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)            } catch (e: Exception) {                e.printStackTrace()            }        }        fun saveBitmapToFile(file: File): File? {            try {                // BitmapFactory options to downsize the image                val o = BitmapFactory.Options()                o.inJustDecodeBounds = true                o.inSampleSize = 6                // factor of downsizing the image                var inputStream = FileInputStream(file)                //Bitmap selectedBitmap = null;                BitmapFactory.decodeStream(inputStream, null, o)                inputStream.close()                // The new size we want to scale to                val REQUIRED_SIZE = 75                // Find the correct scale value. It should be the power of 2.                var scale = 1                while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE) {                    scale *= 2                }                val o2 = BitmapFactory.Options()                o2.inSampleSize = scale                inputStream = FileInputStream(file)                val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)                inputStream.close()                // here i override the original image file                file.createNewFile()                val outputStream = FileOutputStream(file)                selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)                return file            } catch (e: Exception) {                return null            }        }        fun saveLocations(list: MutableList<LocationsModel>, activity: Activity) {            val listOfObjects = object : TypeToken<List<LocationsModel>>() {            }.type            val gson = Gson()            val strObject = gson.toJson(list, listOfObjects)            getPrefs(activity)!!.edit().putString(LocationsArray, strObject).apply()        }        fun getLocations(activity: Activity): MutableList<LocationsModel>? {            val gson = Gson()            var productFromShared: MutableList<LocationsModel>            val jsonPreferences = getPrefs(activity)!!.getString(LocationsArray, "")            val type = object : TypeToken<MutableList<LocationsModel>>() {            }.type            productFromShared = gson.fromJson(jsonPreferences, type)            return productFromShared        }        fun callSpinner(            applicationContext: Context,            spinner: Spinner,            list: MutableList<LocationsModel>,            position: Int        ) {            val adapter = CustomSpinnerAdapter(                applicationContext,                R.layout.dropdown_recycler_item,                list,                spinner            )            spinner.adapter = adapter            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)            spinner.setSelection(adapter.getPosition(list[position]))        }        fun callSpinnerTwo(            applicationContext: Context,            spinner: Spinner,            list: MutableList<LocationsModel>        ) {            val adapter = CustomSpinnerAdapter( applicationContext,R.layout.dropdown_recycler_item,list,spinner)            adapter.setDropDownViewResource(R.layout.dropdown_recycler_item)            spinner.adapter = adapter            //spinner.setSelection(adapter.getPosition(list[position]))        }    }}