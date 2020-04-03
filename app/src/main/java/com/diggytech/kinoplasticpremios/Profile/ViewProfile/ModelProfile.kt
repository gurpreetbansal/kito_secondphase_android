package com.diggytech.kinoplasticpremios.Profile.ViewProfile

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ModelProfile() : Parcelable {
    var id: String = ""
    var username: String = ""
    var email: String = ""
    var cpf_number: String = ""
    var phone_number: String = ""
    var address: String = ""
    var brand: String = ""
    var state: String = ""
    var city: String = ""
    var profile_pic: String = ""
    var location: String = ""


    var device_token: String = ""
    var device_type: String = ""
    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        username = parcel.readString()
        email = parcel.readString()
        cpf_number = parcel.readString()
        phone_number = parcel.readString()
        address = parcel.readString()
        brand = parcel.readString()
        profile_pic = parcel.readString()
        state = parcel.readString()
        city = parcel.readString()
       location = parcel.readString()

       device_token = parcel.readString()
       device_type = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeString(cpf_number)
        parcel.writeString(phone_number)
        parcel.writeString(address)
        parcel.writeString(brand)
        parcel.writeString(profile_pic)
        parcel.writeString(state)
        parcel.writeString(city)
        parcel.writeString(location)

        parcel.writeString(device_token)
        parcel.writeString(device_type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelProfile> {
        override fun createFromParcel(parcel: Parcel): ModelProfile {
            return ModelProfile(parcel)
        }

        override fun newArray(size: Int): Array<ModelProfile?> {
            return arrayOfNulls(size)
        }
    }

}
