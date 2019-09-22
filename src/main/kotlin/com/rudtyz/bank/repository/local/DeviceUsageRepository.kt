package com.rudtyz.bank.repository.local

import com.rudtyz.bank.model.DeviceUsage
import org.springframework.stereotype.Component
import javax.transaction.Transactional

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

        dummyDeviceUsageRepository.save(deviceUsage)
    }

    @Transactional
    fun saveAll(entities: Iterable<DeviceUsage>) {
        for (e in entities) {
            save(e)
        }
    }

    /**
     * select * from table where year = @year order by usage limit 1
     */
    fun findByYearOrderByUsageDescLimit1(year: Int): DeviceUsage? =
            storage[year]
                    ?.maxBy { it.rate }


    /**
     * select * from table where device_name = @deviceName order by usage limit 1
     */
    fun findByDeviceNameOrderByUsageDescLimit1(deviceName: String): DeviceUsage? =
            storage.values
                    .flatten()
                    .filter { it.deviceName == deviceName }
                    .maxBy { it.rate }


    /**
     * select distinct device_name from table
     */
    fun findDistinctDeviceName(): List<String> =
            storage.values
                    .flatten()
                    .map { it.deviceName }
                    .distinct()

    /**
     * select count(*) from table
     */
    fun count() =
            storage.flatMap { it.value }
                    .count()
}