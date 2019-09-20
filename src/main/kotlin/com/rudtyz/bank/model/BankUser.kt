package com.rudtyz.bank.model

import javax.persistence.*

@Entity
@Table(indexes = [Index(columnList = "permission")])
data class BankUser (
        @Id
        @Column
        var userId: String = "",

        @Column
        var hashPassword: String = "",

        @Column
        var permission: Int = 0
) {
    companion object {
        const val PERMISSION_ADMIN = 1
    }
}