package com.rudtyz.bank.model

import com.rudtyz.bank.util.newID
import javax.persistence.*

@Entity
@Table(indexes = [
    Index(columnList = "deviceName")])
data class Device (
        @Id
        @Column
        var deviceId: String = "",

        @Column
        var deviceName: String = ""
) {
    companion object{
        fun createDummyDevice(deviceName: String) = Device(newID(), deviceName)
    }
}