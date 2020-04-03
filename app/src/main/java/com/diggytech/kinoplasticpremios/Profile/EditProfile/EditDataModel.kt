package com.diggytech.kinoplasticpremios.Profile.EditProfile


import android.os.Parcel
import android.os.Parcelable

//data class Selected_Image(var name: String, var version: String)
class EditDataModel(): Parcelable {

    var location_name_value: String? = null
    var id_value: String? = null
    var state_value: String? = null
    var city_value: String? = null
    var status_value: String? = null
    var brand_value: String? = null
    // var selected: Boolean? = false

    constructor(parcel: Parcel) : this() {
        location_name_value = parcel.readString()
        id_value = parcel.readString()
        state_value = parcel.readString()
        city_value = parcel.readString()
        status_value = parcel.readString()
        brand_value = parcel.readString()
    }

    fun getNames(): String {
        return location_name_value.toString()
    }
    fun setNames(name: String) {
        this.location_name_value = name
    }

    fun getId(): String {
        return id_value.toString()
    }
    fun setId(id_val: String) {
        this.id_value = id_val
    }

    fun getState(): String {
        return state_value.toString()
    }
    fun setState(state: String) {
        this.state_value = state
    }

    fun getCity(): String {
        return city_value.toString()
    }
    fun setCity(city: String) {
        this.city_value = city
    }

    fun getStatus(): String {
        return status_value.toString()
    }
    fun setStatus(status: String) {
        this.status_value = status
    }

    fun getBrand(): String {
        return brand_value.toString()
    }
    fun setBrand(brand: String) {
        this.brand_value = brand
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(location_name_value)
        parcel.writeString(id_value)
        parcel.writeString(state_value)
        parcel.writeString(city_value)
        parcel.writeString(status_value)
        parcel.writeString(brand_value)



    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EditDataModel> {
        override fun createFromParcel(parcel: Parcel): EditDataModel {
            return EditDataModel(parcel)
        }

        override fun newArray(size: Int): Array<EditDataModel?> {
            return arrayOfNulls(size)
        }
    }


}
