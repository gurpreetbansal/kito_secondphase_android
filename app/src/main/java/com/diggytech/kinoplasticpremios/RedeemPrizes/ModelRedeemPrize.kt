package com.diggytech.kinoplasticpremios.RedeemPrizes

import android.os.Parcel
import android.os.Parcelable

class ModelRedeemPrize() : Parcelable {
    var image = ""
    var title = ""
    var price_range = ""
    var validity_period = ""
    var description = ""
    var id = ""
    var points_consumed = ""

    constructor(parcel: Parcel) : this() {
        image = parcel.readString()!!
        title = parcel.readString()!!
        price_range = parcel.readString()!!
        validity_period = parcel.readString()!!
        description = parcel.readString()!!
        id = parcel.readString()!!
        points_consumed = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(title)
        parcel.writeString(price_range)
        parcel.writeString(validity_period)
        parcel.writeString(description)
        parcel.writeString(id)
        parcel.writeString(points_consumed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelRedeemPrize> {
        override fun createFromParcel(parcel: Parcel): ModelRedeemPrize {
            return ModelRedeemPrize(parcel)
        }

        override fun newArray(size: Int): Array<ModelRedeemPrize?> {
            return arrayOfNulls(size)
        }
    }
}