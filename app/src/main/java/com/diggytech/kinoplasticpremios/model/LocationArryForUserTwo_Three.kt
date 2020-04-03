package com.diggytech.kinoplasticpremios.model

import android.os.Parcel
import android.os.Parcelable

//data class Selected_Image(var name: String, var version: String)
class LocationArryForUserTwo_Three(): Parcelable {

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

//    fun isSelected(): Boolean {
//        return selected!!
//    }
//
//    fun setSelected(selected: Boolean) {
//        this.selected = selected
//    }

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

    companion object CREATOR : Parcelable.Creator<LocationArryForUserTwo_Three> {
        override fun createFromParcel(parcel: Parcel): LocationArryForUserTwo_Three {
            return LocationArryForUserTwo_Three(parcel)
        }

        override fun newArray(size: Int): Array<LocationArryForUserTwo_Three?> {
            return arrayOfNulls(size)
        }
    }


}



//    : Parcelable {
//
//    var name: String? = ""
//    var extention_name: String? = ""
//    var file_path: String? = ""
//
//    constructor(parcel: Parcel) : this() {
//        name = parcel.readString()
//        extention_name = parcel.readString()
//        file_path = parcel.readString()
//    }
//    // var image_drawable: Int = 0
//
//    fun getNames(): String {
//        return name.toString()
//    }
//
//    fun setNames(name: String) {
//        this.name = name
//    }
//    fun getFilePath(): String {
//        return file_path.toString()
//    }
//
//    fun setFilePath(file_path: String) {
//        this.file_path = file_path
//    }
//    fun getExtenName(): String {
//        return extention_name.toString()
//    }
//
//    fun setExtenName(extention_name: String) {
//        this.extention_name = extention_name
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(name)
//        parcel.writeString(extention_name)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Selected_Image_Client> {
//        override fun createFromParcel(parcel: Parcel): Selected_Image_Client {
//            return Selected_Image_Client(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Selected_Image_Client?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//
//}
