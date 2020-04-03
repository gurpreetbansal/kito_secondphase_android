package com.diggytech.kinoplasticpremios.Settings

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.Login.LoginActivity
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.Settings.ChangePassword.ChangepasswordActivity
import com.diggytech.kinoplasticpremios.Settings.PrivacyPolicy.PrivacyPolicyActivity
import com.diggytech.kinoplasticpremios.Settings.TermsConditions.TermsActivity
import com.diggytech.kinoplasticpremios.Support.SupportActivity
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.settings_item.view.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class SettingsAdapter(val context: Activity, val list: MutableList<ModelSettings>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.settings_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val model = list[p1]
        holder.title.text = model.title
        holder.ivLogo.setActualImageResource(model.img)
        holder.ivNext.setActualImageResource(R.drawable.blue_arrow)
        if (p1 == list.size - 1) {
            holder.line.visibility = View.GONE
        } else {
            holder.line.visibility = View.VISIBLE
        }

        holder.itemView.setOnClickListener {
            when (p1) {
                0 -> {
                    val intent = Intent(context, ChangepasswordActivity::class.java)
                    context.startActivity(intent)
                }
                1 -> {
                    val intent = Intent(context, TermsActivity::class.java)
                    intent.putExtra("web_url", Constants.BASE_URL + "terms_and_conditions")
                    context.startActivity(intent)
                }
                2 -> {
                    val intent = Intent(context, PrivacyPolicyActivity::class.java)
                    intent.putExtra("web_url", Constants.BASE_URL + "privacy_policy")
                    context.startActivity(intent)
                }
                3 -> {
                    val bulder = AlertDialog.Builder(context)
                    bulder.setMessage(R.string.are_you_delete)
                        .setPositiveButton(R.string.ok) { dialogInterface: DialogInterface, i: Int ->
                            dialogInterface.dismiss()
                            callDeleteAccountService()
                        }.setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                            dialogInterface.dismiss()
                        }.create().show()


                }
                4 -> {
                    val intent = Intent(context, SupportActivity::class.java)
                    context.startActivity(intent)
                }

            }
        }

    }


    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val title = view.tvTitle as TextView
        val ivLogo = view.ivLogo as SimpleDraweeView
        val ivNext = view.ivNext as SimpleDraweeView
        val line = view.line as View

    }

    fun callDeleteAccountService() {

        val userId = Constants.getPrefs(context)!!.getString(Constants.USER_ID, "")!!
        val AuthorizationToken = Constants.getPrefs(context)!!.getString(Constants.TOKEN, "")!!


        val client = Constants.getHttpClient(userId, AuthorizationToken)
        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: SettingsContract.Service = retrofit.create(SettingsContract.Service::class.java)
        val call: Call<ResponseBody> = service.terminateAccount()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Constants.showAlert(context, "No internet connection")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    val res = response.body()!!.string()
                    try {
                        val object1 = JSONObject(res)
                        val success = object1.optString("success")
                        val message = object1.optString("message")
                        if (success == "true") {
                            Constants.getPrefs(context)!!.edit().clear().apply()
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                            context.finishAffinity()

                        } else {
                            Constants.showAlert(context, message)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    Constants.showAlert(context, "Server Error")

                }
                try {
                    client.dispatcher().executorService().shutdown()
                    client.cache()!!.delete()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }


            }
        })
    }
}
