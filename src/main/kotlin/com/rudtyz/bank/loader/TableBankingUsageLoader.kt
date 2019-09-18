package com.rudtyz.bank.loader

import com.rudtyz.bank.model.DeviceUsage
import com.rudtyz.bank.repository.local.DeviceUsageRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileReader

@Component
class TableBankingUsageLoader(
        private val tableDir: String,
        private val deviceUsageRepository: DeviceUsageRepository
) : InitializingBean {
    /**
     * data.csv 를 읽고 해당 내용을 Map<Int, DeviceUsage> 로 변환
     * 설정 파일이 2개 이상 시 본 함수를 외부로 분리 할 것.
     */

    fun reloadTable() {
        val file = File(tableDir, "data.csv")
        val lines = FileReader(file)
                .readLines()
                .map { it.split(",") }

        val columnName = lines.first()
        lines.drop(1)
                .forEach { line ->
                    val year = line[0].toInt()
                    line.drop(1)
                            .forEachIndexed { i, value ->
                                deviceUsageRepository.save(
                                        DeviceUsage(year, columnName[i + 1], value.toDoubleOrNull() ?: 0.0))
                            }
                }
    }

    override fun afterPropertiesSet() {
        reloadTable()
    }

}