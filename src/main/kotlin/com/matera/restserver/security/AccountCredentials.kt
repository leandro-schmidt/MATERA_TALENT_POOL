package com.matera.restserver.security

data class AccountCredentials(
    var username: String? = null,
    var password: String? = null
)