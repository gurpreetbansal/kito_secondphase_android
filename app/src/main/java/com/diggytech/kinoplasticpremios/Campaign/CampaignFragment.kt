package com.diggytech.kinoplasticpremios.Campaign


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.app.soyadeti.Interface.APIService
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.DashBoard.ProfileResponse
import com.diggytech.kinoplasticpremios.DashBoard.ProfileResponseUserLocation
import com.diggytech.kinoplasticpremios.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.fragment_campaign.*
import kotlinx.android.synthetic.main.fragment_campaign.view.*
import kotlinx.android.synthetic.main.view_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class CampaignFragment : Fragment(), CampaignContract.View {

    private var mylist = mutableListOf<ModelCampaign>()
    private lateinit var mPresenter: CampaignPresenter
    private lateinit var recyclerview: androidx.recyclerview.widget.RecyclerView

    lateinit var v: View

    lateinit var auth_token:String
    lateinit var user_id:String
    var mAPIService: APIService? = null
    lateinit var all_location_id:String
    lateinit var  citiesCommaSeparated:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_campaign, container, false)

        val mToolbar = v.findViewById<Toolbar>(R.id.toolbar)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        mTitle!!.text = getString(R.string.mission)

        mAPIService = APIService.ApiUtils.apiService

        auth_token = Constants.getPrefs(context!!)!!.getString(Constants.TOKEN, "")
        user_id = Constants.getPrefs(context!!)!!.getString(Constants.USER_ID, "")

        var user_type = Constants.getPrefs(context!!)!!.getString(Constants.USER_TYPE, "")
        // var from_where_come = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_TYPESS", "")
        Log.e("USER_TYPE_IS", user_type)
        recyclerview = v.findViewById(R.id.recyclerview)
        if(user_type.equals("1"))
        {

        }
        else {
            Profilemethod()
        }
        mPresenter = CampaignPresenter(this)
        mPresenter.callCampaignApi()

        requestMultiplePermissions()
        return v
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private fun requestMultiplePermissions() {
        try {
            Dexter.withActivity(activity)
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


                }).withErrorListener { Toast.makeText(activity, "Some Error! ", Toast.LENGTH_SHORT).show() }
                .onSameThread()
                .check()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getLocationId(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString(Constants.ADDRESS, "")!!
        }
        return ""
    }

    override fun getLocationId_Two(): String {

        return v.txt_values_of_location.text.toString().trim()
//        if (activity != null && isAdded) {
//            return citiesCommaSeparated
//        }
//        return ""
    }



    override fun getUserId(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString(Constants.USER_ID, "")!!
        }
        return ""
    }

    override fun getUserType(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString(Constants.USER_TYPE, "")!!
        }
        return ""
    }



    override fun getAuthToken(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString(Constants.TOKEN, "")!!
        }
        return ""
    }


    override fun showLoader() {
        if (activity != null && isAdded) {
            v.progressBar.visibility = View.VISIBLE
            Constants.setNonTouchableFlags(activity!!)
        }
    }

    override fun hideLoader() {
        if (activity != null && isAdded) {
            v.progressBar.visibility = View.GONE
            Constants.clearNonTouchableFlags(activity!!)
        }
    }

    override fun showError(error: String) {
        if (activity != null && isAdded) {
            Constants.showAlert(activity!!, error)
        }
    }

    override fun showErrorMessage(message: String?) {
        if (activity != null && isAdded) {
            if (mylist.size <= 0) {
                Constants.showAlert(activity!!, message!!)
                v.noData.visibility = View.VISIBLE
            }
        }
    }

    override fun setAdapter(list: MutableList<ModelCampaign>) {
        mylist = list
        if (activity != null && isAdded) {
            if (mylist.size <= 0) {
                v.noData.visibility = View.VISIBLE
            } else {
                v.noData.visibility = View.GONE
                recyclerview.layoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(activity)
                val adapter = CampaignAdapter(activity!!, mylist)
                recyclerview.adapter = adapter
            }
        }
    }

    /*for multiple*/
    fun Profilemethod() {

        showLoader()

        Log.e("PROFILE_DATA", auth_token + " " + user_id )

        mAPIService!!.Profile(auth_token,user_id).enqueue(object :
            Callback<ProfileResponse> {

            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {

                //  Log.i("", "post submitted to API." + response.body()!!)

                hideLoader()
                if (response.isSuccessful())
                {

                    if (response.body()!!.code == 200)
                    {
                       // Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        var user_name=response.body()!!.data.username
                        var user_cpf=response.body()!!.data.cpf_number
                        var user_contact=response.body()!!.data.phone_number
                        var user_email=response.body()!!.data.email

                        var user_location_arrayList= ArrayList<ProfileResponseUserLocation>()
                        var all_brands_string= ArrayList<String>()

                        user_location_arrayList.addAll(response.body()!!.data.user_Location)

                        for (i in 0 until user_location_arrayList.size)
                        {
                            all_location_id =  user_location_arrayList.get(i).LocationID
                            Log.e("ALL_LOCATION_ID",all_location_id)
                            all_brands_string.add(all_location_id)

                        }


                      citiesCommaSeparated = all_brands_string.joinToString()

                        Log.e("citiesCommaSeparated","citiesCommaSeparated" + citiesCommaSeparated)


                        txt_values_of_location.setText(citiesCommaSeparated);//state

                    }

                    else
                    {
                        Toast.makeText(context!!, response.body()!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                hideLoader()
                t.printStackTrace()

            }
        })
    }



}
