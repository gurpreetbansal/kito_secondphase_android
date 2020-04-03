package com.diggytech.kinoplasticpremios.Login

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.Login.SignIn.SignInFragment
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.Settings.PrivacyPolicy.PrivacyPolicyActivity
import com.diggytech.kinoplasticpremios.Settings.TermsConditions.TermsActivity
import com.diggytech.kinoplasticpremios.SignUpFragmentTwo
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    var token: String? = ""
    private var SelectedLanguage: String? = null
    private val PERMISSION_REQUEST_CODE = 200
    private var myPreferences = "myPrefs"
    private var EMPTY = "";

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        SelectedLanguage = intent.getStringExtra("SelectedLanguage")

        // for permissions
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {

            }

        }




        firebasetoken()


        //requestMultiplePermissions()

        loadFragment(SignInFragment())
        //gotoFragment(SignInFragment())

        signin.setOnClickListener {
            Constants.onHideKeyboard(this)
           // scrollView.smoothScrollTo(0, 0)
            //gotoFragment(SignInFragment())
            loadFragment(SignInFragment())

            // firebasetoken()
        }



        signup.setOnClickListener {
            Constants.onHideKeyboard(this)
           // scrollView.smoothScrollTo(0, 0)
           // gotoFragment(SignUpFragmentTwo())
            loadFragment(SignUpFragmentTwo())
            //firebasetoken()
        }


//        dontHaveAnAccount.setOnClickListener {
//            Constants.onHideKeyboard(this)
//            scrollView.smoothScrollTo(0, 0)
//            //gotoFragment(SignUpFragmentTwo())
//            loadFragment(SignUpFragmentTwo())
//        }


        val ss =
            SpannableString(getString(R.string.by_signing_you_are_agreeing_to_the_terms_conditions_and_the_privacy_policy))
        val termsSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        TermsActivity::class.java
                    ).putExtra("web_url", Constants.BASE_URL + "terms_and_conditions")
                )
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
//        val privacySpan = object : ClickableSpan() {
//            override fun onClick(textView: View) {
//                startActivity(
//                    Intent(
//                        this@LoginActivity,
//                        PrivacyPolicyActivity::class.java
//                    ).putExtra("web_url", Constants.BASE_URL + "privacy_policy")
//                )
//            }
//
//            override fun updateDrawState(ds: TextPaint) {
//                super.updateDrawState(ds)
//                ds.isUnderlineText = true
//            }
//        }

        ss.setSpan(termsSpan, 37, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        ss.setSpan(privacySpan, 60, 83, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


//        tvTerms.text = ss
//        tvTerms.movementMethod = LinkMovementMethod.getInstance()
//        tvTerms.highlightColor = Color.TRANSPARENT

    }

    fun firebasetoken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->

                token = task.result?.token
                val sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("FCM_TOKEN_VALUE", token)
                editor.apply()


                val name = sharedPreferences.getString("FCM_TOKEN_VALUE", EMPTY)
                Log.e("TOKEN_IS_33", name)

            })
    }

    fun gotoFragment(fragment:Fragment) {
        var bundle = Bundle()
        bundle.putString("token", token)
        fragment.arguments = bundle
        supportFragmentManager!!.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }
    private fun loadFragment(fragment: Fragment) {
        var bundle = Bundle()
        bundle.putString("token", token)


      //  Log.e("TOKEN_IS", new_token.toString())

//        alreadyHaveAnAccount.isEnabled = true
//        dontHaveAnAccount.isEnabled = true
//        dontHaveAnAccount.setOnClickListener {
//            scrollView.smoothScrollTo(0, 0)
//            loadFragment(SignUpFragmentTwo())
//        }
//
//        alreadyHaveAnAccount.setOnClickListener {
//            scrollView.smoothScrollTo(0, 0)
//            loadFragment(SignInFragment())
//        }


        try {
            if (fragment is SignInFragment) {
                signin.alpha = 1f
                signup.alpha = 0.5f
//                dontHaveAnAccount.visibility = View.VISIBLE
//                alreadyHaveAnAccount.visibility = View.INVISIBLE
//                alreadyHaveAnAccount.isEnabled = false
//                alreadyHaveAnAccount.isClickable = false
            } else if (fragment is SignUpFragmentTwo) {
                signin.alpha = 0.5f
                signup.alpha = 1f
//                alreadyHaveAnAccount.visibility = View.VISIBLE
//                dontHaveAnAccount.visibility = View.INVISIBLE
//                dontHaveAnAccount.isEnabled = false
//                dontHaveAnAccount.isClickable = false
            }

            val fm = supportFragmentManager
            val fragmentTransaction = fm.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayout, fragment)
            fragmentTransaction.commit() // save the changes
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private fun requestMultiplePermissions() {

        Dexter.withActivity(this@LoginActivity)
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


            }).withErrorListener {
                Toast.makeText(this@LoginActivity, "error", Toast.LENGTH_SHORT).show()
            }
            .onSameThread()
            .check()
    }


}
