package com.tang.jkorm.spring.entity

import com.tang.jkorm.annotation.id.Id

/**
 * @author Tang
 */
class Account (

    @Id
    var id: Long? = null,
    var username: String = "",
    var password: String = ""

) {
    override fun toString(): String {
        return "'Account => id=$id username=$username password=$password'"
    }
}
