package com.rudtyz.bank.service

import com.rudtyz.bank.exception.InvalidDeviceIdException
import com.rudtyz.bank.exception.NoSuchDeviceDataException
import com.rudtyz.bank.loader.TableBankingUsageLoader
import com.rudtyz.bank.model.DeviceUsage
import com.rudtyz.bank.repository.global.DeviceListRepository
import com.rudtyz.bank.repository.local.DeviceUsageRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class DeviceUsageService(
        private val tableLoader: TableBankingUsageLoader,
        private val deviceUsageRepository: DeviceUsageRepository,
        private val deviceListRepository: DeviceListRepository
) {

    /**
     * 전체 이용률 을 제외하고 특정연도에서 가장 사용률이 높은 디바이스 반환
     */
    @Cacheable("deviceUsage")
    fun getDeviceUsage(year: Int) =
            deviceUsageRepository.findByYearOrderByUsageDescLimit1(year)
                    ?: throw NoSuchDeviceDataException("$year")


    /**
     * 가장 사용률이 높은 연도 로 정렬한 디바이스 정보 출력
     */
    @Cacheable("mostUsageYear")
    fun getMostUsageYear(deviceId: String): DeviceUsage? {
        val device = deviceListRepository.findById(deviceId)
        if (device.isPresent) {
            return deviceUsageRepository
                    .findByDeviceNameOrderByUsageDescLimit1(device.get().deviceName)
                    ?: throw NoSuchDeviceDataException(deviceId)
        }
        throw InvalidDeviceIdException(deviceId)
    }

    /**
     * 파일시스템에서 다시 로드
     */
    fun reloadTable() = tableLoader.reloadTable()
}