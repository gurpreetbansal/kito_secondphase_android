package com.diggytech.kinoplasticpremios.Campaign.Products

import com.diggytech.kinoplasticpremios.Campaign.ModelCampaign
import com.diggytech.kinoplasticpremios.Constants
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProductsPresenter(val view: ProductsContract.View) {

 fun getProductsOfCampaigns(id: String) {
  val userId = view.getUserId()
  val authorizationToken = view.getAuthToken()

  view.showLoader()

  val client = Constants.getHttpClient(userId, authorizationToken)
  val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
  val service: ProductsContract.Service = retrofit.create(ProductsContract.Service::class.java)
  val call: Call<ResponseBody> = service.getCampaignProducts(id)
  call.enqueue(object : Callback<ResponseBody> {
   override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
    view.hideLoader()
    view.showError("No internet connection")
   }

   override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
    view.hideLoader()
    if (response.isSuccessful) {
     val res = response.body()!!.string()
     try {
      val object1 = JSONObject(res)
      val success = object1.optString("success")
      val message = object1.optString("message")
      if (success == "true") {
       val data = object1.optJSONObject("data")
       val campaign = data.optJSONObject("campaign")
       view.setCampaignData(parseCampaign(campaign))
       val product_details = data.optJSONArray("product_details")
       view.setAdapter(parseResponse(product_details))
      } else {
       view.showErrorMessage(message)
      }
     } catch (e: Exception) {
      e.printStackTrace()
     }
    } else {
     view.showError("Server Error")
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

 private fun parseCampaign(campaign: JSONObject): ModelCampaign {
  val model = ModelCampaign()
  model.id = campaign.optString("id")
  model.name = campaign.optString("name")
  model.description = campaign.optString("description")
  return model
 }


 private fun parseResponse(product_details: JSONArray): MutableList<ModelProducts> {

  val list = mutableListOf<ModelProducts>()
  for (i in 0 until product_details.length()) {
   val model = ModelProducts()
   val object1 = product_details.optJSONObject(i)
   model.product_id = object1.optString("product_id")
   model.product_name = object1.optString("product_name")
   model.product_sku = object1.optString("product_sku")
   model.product_brand = object1.optString("product_brand")
   model.product_image = object1.optString("product_image")
   model.points_per_sale = object1.optString("points_per_sale")

   list.add(model)
  }

  return list
 }

}