package com.diggytech.kinoplasticpremios.Profile.ViewProfile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.app.soyadeti.Interface.APIService
import com.bumptech.glide.Glide
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.DashBoard.DashBoardActivity
import com.diggytech.kinoplasticpremios.DashBoard.ProfileResponse
import com.diggytech.kinoplasticpremios.DashBoard.ProfileResponseUserLocation
import com.diggytech.kinoplasticpremios.Profile.EditProfile.EditProfileActivity
import com.diggytech.kinoplasticpremios.R
import kotlinx.android.synthetic.main.activity_view_profile.*
import kotlinx.android.synthetic.main.activity_view_profile.progressBar
import kotlinx.android.synthetic.main.edit_profile_two.*
import kotlinx.android.synthetic.main.fragment_my_space.*
import kotlinx.android.synthetic.main.fragment_my_space.view.*
import kotlinx.android.synthetic.main.view_profile.*
import kotlinx.android.synthetic.main.view_profile.contact
import kotlinx.android.synthetic.main.view_profile.email
import kotlinx.android.synthetic.main.view_profile.tvBrand
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ViewProfileActivity : AppCompatActivity(), ViewProfileContract.View {

    private lateinit var mPresenter: ViewProfilePresenter

    lateinit var auth_token:String
    lateinit var user_id:String
    lateinit var user_type:String
    var mAPIService: APIService? = null
    lateinit var all_brands:String
    lateinit var all_states:String
    lateinit var all_Location:String
    lateinit var all_city:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)

        mAPIService = APIService.ApiUtils.apiService

        auth_token = Constants.getPrefs(this)!!.getString(Constants.TOKEN, "")
        user_id = Constants.getPrefs(this)!!.getString(Constants.USER_ID, "")
        user_type = Constants.getPrefs(this)!!.getString(Constants.USER_TYPE, "")


        if (user_type.equals("1"))
        {
             mPresenter = ViewProfilePresenter(this)
            mPresenter.getProfileData()
        }
      else {
            Profilemethod()
            editProfile.setOnClickListener {
                val intent = Intent(this@ViewProfileActivity, EditProfileActivity::class.java)
                startActivityForResult(intent, 1)
            }

        }

        img_back.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ViewProfileActivity, DashBoardActivity::class.java)
            startActivityForResult(intent, 1)

        })
    }


    override fun getUserId(): String {
        return Constants.getPrefs(this)!!.getString(Constants.USER_ID, "")!!
    }

    override fun getAuthToken(): String {
        return Constants.getPrefs(this)!!.getString(Constants.TOKEN, "")!!
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
        Constants.setNonTouchableFlags(this@ViewProfileActivity)
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
        Constants.clearNonTouchableFlags(this@ViewProfileActivity)
    }

    override fun showError(message: String) {
        Constants.showAlert(this@ViewProfileActivity, message)
    }

    override fun showErrorMessage(message: String?) {
        Constants.showAlert(this@ViewProfileActivity, message!!)
    }

    override fun setProfileData(model: ModelProfile)
    {
        ivImage.setImageURI(model.profile_pic)
Log.e("VIEW_PROFILE_PIC",model.profile_pic)
        name.text = model.username
        location.text = model.location

        if (model.cpf_number.isEmpty()) {
            cpf.text = "NA"
        } else {
            cpf.text = model.cpf_number
        }
        if (model.phone_number.isEmpty()) {
            contact.text = "NA"
        } else {
            contact.text = model.phone_number
        }


        brand.text = model.brand
        email.text = model.email
        state.text = model.state
        city.text = model.city
       // location.text=model.location

        editProfile.setOnClickListener {
            val intent = Intent(this@ViewProfileActivity, EditProfileActivity::class.java)
            startActivityForResult(intent, 1)
        }

//        try {
//            val list = Constants.getLocations(this@ViewProfileActivity)
//            for (i in 0 until list!!.size) {
//                if (list[i].id == model.address) {
//                    location.text = list[i].name
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                // result from edit profile

                if (user_type.equals("1"))
                {
                    mPresenter.getProfileData()
                }
                else {
                    Profilemethod()
                    editProfile.setOnClickListener {
                        val intent = Intent(this@ViewProfileActivity, EditProfileActivity::class.java)
                        startActivityForResult(intent, 1)
                    }

                }


                //mPresenter.getProfileData()
            }
        }
    }

    fun Profilemethod() {

        showLoader()

        /*  lateinit var auth_token:String
    lateinit var user_id:String*/
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
                      //  Toast.makeText(this@ViewProfileActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        var user_name=response.body()!!.data.username
                        name.setText(user_name)

                        var user_cpf=response.body()!!.data.cpf_number
                        cpf.setText(user_cpf)

                        var user_contact=response.body()!!.data.phone_number
                        contact.setText(user_contact)

                        //email
                        var user_email=response.body()!!.data.email
                        email.setText(user_email)

                        var user_pic=response.body()!!.data.profile_pic

                        Log.e("PROFILE_PIC", user_name)
                        Log.e("PROFILE_PIC", user_pic)
                        if(user_pic.equals(null)||user_pic.equals(""))
                        {
                            ivImage.setImageResource(R.drawable.placeholder_img)
                        }
                        else {
                            // var image_value="https://clientstagingdev.com/kinoplastic_server/public/images/users/"+user_pic
                            Glide.with(this@ViewProfileActivity)
                                .load(user_pic)
                                .into(ivImage);

                        }


                        var user_location_arrayList= ArrayList<ProfileResponseUserLocation>()
                        var all_brands_string= ArrayList<String>()
                        var all_location_string= ArrayList<String>()
                        var all_state_string= ArrayList<String>()
                        var all_city_string= ArrayList<String>()


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
                        lateinit var  citiesCommaSeparated:String
                        citiesCommaSeparated = all_brands_string.joinToString()
                        //tvBrand.setText(citiesCommaSeparated);

                      //  brand.setText(all_brands_string.toString());//state
                        brand.setText(citiesCommaSeparated);//state

                        for (i in 0 until user_location_arrayList.size)
                        {
                            all_states =  user_location_arrayList.get(i).State
                            Log.e("ALL_LOCATION",all_states)
                            all_state_string.add(all_states)


                            var set = HashSet<String>(all_state_string);
                            all_state_string.clear();
                            all_state_string.addAll(set);

                        }

                        lateinit var  citiesCommaSeparated2:String
                        citiesCommaSeparated2 = all_state_string.joinToString()

                        state.setText(citiesCommaSeparated2);//state
                       // state.setText( all_state_string.toString());

                        for (i in 0 until user_location_arrayList.size)
                        {
                            all_city =  user_location_arrayList.get(i).City
                            Log.e("ALL_LOCATION",all_city)
                            all_city_string.add(all_city)

                            var set = HashSet<String>(all_city_string);
                            all_city_string.clear();
                            all_city_string.addAll(set);


                        }
                        lateinit var  citiesCommaSeparated3:String
                        citiesCommaSeparated3 = all_city_string.joinToString()

                        city.setText(citiesCommaSeparated3);//state
                       // city.setText( all_city_string.toString());

                        for (i in 0 until user_location_arrayList.size)
                        {
                            all_Location =  user_location_arrayList.get(i).Location
                            Log.e("ALL_LOCATION",all_Location)
                            all_location_string.add(all_Location)

                            var set = HashSet<String>(all_location_string);
                            all_location_string.clear();
                            all_location_string.addAll(set);

                        }
                        lateinit var  citiesCommaSeparated4:String
                        citiesCommaSeparated4 = all_location_string.joinToString()

                        location.setText(citiesCommaSeparated4);//state

                        //location.setText("Address:-" + all_location_string.toString());
                    }

                    else
                    {
//                        Toast.makeText(this@ViewProfileActivity, response.body()!!.message, Toast.LENGTH_SHORT)
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


}
