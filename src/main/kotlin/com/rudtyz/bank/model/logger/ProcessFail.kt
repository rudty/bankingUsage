package com.rudtyz.bank.model.logger

import java.util.*
import javax.persistence.*

@Entity
@Table(indexes = [
    Index(columnList = "createAt")])
data class ProcessFail (
        @Column(length = 4096)
        val reason: String = "",

        @Column
        val method: String = "",

        @Column
        val url: String = "",

        @Column
        val remoteAddr: String = "",

        @Id
        @Column
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int = 0,

        @Column
        val createAt: Date = Date()
)