package com.fredericho.movies.core.genres.implementation.remote.response

import com.google.gson.annotations.SerializedName

data class BaseGenreResponse<T>(

	@field:SerializedName("genres")
	val genres: List<T>
)

data class GenreResponse(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
