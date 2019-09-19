package com.rudtyz.bank.controller

import com.rudtyz.bank.repository.global.DeviceListRepository
import com.rudtyz.bank.service.DeviceUsageService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/device")
class DeviceController(
        private val deviceUsageService: DeviceUsageService,
        private val deviceListRepository: DeviceListRepository
) {
    /**
     * 디바이스 아이디를 입력받아 인터넷뱅킹에 접속 비율이 가장 많은 해를 출력하세요.
     */
    @GetMapping("/rank/id/{deviceId}")
    fun singleUsageByDeviceId(@PathVariable deviceId: String): Any? = deviceUsageService.getMostUsageYear(deviceId)

    /**
     * 특정 년도를 입력받아 그 해에 인터넷뱅킹에 가장 많이 접속하는 기기 이름을 출력하세요.
     */
    @GetMapping("/rank/year/{year}")
    fun singleUsageByYear(@PathVariable year: Int): Any? = deviceUsageService.getDeviceUsage(year)

    /**
     * 각 년도별로 인터넷뱅킹을 가장 많이 이용하는 접속기기를 출력하는 API를 개발하세요.
     */
    @GetMapping("/all")
    fun listDevices(): Any? = deviceListRepository.findAll()

    /**
     * 단, 데이터 파일을 로컬 파일 시스템에서 로딩하여 적재하는 API를 개발하여도 무방함
     */
    @GetMapping("/reload")
    fun singleReloadTable(): Any? {
        deviceUsageService.reloadTable()
        return "OK"
    }
}
