package com.example.mobilehub.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
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

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }


}