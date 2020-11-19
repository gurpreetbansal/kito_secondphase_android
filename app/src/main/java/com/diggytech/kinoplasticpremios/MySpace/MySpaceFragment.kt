package com.diggytech.kinoplasticpremios.MySpace


import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.app.soyadeti.Interface.APIService
import com.bumptech.glide.Glide
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.DashBoard.ProfileResponse
import com.diggytech.kinoplasticpremios.DashBoard.ProfileResponseUserLocation
import com.diggytech.kinoplasticpremios.Notification.NotificationActivity
import com.diggytech.kinoplasticpremios.Profile.ViewProfile.ViewProfileActivity
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.Settings.SettingsActivity
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.fragment_my_space.*
import kotlinx.android.synthetic.main.fragment_my_space.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MySpaceFragment : Fragment(), MySpaceContract.View {
    private var pointEarntList = mutableListOf<ModelPointsEarnt>()
    private var pointSpentList = mutableListOf<ModelPointsSpent>()
    private lateinit var pointsEarnedHeading: LinearLayout
    private lateinit var pointsSpentHeading: LinearLayout
    private lateinit var mPresenter: MySpacePresenter
    private lateinit var pointEarnedRecycler: androidx.recyclerview.widget.RecyclerView
    private lateinit var pointSpentRecycler: androidx.recyclerview.widget.RecyclerView
    private lateinit var pointsEarnedTab: TextView
    private lateinit var pointsSpentTab: TextView
    private lateinit var tvViewMore: TextView
    private lateinit var ivSettings: SimpleDraweeView
    private lateinit var ivNotification: SimpleDraweeView
    lateinit var v: View
    lateinit var auth_token:String
    lateinit var user_id:String
    var mAPIService: APIService? = null
    lateinit var all_brands:String
    lateinit var all_Location:String
    lateinit var  citiesCommaSeparated:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_my_space, container, false)
        mAPIService = APIService.ApiUtils.apiService

        auth_token = Constants.getPrefs(activity!!)!!.getString(Constants.TOKEN, "")
        user_id = Constants.getPrefs(activity!!)!!.getString(Constants.USER_ID, "")

        pointEarnedRecycler = v.findViewById(R.id.pointEarnedRecycler)
        pointSpentRecycler = v.findViewById(R.id.pointSpentRecycler)
        pointsEarnedTab = v.findViewById(R.id.pointsEarnedTab)
        pointsSpentTab = v.findViewById(R.id.pointsSpentTab)
        pointsEarnedHeading = v.findViewById(R.id.pointsEarnedHeading)
        pointsSpentHeading = v.findViewById(R.id.pointsSpentHeading)
        ivSettings = v.findViewById(R.id.ivSettings)
        ivNotification = v.findViewById(R.id.ivNotification)
        tvViewMore = v.findViewById(R.id.tvViewMore)

        mPresenter = MySpacePresenter(this)
        //  pointEarnedRecycler.isNestedScrollingEnabled = false
        pointEarnedRecycler.setHasFixedSize(true)
        //   pointSpentRecycler.isNestedScrollingEnabled = false
        pointSpentRecycler.setHasFixedSize(true)


        mPresenter.getMySpacedata()

        var  user_type = Constants.getPrefs(context!!)!!.getString(Constants.USER_TYPE, "")
        // var from_where_come = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_TYPESS", "")
        Log.e("USER_TYPE_IS",user_type)

        if(user_type.equals("1"))
        {
           // Profilemethod()
        }
        else {
            Profilemethod()
        }

        pointsEarnedTab.setOnClickListener {
            pointsSpentHeading.visibility = View.INVISIBLE
            pointSpentRecycler.visibility = View.GONE

            pointsEarnedHeading.visibility = View.VISIBLE
            pointEarnedRecycler.visibility = View.VISIBLE

            if (pointEarntList.size <= 0) {
                v.NoPointEarned.visibility = View.VISIBLE
            }
            v.NoPointSpent.visibility = View.GONE

            if (activity != null && isAdded) {
                pointsEarnedTab.setBackgroundResource(R.drawable.blue_bg_rounded_top)
                pointsEarnedTab.setTextColor(activity!!.resources.getColor(R.color.colorWhite))

                pointsSpentTab.setBackgroundResource(R.drawable.grey_bg_rounded_top)
                pointsSpentTab.setTextColor(activity!!.resources.getColor(R.color.colorGreyDark))
            }
        }


        pointsSpentTab.setOnClickListener {

            pointsEarnedHeading.visibility = View.INVISIBLE
            pointEarnedRecycler.visibility = View.GONE
            pointsSpentHeading.visibility = View.VISIBLE
            pointSpentRecycler.visibility = View.VISIBLE

            if (pointSpentList.size <= 0) {
                v.NoPointSpent.visibility = View.VISIBLE
            }
            v.NoPointEarned.visibility = View.GONE

            if (activity != null && isAdded) {
                pointsSpentTab.setBackgroundResource(R.drawable.blue_bg_rounded_top)
                pointsSpentTab.setTextColor(activity!!.resources.getColor(R.color.colorWhite))

                pointsEarnedTab.setBackgroundResource(R.drawable.grey_bg_rounded_top)
                pointsEarnedTab.setTextColor(activity!!.resources.getColor(R.color.colorGreyDark))
            }
        }

        ivNotification.visibility = View.GONE
        ivNotification.setOnClickListener {
            if (activity != null) {
                val intent = Intent(activity, NotificationActivity::class.java)
                startActivity(intent)
            }
        }

        ivSettings.setOnClickListener {
            if (activity != null) {
                val intent = Intent(activity, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        tvViewMore.setOnClickListener {
            if (activity != null) {
                val intent = Intent(activity, ViewProfileActivity::class.java)
                startActivity(intent)
            }
        }

        return v
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getUserDetails()
    }

    override fun setPointsEarntAdapter(list: MutableList<ModelPointsEarnt>) {
        pointEarntList = list
        if (activity != null && isAdded) {
            pointEarnedRecycler.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(activity)
            val adapter = PointsEarntAdapter(activity!!, pointEarntList)
            pointEarnedRecycler.adapter = adapter
        }

        if (pointEarntList.size <= 0) {
            v.NoPointEarned.visibility = View.VISIBLE
            v.NoPointSpent.visibility = View.GONE
        }

    }

    override fun setPointsSpentAdapter(list: MutableList<ModelPointsSpent>) {
        pointSpentList = list
        if (activity != null && isAdded) {
            pointSpentRecycler.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(activity)
            val adapter = PointSpentAdapter(activity!!, pointSpentList)
            pointSpentRecycler.adapter = adapter
        }
    }

    override fun setUserDetails() {
        val image = Constants.getPrefs(activity!!)!!.getString(Constants.PROFILE_PIC, "")
        val name = Constants.getPrefs(activity!!)!!.getString(Constants.USERNAME, "")
        val brand = Constants.getPrefs(activity!!)!!.getString(Constants.BRAND, "")
        val address = Constants.getPrefs(activity!!)!!.getString(Constants.ADDRESS, "")
        val location = Constants.getPrefs(activity!!)!!.getString(Constants.LOCATION, "")
        Log.e("PROFILE_DATA_11", "PROFILE_DATA_11" + image)
        //val location_id = Constants.getPrefs(activity!!)!!.getString(Constants.LOCATION_ID, "")
        v.tvName.text = name
        // v.tvPlace.text(location) Uri
        Log.e("PROFILE_DATA", "PROFILE_DATA" + image)
        var myUri = Uri.parse(image)

      //  var image_value="https://clientstagingdev.com/kinoplastic_server/public/images/users/"+image
        if (image.equals(null) || image.equals("")) {
            v.ivProfilePic.setImageResource(R.drawable.placeholder_img)
        } else {
            // v.ivProfilePic.setImageURI(myUri)
            // v.ivProfilePic.setImageURI(myUri)
          //  var image_value="https://clientstagingdev.com/kinoplastic_server/public/images/users/"+image
            var image_value=image
            /*Glide.with(activity!!)
                .load(image_value)
                .into(v.ivProfilePic);*/
            ivProfilePic.setImageURI(image)
            Log.e("PROFILE_DATA_11", "PROFILE_DATA_11" + image_value)

        }





//var image_value="https://clientstagingdev.com/kinoplastic_server/public/images/users/"+image
//        if (image_value.equals(null) || image_value.equals("")) {
//            v.ivProfilePic.setImageResource(R.drawable.placeholder_img)
//        } else {
//           // v.ivProfilePic.setImageURI(myUri)
//
//            Glide.with(activity!!)
//                .load(image_value)
//                .into(v.ivProfilePic);
//
//
//        }
        // v.ivProfilePic.setImageURI(myUri)
        if (brand.isNullOrEmpty()) {
            v.tvBrand.text = "NA"
        } else {
            v.tvBrand.text = "$brand"
        }
        if (location.isNullOrEmpty()) {
            v.tvPlace.text = "NA"
        } else {
            v.tvPlace.text = "$location"
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
            Constants.showAlert(activity!!, message!!)
        }
    }


    override fun setPoints(availablePoints: String?, pendingPoints: String?) {
        v.tvPendingPoints.text = pendingPoints!!
        v.tvAvailablePoint.text = availablePoints!!
    }


    /*profile api*/
    fun Profilemethod() {

        showLoader()

        /*  lateinit var auth_token:String
    lateinit var user_id:String*/
        Log.e("PROFILE_DATA", auth_token + " " + user_id )

        mAPIService!!.Profile(auth_token,user_id).enqueue(object :
            Callback<ProfileResponse> {

            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {

                hideLoader()
                if (response.isSuccessful())
                {

                    if (response.body()!!.code == 200)
                    {
                       // Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        var user_name=response.body()!!.data.username
                        var user_pic=response.body()!!.data.profile_pic

                        tvName.setText(user_name)
                       // var user_profile_pic=response.body()!!.data.profile_pic
                       // Log.e("PROFILE_PIC", user_profile_pic)
                        Log.e("PROFILE_PIC", user_name)
                        Log.e("PROFILE_PIC", user_pic)




                        if(user_pic.equals(null)||user_pic.equals(""))
                        {
                            v.ivProfilePic.setImageResource(R.drawable.placeholder_img)
                        }
else {
                           // var image_value="https://clientstagingdev.com/kinoplastic_server/public/images/users/"+user_pic
                           /* Glide.with(activity!!)
                                .load(user_pic)
                                .into(v.ivProfilePic);*/
                            v.ivProfilePic.setImageURI(user_pic)
                        }
                        var user_location_arrayList=ArrayList<ProfileResponseUserLocation>()
                        var all_brands_string=ArrayList<String>()
                        var all_location_string=ArrayList<String>()


                        user_location_arrayList.addAll(response.body()!!.data.user_Location)

                        for (i in 0 until user_location_arrayList.size)
                        {
                        all_brands =  user_location_arrayList.get(i).Brand
                            Log.e("ALL_BRANDS",all_brands)


                            all_brands_string.add(all_brands)

                            var set = HashSet<String>(all_brands_string);
                            all_brands_string.clear();
                            all_brands_string.addAll(set);

                        }
                        citiesCommaSeparated = all_brands_string.joinToString()
                        tvBrand.setText(citiesCommaSeparated);

                        for (i in 0 until user_location_arrayList.size)
                        {
                            all_Location =  user_location_arrayList.get(i).Location
                            Log.e("ALL_LOCATION",all_Location)
                            all_location_string.add(all_Location)
                        }

                        citiesCommaSeparated = all_location_string.joinToString()
                        tvPlace.setText(citiesCommaSeparated);
                    }

                    else
                    {
                       // Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT)
                          //  .show()
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
