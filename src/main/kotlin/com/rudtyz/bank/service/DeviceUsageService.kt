package com.rudtyz.bank.service

import com.rudtyz.bank.exception.InvalidDeviceIdException
import com.rudtyz.bank.exception.NoSuchDeviceDataException
import com.rudtyz.bank.loader.DatasetBankingUsageLoader
import com.rudtyz.bank.model.Device
import com.rudtyz.bank.model.DeviceUsage
import com.rudtyz.bank.repository.global.DeviceListRepository
import com.rudtyz.bank.repository.local.DeviceUsageRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class DeviceUsageService(
        private val datasetLoader: DatasetBankingUsageLoader,
        private val deviceUsageRepository: DeviceUsageRepository,
        private val deviceListRepository: DeviceListRepository
) {

    /**
     * 모든 디바이스
     */
    @Cacheable("deviceFindAll")
    fun getDevicesAll(): List<Device> = deviceListRepository.findAll()

    /**
     * 가장 사용율이 높은 디바이스
     */
    @Cacheable("getMostUsage")
    fun getMostUsage(): List<DeviceUsage> =
            deviceListRepository.findAll()
                    .map(::getDeviceUsageByDevice)
    
    /**
     * 전체 이용률 을 제외하고 특정연도에서 가장 사용률이 높은 디바이스 
     */
    @Cacheable("deviceUsage")
    fun getDeviceUsage(year: Int) =
            deviceUsageRepository.findByYearOrderByUsageDescLimit1(year)
                    ?: throw NoSuchDeviceDataException("$year")
    
    /**
     * 디바이스가 가장 사용율이 높은 연도에서 정보
     */
    @Cacheable("mostUsageYear")
    fun getMostUsageYear(deviceId: String): DeviceUsage? {
        val device = deviceListRepository.findById(deviceId)
        if (device.isPresent) {
            return getDeviceUsageByDevice(device.get())
        }
        throw InvalidDeviceIdException(deviceId)
    }

    /**
     * 파일시스템에서 다시 로드
     */
    fun reloadTable() = datasetLoader.reloadTable()


    /**
     * 디바이스 이름으로 사용률 조회
     */
    private fun getDeviceUsageByDevice(device: Device) =
            deviceUsageRepository.findByDeviceNameOrderByUsageDescLimit1(device.deviceName)
                    ?: throw NoSuchDeviceDataException(device.deviceId)
}