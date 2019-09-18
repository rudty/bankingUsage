package com.rudtyz.bank.repository.local

import com.rudtyz.bank.model.DeviceUsage
import org.springframework.stereotype.Component

/**
 * Repository 대신 map 으로 비슷하게 반환 
 * 함수 이름은 JPA 와 같이 하여 이후 마이그레이션이 쉽도록 구현
 */
@Component
class DeviceUsageRepository(
        private  val dummyDeviceUsageRepository: DummyDeviceUsageRepository
) {
    private val storage = HashMap<Int, MutableList<DeviceUsage>>()

    fun save(deviceUsage: DeviceUsage) {
        val elem = storage[deviceUsage.year]
        if (elem != null) {
            elem += deviceUsage
        } else {
            storage[deviceUsage.year] = mutableListOf(deviceUsage)
        }

        dummyDeviceUsageRepository.saveAndFlush(deviceUsage)
    }

    /**
     * 특정 년도를 입력받아 그 해에 인터넷뱅킹에 가장 많이 접속하는 기기 이름을 출력하세요.
     */
    fun findByYearAndDeviceNameNotOrderByDescUsageLimit1(year: Int, deviceName: String): DeviceUsage? =
            storage[year]
                    ?.filterNot { it.deviceName == deviceName }
                    ?.maxBy { it.usage }
}