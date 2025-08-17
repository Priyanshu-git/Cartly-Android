package com.nexxlabs.cartly.data.api.model.response

import com.nexxlabs.cartly.data.models.User

data class LoginResponse(val token: String, val user: User)