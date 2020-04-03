package com.diggytech.kinoplasticpremios.Newsletter


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
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
import com.diggytech.kinoplasticpremios.Login.SignUp.SignUpRequest_ChlidDataModel
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.model.LocationArryForUserTwo_Three
import kotlinx.android.synthetic.main.fragment_campaign.*
import kotlinx.android.synthetic.main.fragment_campaign.view.*
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_notification.view.*
import kotlinx.android.synthetic.main.fragment_redeem_prizes.view.*
import kotlinx.android.synthetic.main.fragment_redeem_prizes.view.progressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class NewsLetterFragment : Fragment(), NewsLetterContract.View {



    private lateinit var mPresenter: NewsLetterPresenter
    private lateinit var recyclerview: androidx.recyclerview.widget.RecyclerView
    private lateinit var txt_news_not_available: TextView
    lateinit var v: View
    lateinit var auth_token:String
    lateinit var user_id:String
    var mAPIService: APIService? = null
    lateinit var all_brands:String
    lateinit var all_Location:String
    lateinit var  citiesCommaSeparated:String
    lateinit var  citiesCommaSeparated_two:String
    lateinit var all_location_id:String

    val selected_location = ArrayList<LocationArryForUserTwo_Three>()
    var user_location_arrayList= ArrayList<ProfileResponseUserLocation>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_notification, container, false)
        mAPIService = APIService.ApiUtils.apiService

        auth_token = Constants.getPrefs(activity!!)!!.getString(Constants.TOKEN, "")
        user_id = Constants.getPrefs(activity!!)!!.getString(Constants.USER_ID, "")


        val mToolbar = v.findViewById<Toolbar>(R.id.toolbar)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        mTitle!!.text = getString(R.string.notification_two)

        var  user_type = Constants.getPrefs(context!!)!!.getString(Constants.USER_TYPE, "")
        // var from_where_come = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_TYPESS", "")
        Log.e("USER_TYPE_IS",user_type)

        if(user_type.equals("1"))
        {

        }
        else {
            Profilemethod()
        }



        var value_one =Constants.getPrefs(activity!!)!!.getString(Constants.ADDRESS, "")!!
        var value_two =Constants.getPrefs(activity!!)!!.getString(Constants.BRAND, "")!!

       // Toast.makeText(context,"VALUES_ ARE"+ " "+value_one+" " +value_two,Toast.LENGTH_LONG).show()
        recyclerview = v.findViewById(R.id.recyclerview_notification)
        txt_news_not_available = v.findViewById(R.id.txt_news_not_available)
        mPresenter = NewsLetterPresenter(this)
        mPresenter.callgetVouchersApi()
        return v
    }



    override fun setAdapter(list: MutableList<ModelNewsLetter>) {
        if (activity != null && isAdded) {
            recyclerview.layoutManager = androidx.recyclerview.widget.GridLayoutManager(
                activity,
                1,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
            val adapter = NewsLetterAdapter(activity!!, list)
            recyclerview.adapter = adapter
        }
    }

    override fun getUserId(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString(Constants.USER_ID, "")!!
        }
        return ""
    }

    override fun getAuthToken(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString(Constants.TOKEN, "")!!
        }
        return ""
    }

    override fun getLocationId(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString(Constants.ADDRESS, "")!!
        }
        return ""
    }

    override fun getBrandName(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString(Constants.BRAND, "")!!
        }
        return ""
    }
    override fun getBrandName_All(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString("BRAND_TWO", "")!!
        }
        return ""
    }
    override fun showEmptyListText() {
        if (activity != null && isAdded) {
            v.txt_news_not_available.visibility = View.VISIBLE
            Constants.setNonTouchableFlags(activity!!)
        }
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

    override fun showError(message: String) {
        if (activity != null && isAdded) {
            Constants.showAlert(activity!!, message)
        }
    }

    override fun showErrorMessage(message: String?) {
        if (activity != null && isAdded) {
            Constants.showAlert(activity!!, message!!)
        }
    }

    override fun showSuccessMessage(message: String?) {
        if (activity != null && isAdded) {
            Constants.showAlert(activity!!, message!!)
        }
    }
    override fun getUserType(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString(Constants.USER_TYPE, "")!!
        }
        return ""
    }
    override fun getLocationId_Two(): String {
        return v.txt_values_of_news.text.toString().trim()
    }


    /*for multiple*/
    fun Profilemethod()
    {

        showLoader()

        Log.e("PROFILE_DATA", auth_token + " " + user_id )

        mAPIService!!.Profile(auth_token,user_id).enqueue(object :Callback<ProfileResponse> {

            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {

                hideLoader()
                if (response.isSuccessful())
                {
                    if (response.body()!!.code == 200)
                    {
                     //   Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        var all_location_string= ArrayList<String>()
                        var all_brand= ArrayList<String>()
                        user_location_arrayList.addAll(response.body()!!.data.user_Location)
                        for (i in 0 until user_location_arrayList.size)
                        {
                            all_location_id =  user_location_arrayList.get(i).LocationID
                            all_brands =  user_location_arrayList.get(i).Brand
//                            all_location_string.add(all_location_id)
//                            all_brand.add(all_brands)

                        }
//
//
//                        citiesCommaSeparated = all_location_string.joinToString()
//                        citiesCommaSeparated_two = all_brand.joinToString()
//
//                        Log.e("citiesCommaSeparated","citiesCommaSeparated" + citiesCommaSeparated)
//                        Log.e("citiesCommaSeparated","citiesCommaSeparated" + citiesCommaSeparated_two)
//
//
//                        txt_values_of_news.setText(citiesCommaSeparated);//state
//                        txt_values_of_brand.setText(citiesCommaSeparated_two);//state
//
//
//                        Constants.getPrefs(activity!!)!!.edit().putString("BRAND_TWO", citiesCommaSeparated_two).apply()

                    }

                    else
                    {
//                        Toast.makeText(context!!, response.body()!!.message, Toast.LENGTH_SHORT)
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



    override fun getLocationArray(locationsArray: ArrayList<ChildNewsDataModel>):ArrayList<ChildNewsDataModel>
    {
        var news_multiple_locations = ArrayList<ChildNewsDataModel>();
        for (i in 0 until user_location_arrayList.size)
        {
            var model= ChildNewsDataModel()
            model.brand=user_location_arrayList[i].Brand
            model.locationID=user_location_arrayList.get(i).LocationID
            news_multiple_locations.add(model);
            Log.e("SELECTED_POSITION4",  news_multiple_locations.size.toString())
        }
        return  news_multiple_locations
    }
}
