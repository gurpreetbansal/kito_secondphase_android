package com.diggytech.kinoplasticpremios.Newsletter

import android.os.Parcel
import android.os.Parcelable

class ModelNewsLetter() : Parcelable {
    var image = ""
    var title = ""
    var message = ""
    var created_at = ""
    var cover_pic = ""
    var brand = ""
    var id = ""
    var location = ""

    constructor(parcel: Parcel) : this() {
        image = parcel.readString()!!
        title = parcel.readString()!!
        message = parcel.readString()!!
        created_at = parcel.readString()!!
        cover_pic = parcel.readString()!!
        brand = parcel.readString()!!
        id = parcel.readString()!!
        location = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(title)
        parcel.writeString(message)
        parcel.writeString(created_at)
        parcel.writeString(cover_pic)
        parcel.writeString(brand)
        parcel.writeString(id)
        parcel.writeString(location)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelNewsLetter> {
        override fun createFromParcel(parcel: Parcel): ModelNewsLetter {
            return ModelNewsLetter(parcel)
        }

        override fun newArray(size: Int): Array<ModelNewsLetter?> {
            return arrayOfNulls(size)
        }
    }
}