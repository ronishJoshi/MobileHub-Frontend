package com.example.mobilehub.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Checkout(
    val _id: String? = null,
    var productname: String? = null,
    var price: String? = null,
    var producttype: String? = null,
    var description: String? = null,
    val photo: String?= null
) : Parcelable{
    @PrimaryKey(autoGenerate = true)
    var mobId: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        (parcel.readValue(Int::class.java.classLoader) as? Int).toString(),
        parcel.readString(),
        parcel.readString()
    ) {
        mobId = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productname)
        parcel.writeValue(price)
        parcel.writeString(producttype)
        parcel.writeString(description)
        parcel.writeInt(mobId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Checkout> {
        override fun createFromParcel(parcel: Parcel): Checkout {
            return Checkout(parcel)
        }

        override fun newArray(size: Int): Array<Checkout?> {
            return arrayOfNulls(size)
        }
    }


}