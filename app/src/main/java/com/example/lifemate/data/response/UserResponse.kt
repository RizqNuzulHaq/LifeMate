package com.example.lifemate.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("idUser")
	val idUser: Int? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("birthDate")
	val birthDate: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
