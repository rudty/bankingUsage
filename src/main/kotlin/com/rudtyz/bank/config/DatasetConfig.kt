package com.rudtyz.bank.config

import org.springframework.boot.ApplicationArguments
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class DatasetConfig {

    companion object {
        private const val DATASET_PATH = "DATASET_PATH"
    }

    /**
     * 시작 시 검사.
     * 1. command line 에 설정 파일 디렉터리가 들어왔는지 검사 (--DATASET_PATH=./path)
     * 2. 환경 변수에 설정 파일 디렉터리가 들어왔는지 검사
     * 3. working 디렉터리와 같은 경로
     *
     * @return 설정 디렉터리 경로
     */
    private fun findDatasetDir(args: ApplicationArguments): String {
        val argumentPath = args
                .getOptionValues(DATASET_PATH)
                ?.first()
                ?: ""

        if (argumentPath.isNotEmpty()) {
            return argumentPath
        }

       return System.getenv(DATASET_PATH) ?: System.getProperty("user.dir")
    }

    @Bean
    fun getDatasetDir(args: ApplicationArguments): String {
        return findDatasetDir(args)
    }
}
