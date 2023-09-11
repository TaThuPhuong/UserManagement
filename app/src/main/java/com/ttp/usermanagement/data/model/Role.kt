package com.ttp.usermanagement.data.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Role(
    val authorityId: Int,
    val authorityName: String,
    val createUserId: String,
    val updateUserId: String,
    val createDate: Long,
    val updateDate: Long,
): Parcelable {

    // Hàm này để đóng gói đối tượng
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(authorityId)
        dest.writeString(authorityName)
    }

    // Hàm này để tạo đối tượng từ Parcel
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readLong()
    )

    // Hàm này để tạo một mảng đối tượng từ Parcel
    companion object CREATOR : Parcelable.Creator<Role> {
        override fun createFromParcel(parcel: Parcel): Role {
            return Role(parcel)
        }

        override fun newArray(size: Int): Array<Role?> {
            return arrayOfNulls(size)
        }
    }

    // Các hàm khác của Parcelable
    override fun describeContents(): Int {
        return 0
    }
}