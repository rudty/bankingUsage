package com.rudtyz.bank.repository.global

import com.rudtyz.bank.model.Device
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository

@Repository
class DeviceListRepository(
    private val deviceListRepository: JpaDeviceListRepository
):JpaDeviceListRepository by deviceListRepository {

    @Cacheable("findAll")
    override fun findAll(): MutableList<Device> {
        return deviceListRepository.findAll()
    }
}