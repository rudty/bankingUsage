package com.rudtyz.bank.model

import javax.persistence.*

/**
 * 데이터 이용 파일 -> 객체
 * 본 클래스 는 파일을 읽은 후 DB 삽입 전용입니다.
 */
@Entity
@Table(indexes = [
        Index(columnList = "year, deviceName"),
        Index(columnList = "deviceName, rate")])
data class DeviceUsage(
        @Id
        @Column
        var year: Int = 0,

        @Column
        var deviceName: String = "",

        @Column
        var rate: Double = 0.0
)
