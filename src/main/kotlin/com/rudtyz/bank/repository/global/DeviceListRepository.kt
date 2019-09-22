package com.rudtyz.bank.repository.global

import com.rudtyz.bank.model.Device
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceListRepository: JpaRepository<Device, String>

