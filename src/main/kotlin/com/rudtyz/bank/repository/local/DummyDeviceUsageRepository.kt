package com.rudtyz.bank.repository.local

import com.rudtyz.bank.model.DeviceUsage
import org.springframework.data.jpa.repository.JpaRepository

/**
 *  데이터 파일(서울시 인터넷뱅킹 이용률 및 이용기기 통계 데이터)에서 각 레코드를 데이터베이스에 저장하는 코드를 구현하세요.
 *  o 단, 데이터 파일은 로컬(서버)의 특정 디렉토리에 저장되어 있고, 어플리케이션이 로딩되는 시점에서 데이터베이스에 적재되게 구현 요망.
 *  o 단, 데이터 파일을 로컬 파일 시스템에서 로딩하여 적재하는 API를 개발하여도 무방함.
 *
 *  데이터는 데이터베이스에 일단 저장하지만 실제로 활용하지는 않음
 *  활용은 DeviceUsageRepository 참고 할 것.
 */
interface DummyDeviceUsageRepository: JpaRepository<DeviceUsage, Int>