package com.diggytech.kinoplasticpremios.DashBoard

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
//import android.support.annotation.RequiresApi
//import android.support.v4.app.Fragment
//import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.diggytech.kinoplasticpremios.Campaign.CampaignFragment
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.LocationsModel
import com.diggytech.kinoplasticpremios.MySpace.MySpaceFragment
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.RedeemPrizes.RedeemPrizesFragment
import com.diggytech.kinoplasticpremios.SelectLocation.SelectLocationActivity
import com.diggytech.kinoplasticpremios.Support.SupportFragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.bottom_navigation_layout.*

import android.content.res.Configuration
import android.preference.PreferenceManager
import android.util.Log
import android.view.Window
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.diggytech.kinoplasticpremios.Newsletter.NewsLetterFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class DashBoardActivity : AppCompatActivity(), DashBoardContract.View {

    private lateinit var mPresenter: DashBoardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        var user_type = Constants.getPrefs(this)!!.getString(Constants.USER_TYPE, "")
        // var from_where_come = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_TYPESS", "")
        Log.e("USER_TYPE_IS", user_type)

        ivPlus.setActualImageResource(R.drawable.rounded_plus)
        mPresenter = DashBoardPresenter(this)
        mPresenter.getAllLocations()

        mPresenter.setUpBottomNavigationLayout()
        loadFragment(MySpaceFragment())

        requestMultiplePermissions()

        if (user_type.equals("1")) {
            ivPlus.visibility = View.VISIBLE
            ivPlus.setOnClickListener {
                val intent = Intent(this@DashBoardActivity, SelectLocationActivity::class.java)
                startActivity(intent)
            }
        } else {
            ivPlus.visibility = View.GONE
        }


    }


    override fun setUpBottomNavigation() {
        mySpaceTab.setOnClickListener {
            ivMySpace.setActualImageResource(R.drawable.nav_my_space)
            tvMySpace.setTextColor(resources.getColor(R.color.colorYellow))

            ivCampaign.setActualImageResource(R.drawable.nav_campaign_white)
            tvCampaign.setTextColor(resources.getColor(R.color.colorWhite))

            ivRedeem.setActualImageResource(R.drawable.nav_redeem_points_white)
            tvRedeem.setTextColor(resources.getColor(R.color.colorWhite))

            ivSupport.setActualImageResource(R.drawable.newsletter_white)
            tvSupport.setTextColor(resources.getColor(R.color.colorWhite))

//            ivSupport.setActualImageResource(R.drawable.nav_support_white)
//            tvSupport.setTextColor(resources.getColor(R.color.colorWhite))

            loadFragment(MySpaceFragment())

        }



        campaignTab.setOnClickListener {
            ivMySpace.setActualImageResource(R.drawable.nav_my_space_white)
            tvMySpace.setTextColor(resources.getColor(R.color.colorWhite))

            ivCampaign.setActualImageResource(R.drawable.nav_campaign)
            tvCampaign.setTextColor(resources.getColor(R.color.colorYellow))

            ivRedeem.setActualImageResource(R.drawable.nav_redeem_points_white)
            tvRedeem.setTextColor(resources.getColor(R.color.colorWhite))

            ivSupport.setActualImageResource(R.drawable.newsletter_white)
            tvSupport.setTextColor(resources.getColor(R.color.colorWhite))

//            ivSupport.setActualImageResource(R.drawable.nav_support_white)
//            tvSupport.setTextColor(resources.getColor(R.color.colorWhite))

            loadFragment(CampaignFragment())
        }




        redeemTab.setOnClickListener {
            ivMySpace.setActualImageResource(R.drawable.nav_my_space_white)
            tvMySpace.setTextColor(resources.getColor(R.color.colorWhite))

            ivCampaign.setActualImageResource(R.drawable.nav_campaign_white)
            tvCampaign.setTextColor(resources.getColor(R.color.colorWhite))

            ivRedeem.setActualImageResource(R.drawable.nav_redeem_points)
            tvRedeem.setTextColor(resources.getColor(R.color.colorYellow))


            ivSupport.setActualImageResource(R.drawable.newsletter_white)
            tvSupport.setTextColor(resources.getColor(R.color.colorWhite))

            // ivSupport.setActualImageResource(R.drawable.nav_support_white)
            //  tvSupport.setTextColor(resources.getColor(R.color.colorWhite))

            loadFragment(RedeemPrizesFragment())

        }




        supportTab.setOnClickListener {
            ivMySpace.setActualImageResource(R.drawable.nav_my_space_white)
            tvMySpace.setTextColor(resources.getColor(R.color.colorWhite))

            ivCampaign.setActualImageResource(R.drawable.nav_campaign_white)
            tvCampaign.setTextColor(resources.getColor(R.color.colorWhite))

            ivRedeem.setActualImageResource(R.drawable.nav_redeem_points_white)
            tvRedeem.setTextColor(resources.getColor(R.color.colorWhite))

            ivSupport.setActualImageResource(R.drawable.newsletter)
            tvSupport.setTextColor(resources.getColor(R.color.colorYellow))

//            ivSupport.setActualImageResource(R.drawable.nav_support)
//            tvSupport.setTextColor(resources.getColor(R.color.colorYellow))

            //  loadFragment(SupportFragment())
            loadFragment(NewsLetterFragment())

        }


    }


    private fun loadFragment(fragment: Fragment) {
        try {
            val fm = supportFragmentManager
            val fragmentTransaction = fm.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private fun requestMultiplePermissions() {
        try {
            Dexter.withActivity(this@DashBoardActivity)
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
                })
                .withErrorListener {
                    Toast.makeText(
                        this@DashBoardActivity,
                        "Some Error! ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .onSameThread()
                .check()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
        Constants.setNonTouchableFlags(this@DashBoardActivity)
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
        Constants.clearNonTouchableFlags(this@DashBoardActivity)
    }

    override fun showError(error: String) {
        Constants.showAlert(this@DashBoardActivity, error)
    }

    override fun showErrorMessage(message: String?) {
        //Constants.showAlert(this@DashBoardActivity, message!!)
    }

    override fun saveLocations(data: MutableList<LocationsModel>) {
        Constants.saveLocations(data, this@DashBoardActivity)
    }

    override fun onBackPressed() {

    }


}