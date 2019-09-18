package com.rudtyz.bank

import org.springframework.boot.ApplicationArguments
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

typealias Year = Int

@Component
class AppData {

    companion object {
        private const val TABLE_PATH = "TABLE_PATH"
    }

    /**
     * 시작 시 검사.
     * 1. command line 에 설정 파일 디렉터리가 들어왔는지 검사 (--TABLE_PATH=./path)
     * 2. 환경 변수에 설정 파일 디렉터리가 들어왔는지 검사
     * 3. working 디렉터리와 같은 경로
     *
     * @return 설정 디렉터리 경로
     */
    private fun findConfigDir(args: ApplicationArguments): String {
        val argumentPath = args
                .getOptionValues(TABLE_PATH)
                ?.first()
                ?: ""

        if (argumentPath.isNotEmpty()) {
            return argumentPath
        }

       return System.getenv(TABLE_PATH) ?: System.getProperty("user.dir")
    }

    @Bean
    fun getTableDir(args: ApplicationArguments): String {
        return findConfigDir(args)
    }
}
