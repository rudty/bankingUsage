package com.rudtyz.bank.loader

import com.rudtyz.bank.model.Device
import com.rudtyz.bank.model.DeviceUsage
import com.rudtyz.bank.repository.global.DeviceListRepository
import com.rudtyz.bank.repository.local.DeviceUsageRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileReader

@Component
class TableBankingUsageLoader(
        private val tableDir: String,
        private val deviceUsageRepository: DeviceUsageRepository,
        private val deviceListRepository: DeviceListRepository
) : InitializingBean {
    companion object {
        private const val TABLE_NAME = "data.csv"
    }

    /**
     * data.csv 를 읽는다
     * 해당 데이터를 db에 저장하는 방법은
     * DeviceUsage(
     *      year : data.csv 의 기간 column
     *      deviceName: data.csv 의 기간을 제외한 나머지 column
     *      usage: data.csv 의 각 원소들(elem) 만약 -라면 0.0으로 조정
     * )
     * 예를들어 row 가
     *
     * 기간,이용률,스마트폰
     * 2011,52.9,11 이라면
     *
     * DeviceUsage(2011, 이용률, 52.9)
     * DeviceUsage(2011, 스마트폰, 11)
     *
     * 과 같이 2개의 객체가 만들어진다.
     *
     * 전체 이용률도 들어가지만 현재 사용하고 있지 않음.
     */
    fun reloadTable() {
        val file = File(tableDir, TABLE_NAME)
        val lines = FileReader(file)
                .readLines()
                .map { it.split(",") }

        val columnName = lines.first()
        lines.drop(1)
                .map { headTail(it) }
                .map { p ->
                    val year = p.first.toInt()
                    p.second
                            .drop(1)
                            .mapIndexed { i, value ->
                                DeviceUsage(year, columnName[i + 2], value.toDoubleOrNull() ?: 0.0) }
                }
                .forEach(deviceUsageRepository::saveAll)
    }

    private fun<T> headTail(iter: Iterable<T>) = Pair(iter.first(), iter.drop(1))


    /**
     * 이름을 로드 후 이름별로 가상의 Device 를 만들어서 저장
     */
    private fun saveDummyDevices() = deviceUsageRepository.findDistinctDeviceName()
                .map { Device.createDummyDevice(it) }
                .forEach { deviceListRepository.save(it) }

    override fun afterPropertiesSet() {
        reloadTable()
        saveDummyDevices()
    }

}