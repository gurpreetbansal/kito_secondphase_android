package com.diggytech.kinoplasticpremios.Login.SignIn


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.DashBoard.DashBoardActivity
import com.diggytech.kinoplasticpremios.LocationsModel
//import com.diggytech.kinoplasticpremios.LocationsModel
import com.diggytech.kinoplasticpremios.Login.ForgotPassword.ForgotPasswordActivity
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.RedeemPrizes.GiftCards.GiftCardsActivity
import com.diggytech.kinoplasticpremios.SelectLocation.SelectLocationActivity
import com.facebook.*
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
//import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_signup2.view.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.etPassword
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class SignInFragment : Fragment(), SignInContract.View {
//    override fun save_userpe(): String {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }


    private val TAG: String = "keyhash"
    private lateinit var mPresenter: SignInPresenter
    lateinit var view1: View
    internal lateinit var callbackManager: CallbackManager
    internal lateinit var accessToken: AccessToken
    internal lateinit var accessTokenTracker: AccessTokenTracker
    internal lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 985



    var fb_user_name: String = ""
    var fb_user_id: String = ""
    var fb_user_email: String = ""
    var fb_user_password: String = ""
    var fb_user_bitmap: String = ""
    var fb_user_link: String = ""
    var android_id:String=""


    private var myPreferences = "myPrefs"
    private var EMPTY = "";

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_sign_in, container, false)

        FacebookSdk.sdkInitialize(context)





        android_id = Settings.Secure.getString(context?.getContentResolver(), Settings.Secure.ANDROID_ID);
        // Log.e("My ID is: "+" " + android_id);
        Log.e("My_ID_is:","" + android_id)

        val ivEmail = view1.findViewById<SimpleDraweeView>(R.id.ivEmail)
        val ivLock = view1.findViewById<SimpleDraweeView>(R.id.ivLock)
        val facebook = view1.findViewById<SimpleDraweeView>(R.id.facebook)
        val google = view1.findViewById<SimpleDraweeView>(R.id.google)
        val btnSignIn = view1.findViewById<TextView>(R.id.btnSignIn)
        val tvForgotPass = view1.findViewById<TextView>(R.id.tvForgotPass)

//        ivEmail.setActualImageResource(R.drawable.email)
//        ivLock.setActualImageResource(R.drawable.lock)
//        facebook.setActualImageResource(R.drawable.facebook)
//        google.setActualImageResource(R.drawable.google)

        mPresenter = SignInPresenter(this)


        callbackManager = CallbackManager.Factory.create()
        printHashKey()

        // facebook
        accessTokenTracker = object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
                if (currentAccessToken != null) {
                    accessToken = currentAccessToken
                }
            }
        }

        googleSignInsetUp()


        if (activity != null) {
            activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }
      //  mPresenter.getAllLocations()
        btnSignIn.setOnClickListener {
            if (activity != null && isAdded) {
                mPresenter.callLoginService(activity)

            }
        }

        tvForgotPass.setOnClickListener {
            if (activity != null && isAdded) {
                Constants.onHideKeyboard(activity!!)
                val intent = Intent(activity, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }
        }

//        facebook.setOnClickListener {
////            if (activity != null && isAdded)
////            {
////                Constants.onHideKeyboard(activity!!)
////                Constants.getPrefs(activity!!)!!.edit().putString(Constants.SOCIAL_TYPE, "facebook").apply()
////              //  mPresenter.callfacebookLoginApi("","","","","")
////                mPresenter.setUpFacebookSignUp()
////
////
////            }
//
//
//          //  callbackManager = CallbackManager.Factory.create()
//            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
//            LoginManager.getInstance().registerCallback(callbackManager,
//                object : FacebookCallback<LoginResult> {
//                    override fun onSuccess(loginResult: LoginResult) {
//                        Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
//                        Log.d("FBLOGIN", loginResult.accessToken.token.toString())
//                        Log.d("FBLOGIN", loginResult.recentlyDeniedPermissions.toString())
//                        Log.d("FBLOGIN", loginResult.recentlyGrantedPermissions.toString())
//
//                        val request: GraphRequest = GraphRequest.newMeRequest(
//                            loginResult.accessToken,
//                            GraphRequest.GraphJSONObjectCallback { obj: JSONObject, response: GraphResponse ->
//                                fb_user_name = obj.getString("name")
//                                fb_user_id = obj.getString("id")
//                                fb_user_email = obj.getString("email")
//                                fb_user_bitmap = "https://graph.facebook.com/" + fb_user_id + "/picture";
//                                Log.e(TAG, "NamePrint>>>>>>>" + fb_user_name + " " +fb_user_id + " " + fb_user_email + " " + fb_user_password + " " + fb_user_link + " " + fb_user_bitmap
//                                );
//                                mPresenter.callfacebookLoginApi(fb_user_email,"facebook",fb_user_id,"android",android_id,"GM","AC")
//                            })
//                        val parameters = Bundle()
//                        parameters.putString("fields", "name,email,id,picture.type(large)")//,password ,link
//                        request.parameters = parameters
//                        request.executeAsync()
//                    }
//                    override fun onCancel() {
//                        Log.d("MainActivity", "Facebook onCancel.")
//                    }
//                    override fun onError(error: FacebookException) {
//                        Log.d("MainActivity", "Facebook onError.")
//                    }
//                })
//
//
//
//        }
//        google.setOnClickListener {
//            if (activity != null && isAdded) {
//                Constants.onHideKeyboard(activity!!)
//                Constants.getPrefs(activity!!)!!.edit().putString(Constants.SOCIAL_TYPE, "gmail").apply()
//                mPresenter.setUpGoogleSignUp()
//            }
//        }



        return view1
    }


    // google sign in
    private fun googleSignInsetUp() {
        if (activity != null && isAdded) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

            mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)
        }
    }

