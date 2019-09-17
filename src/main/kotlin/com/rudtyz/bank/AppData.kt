package com.rudtyz.bank

import com.rudtyz.bank.model.Device
import com.rudtyz.bank.model.DeviceType
import com.rudtyz.bank.model.DeviceUsage
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

import javax.validation.constraints.NotNull
import java.io.*
import java.lang.RuntimeException

typealias Year = Int

@Component
class AppData {

    companion object {
        private const val CONFIG_PATH = "CONFIG_PATH"
    }

    /**
     * 시작 시 검사.
     * 1. command line 에 설정 파일 디렉터리가 들어왔는지 검사 (--CONFIG_PATH=./path)
     * 2. 환경 변수에 설정 파일 디렉터리가 들어왔는지 검사
     * 3. working 디렉터리와 같은 경로
     *
     * @return 설정 디렉터리 경로
     */
    private fun findConfigDir(args: ApplicationArguments): String {
        val argumentPath = args
                .getOptionValues(CONFIG_PATH)
                ?.first()
                ?: ""

        if (argumentPath.isNotEmpty()) {
            return argumentPath
        }

       return System.getenv(CONFIG_PATH) ?: System.getProperty("user.dir")
    }

    @Bean
    fun getConfigDir(args: ApplicationArguments): String {
        return findConfigDir(args)
    }

    /**
     * data.csv 를 읽고 해당 내용을 Map<Int, DeviceUsage> 로 변환
     * 설정 파일이 2개 이상 시 본 함수를 외부로 분리 할 것.
     */
    @Throws(IOException::class)
    private fun loadDeviceUsage(configDir: String): Map<Year, DeviceUsage> {
        val file = File(configDir, "data.csv")
        val m = HashMap<Int, DeviceUsage>()
        BufferedReader(FileReader(file)).use { stream ->
            stream.readLine() // 1 line skip
            while (true) {
                val line = stream.readLine() ?: break
                val device = DeviceUsage.fromCsvLine(line.split(","))
                m[device.year] = device
            }
        }
        return m
    }

    @Bean
    fun getDeviceUsage(configDir: String): Map<Year, DeviceUsage> {
       return loadDeviceUsage(configDir)
    }
}
