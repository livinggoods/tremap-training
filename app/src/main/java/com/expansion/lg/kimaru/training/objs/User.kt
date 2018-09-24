package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/22/18.
 */

class User {
    var id: Int = 0
    var email: String
    var userName: String
    var passWord: String
    var country: String
    var name: String

    constructor() {}

    constructor(id: Int, email: String, userName: String, passWord: String, country: String, name: String) {
        this.id = id
        this.email = email
        this.userName = userName
        this.passWord = passWord
        this.country = country
        this.name = name
    }
}
