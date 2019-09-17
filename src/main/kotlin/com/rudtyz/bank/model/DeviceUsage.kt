package com.rudtyz.bank.model

import org.hibernate.validator.constraints.Range
import java.lang.RuntimeException
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.Validation
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Max
import javax.validation.constraints.Min

/**
 * 데이터 이용 파일 -> 객체
 * 본 클래스 는 파일을 읽은 후 DB 삽입 전용입니다.
 */
@Entity
data class DeviceUsage(
        @Id
        @Column
        var year: Int = 0,

        @DecimalMin(value = "0.0", message = "totalUsage range 0 ~ ")
        @DecimalMax(value = "100.0", message = "totalUsage range  ~ 100 ")
        @Column
        var totalUsage: Double = 0.0,

        @DecimalMin(value = "0.0", message = "smartPhoneUsage range 0 ~ ")
        @DecimalMax(value = "100.0", message = "smartPhoneUsage range  ~ 100 ")
        @Column
        var smartPhoneUsage: Double = 0.0,

        @DecimalMin(value = "0.0", message = "desktopUsage range 0 ~ ")
        @DecimalMax(value = "100.0", message = "desktopUsage range  ~ 100 ")
        @Column
        var desktopUsage: Double = 0.0,

        @DecimalMin(value = "0.0", message = "desktopUsage range 0 ~ ")
        @DecimalMax(value = "100.0", message = "desktopUsage range  ~ 100 ")
        @Column
        var notebookUsage: Double = 0.0,

        @DecimalMin(value = "0.0", message = "desktopUsage range 0 ~ ")
        @DecimalMax(value = "100.0", message = "desktopUsage range  ~ 100 ")
        @Column
        var otherUsage: Double = 0.0,

        @DecimalMin(value = "0.0", message = "desktopUsage range 0 ~ ")
        @DecimalMax(value = "100.0", message = "desktopUsage range  ~ 100 ")
        @Column
        var smartPadUsage: Double = 0.0
) {
    companion object {

        @JvmStatic
        fun     fromCsvLine(line: List<String>): DeviceUsage{
            if (line.size != 7) {
                throw RuntimeException("bad DeviceUsage file exception $line, lineCount: ${line.size}")
            }

//            val hashMap = hashMapOf("totalUsage")

            val r = DeviceUsage(
                    line[0].toInt(),
                    line[1].toDoubleOrNull() ?: 0.0,
                    line[2].toDoubleOrNull() ?: 0.0,
                    line[3].toDoubleOrNull() ?: 0.0,
                    line[4].toDoubleOrNull() ?: 0.0,
                    line[5].toDoubleOrNull() ?: 0.0,
                    line[6].toDoubleOrNull() ?: 0.0
            )

            val k = Validation.buildDefaultValidatorFactory().validator.validate(r)
            k.forEach { println(it.message) }
            println(k)
            println(r)
            return r;
        }
    }
}
