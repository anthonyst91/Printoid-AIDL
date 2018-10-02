package fr.printoid.phones

import android.os.Parcel
import android.os.Parcelable

/**
 * The server data model is a simplification of a configured OctoPrint server
 * in Printoid.
 *
 * It contains:
 * - serverId: the unique ID to identify the server in Printoid
 * - serverName: the server name defined by the user
 * - lastConnectionTimestamp: the last time the user connected Printoid to this server
 */
data class ServerModel constructor(val serverId: String,
                                   val serverName: String,
                                   val lastConnectionTimestamp: Long): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readLong())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(serverId)
        parcel.writeString(serverName)
        parcel.writeLong(lastConnectionTimestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ServerModel> {
        override fun createFromParcel(parcel: Parcel): ServerModel {
            return ServerModel(parcel)
        }
        override fun newArray(size: Int): Array<ServerModel?> {
            return arrayOfNulls(size)
        }
    }

}