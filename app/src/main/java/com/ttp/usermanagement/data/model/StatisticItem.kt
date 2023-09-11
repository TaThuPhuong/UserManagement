package com.ttp.usermanagement.data.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class StatisticItem(
    val roleName: String,
    var maleCount: Int,
    var femaleCount: Int,
    var age0to19Count: Int,
    var age20PlusCount: Int,
    var ageUnknownCount: Int
) : Parcelable {

    // Hàm này để đóng gói đối tượng
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(roleName)
        parcel.writeInt(maleCount)
        parcel.writeInt(femaleCount)
        parcel.writeInt(age0to19Count)
        parcel.writeInt(age20PlusCount)
        parcel.writeInt(ageUnknownCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    // Hàm này để tạo một mảng đối tượng từ Parcel
    companion object CREATOR : Parcelable.Creator<StatisticItem> {
        override fun createFromParcel(parcel: Parcel): StatisticItem {
            return StatisticItem(
                parcel.readString() ?: "",
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt()
            )
        }

        override fun newArray(size: Int): Array<StatisticItem?> {
            return arrayOfNulls(size)
        }
    }
}
