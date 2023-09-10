package com.fredericho.movies.core.movie.implementation.mapper

import com.fredericho.movies.core.movie.api.model.Author
import com.fredericho.movies.core.movie.api.model.BelongsToCollection
import com.fredericho.movies.core.movie.api.model.Cast
import com.fredericho.movies.core.movie.api.model.Credit
import com.fredericho.movies.core.movie.api.model.Crew
import com.fredericho.movies.core.movie.api.model.DetailMovie
import com.fredericho.movies.core.movie.api.model.GenresItem
import com.fredericho.movies.core.movie.api.model.Movie
import com.fredericho.movies.core.movie.api.model.ProductionCompaniesItem
import com.fredericho.movies.core.movie.api.model.ProductionCountriesItem
import com.fredericho.movies.core.movie.api.model.Review
import com.fredericho.movies.core.movie.api.model.SpokenLanguagesItem
import com.fredericho.movies.core.movie.api.model.VideoMovie
import com.fredericho.movies.core.movie.implementation.remote.response.AuthorDetailResponse
import com.fredericho.movies.core.movie.implementation.remote.response.BelongsToCollectionResponse
import com.fredericho.movies.core.movie.implementation.remote.response.CastResponse
import com.fredericho.movies.core.movie.implementation.remote.response.CreditResponse
import com.fredericho.movies.core.movie.implementation.remote.response.CrewResponse
import com.fredericho.movies.core.movie.implementation.remote.response.DetailMovieResponse
import com.fredericho.movies.core.movie.implementation.remote.response.GenreItemResponse
import com.fredericho.movies.core.movie.implementation.remote.response.MovieResponse
import com.fredericho.movies.core.movie.implementation.remote.response.ProductionCompaniesResponse
import com.fredericho.movies.core.movie.implementation.remote.response.ProductionCountriesResponse
import com.fredericho.movies.core.movie.implementation.remote.response.ReviewResponse
import com.fredericho.movies.core.movie.implementation.remote.response.SpokenLanguagesResponse
import com.fredericho.movies.core.movie.implementation.remote.response.VideoItemResponse

internal fun MovieResponse.toMovie(): Movie =
    Movie(
        overview,
        originalLanguage,
        originalTitle,
        video,
        title,
        genreIds,
        posterPath,
        backdropPath,
        releaseDate,
        popularity,
        voteAverage,
        id,
        adult,
        voteCount
    )

internal fun ProductionCompaniesResponse.toProductionCompaniesItem(): ProductionCompaniesItem =
    ProductionCompaniesItem(
        logoPath, name, id, originCountry
    )

internal fun GenreItemResponse.toGenresItem(): GenresItem = GenresItem(
    name, id
)

internal fun ProductionCountriesResponse.toProductionCountries(): ProductionCountriesItem =
    ProductionCountriesItem(iso31661, name)

internal fun BelongsToCollectionResponse.toBelongsToCollection(): BelongsToCollection =
    BelongsToCollection(backdropPath, name, id, posterPath)

internal fun SpokenLanguagesResponse.toSpokenLanguagesItem(): SpokenLanguagesItem =
    SpokenLanguagesItem(name, iso6391, englishName)

internal fun DetailMovieResponse.toDetailMovie(): DetailMovie =
    DetailMovie(
        originalLanguage,
        imdbId,
        video,
        title,
        backdropPath,
        revenue,
        genres?.map { it?.toGenresItem() },
        popularity,
        productionCountries?.map { it?.toProductionCountries() },
        id,
        voteCount,
        budget,
        overview,
        originalTitle,
        runtime,
        posterPath,
        spokenLanguages?.map { it?.toSpokenLanguagesItem() },
        productionCompanies?.map { it?.toProductionCompaniesItem() },
        releaseDate,
        voteAverage,
        belongsToCollection?.toBelongsToCollection(),
        tagline,
        adult,
        homepage,
        status
    )

internal fun VideoItemResponse.toVideoMovie(): VideoMovie = VideoMovie(
    site,
    size,
    iso31661,
    name,
    official,
    id,
    type,
    publishedAt,
    iso6391,
    key
)

internal fun CrewResponse.toCrew(): Crew =
    Crew(
        gender,
        creditId,
        knownForDepartment,
        originalName,
        popularity,
        name,
        profilePath,
        id,
        adult
    )

internal fun CastResponse.toCast(): Cast =
    Cast(
        castId,
        character,
        gender,
        creditId,
        knownForDepartment,
        originalName,
        popularity,
        name,
        profilePath,
        id,
        adult,
        order
    )

internal fun CreditResponse.toCredit(): Credit =
    Credit(
        cast = cast.map { it.toCast() },
        crew = crew.map { it.toCrew() },
    )

internal fun AuthorDetailResponse.toAuthor(): Author =
    Author(avatarPath, name, rating, username)

internal fun ReviewResponse.toReview(): Review =
    Review(
        authorDetails?.toAuthor(),
        updatedAt,
        author,
        createdAt,
        id,
        content,
        url
    )