//    override fun getTextEmpty(){
//         view1.etName.setText("")
//        view1.etPassword.setText("")
//    }
    override fun getEmail(): String {
        return view1.etName.text.toString().trim()
    }

    override fun getPassword(): String {
        return view1.etPassword.text.toString().trim()
    }
//    override fun getfcmtoken(): String {
//        return Constants.FCMTOKEN
//    }


    override fun getDeviceType(): String {
        return Constants.DEVICE_TYPE
    }

    override fun getDeviceToken(): String {
        if (activity != null && isAdded) {
            return Constants.getDeviceId(activity!!)
        }
        return ""
    }


    override fun showError(message: String) {
        if (activity != null && isAdded) {
            Constants.showAlert(activity!!, message)//12aug
        }
    }


    override fun showLoader() {
        if (activity != null && isAdded) {
            view1.progressBar.visibility = View.VISIBLE
            Constants.setNonTouchableFlags(activity!!)
        }
    }

    override fun hideLoader() {
        if (activity != null && isAdded) {
            view1.progressBar.visibility = View.GONE
            Constants.clearNonTouchableFlags(activity!!)
        }
    }

    override fun saveLoginDataToPrefs(data: JSONObject?, token: String?) {
        if (activity != null && isAdded) {
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.TOKEN, token).apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.USER_ID, data!!.optString("id")).apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.USER_TYPE, data!!.optString("user_type_value")).apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.EMAIL, data.optString("email")).apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.USERNAME, data.optString("username")).apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.PHONE, data.optString("phone_number")).apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.ADDRESS, data.optString("address")).apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.BRAND, data.optString("brand")).apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.CPF, data.optString("cpf_number")).apply()

            Constants.getPrefs(activity!!)!!.edit().putString(Constants.LOCATION, data.optString("address_field")).apply()

            //Constants.getPrefs(activity!!)!!.edit().putString(Constants.LOCATION_ID, data.optString("id_location")).apply()

            Constants.getPrefs(activity!!)!!.edit().putString(Constants.PROFILE_PIC, data.optString("profile_pic"))
                .apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.STATE, data.optString("state"))
                .apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.CITY, data.optString("city")).apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.USER_TYPE, data.optString("user_type")).apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.GENDER, data.optString("gender")).apply()
            Constants.getPrefs(activity!!)!!.edit().putString(Constants.DOB, data.optString("dob")).apply()

           // Constants.getPrefs(activity!!)!!.edit().putString(Constants.DEVICE_TYPE, data.optString("device_type")).apply()
           // Constants.getPrefs(activity!!)!!.edit().putString(Constants.DEVICE_TOKEN, data.optString("device_token")).apply()

            Constants.getPrefs(activity!!)!!.edit().putBoolean(Constants.loggedIn, true).apply()
        }

        if (activity != null && isAdded) {
            Constants.onHideKeyboard(activity!!)
            val intent = Intent(activity, DashBoardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun showErrorMessage(message: String?) {
        if (activity != null && isAdded) {
            Constants.showAlert(activity!!, message!!)//12 aug
        }
    }
    override fun gefcmtoken(): String {
        val sharedPreferences = context?.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("FCM_TOKEN_VALUE", EMPTY)
        view1.etfcm_login.setText(name)
        Log.e("TOKEN_IS_SIGN_UP", name)
        Log.e("TOKEN_IS_SIGN_UP22", view1.etfcm_login.text.toString().trim())
        return view1.etfcm_login.text.toString().trim()
    }
    override fun setFacebookSignUp() {
        try {
            val tok = AccessToken.getCurrentAccessToken().token
            if (tok != null) {
                LoginManager.getInstance().logOut()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult)
                {
                    Log.i("loginResult", Gson().toJson(loginResult))
                    accessToken = loginResult.accessToken
                    if (AccessToken.getCurrentAccessToken().token != null) {
                        RequestData(loginResult)
                        val intent = Intent(context, DashBoardActivity::class.java)
                        startActivity(intent)
                    }
                }

                override fun onCancel() {}

                override fun onError(exception: FacebookException) {
                    Log.i("loginResult", exception.toString())
                }
            })
    }

    // facebook get hash key
    fun printHashKey() {
        try {
            val info = activity!!.packageManager.getPackageInfo(activity!!.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                //Log.e(TAG, "printHashKey() Hash Key: $hashKey")
                Log.e(TAG, "printHashKey()Hash Key:"+ "  "+ hashKey)
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "printHashKey()", e)
        } catch (e: Exception) {
            Log.e(TAG, "printHashKey()", e)
        }

    }

    // facebook request data
    private fun RequestData(
        loginResult: LoginResult
    ) {
        val request = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
            val json = response.jsonObject
            if (json != null) {
                //  println("Json data :" + json!!)
                try {
                    if (json.optString("email") == "" || json.optString("email") == null) {
                        /* Constants.showFacebookEmailAlert(
                             this@RegisterActivity,
                             "Please go through the link and verify your facebook email to continue login into the app"
                         )*/

                    } else {

                        // rest of the data
                        val facebookId = json.optString("id")
                        val profilePictureUrl = (URL("https://graph.facebook.com/$facebookId/picture?type=normal"))
                            .toString()
                        var fbUserName = json.optString("name")
                        val splited = fbUserName.split(("\\s+").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val first_name = splited[0]
                        val last_name = splited[1]
                        val username = first_name + " " + last_name
                        val device_type = Constants.DEVICE_TYPE
                        val social_type = Constants.getPrefs(activity!!)!!.getString(Constants.SOCIAL_TYPE, "")
                        val device_token = Constants.getDeviceId(activity!!)
                        val email = json.optString("email")

                        Log.e("","facebook_data"+"  "+fbUserName+""+""+first_name+" "+device_type+" "+social_type+" "+email)


                        mPresenter.callSignUpService(
                            device_type, device_token, username, "", "", "", ""
                            , email, "", social_type!!, facebookId,
                        "","")

                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,link,email,picture")
        request.parameters = parameters
        request.executeAsync()
    }

    override fun setGoogleSignUp() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }

    }


    // google sign in result
    private fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            // Log.i("googleInfo", Gson().toJson(account))

            val social_id = account!!.id
            val email = account.email
            val firstName = account.givenName
            val lastName = account.familyName
            // profilePictureUrl = account.photoUrl.toString()
            val first_name = "$firstName"
            val last_name = "$lastName"
            val username = first_name + " " + last_name
            val device_type = Constants.DEVICE_TYPE
            val social_type = Constants.getPrefs(activity!!)!!.getString(Constants.SOCIAL_TYPE, "")
            val device_token = Constants.getDeviceId(activity!!)



            mPresenter.callSignUpService(
                device_type, device_token, username, "", "", "", ""
                , email!!, "", social_type!!, social_id!!,"",""
            )

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //  Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            // updateUI(null);
        }

    }

    override fun saveLocations(locations: MutableList<LocationsModel>) {
        if (activity != null && isAdded) {
            Constants.saveLocations(locations, activity!!)
        }
    }
}
