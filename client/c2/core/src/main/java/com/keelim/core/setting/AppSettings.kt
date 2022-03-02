//package com.keelim.core.setting
//
//import kotlinx.coroutines.flow.Flow
//
//interface AppSettings {
//
//    suspend fun setTheaterFilter(theaterFilter: TheaterFilter)
//    fun getTheaterFilterFlow(): Flow<TheaterFilter>
//
//    suspend fun setAgeFilter(ageFilter: AgeFilter)
//    fun getAgeFilterFlow(): Flow<AgeFilter>
//
//    suspend fun setGenreFilter(genreFilter: GenreFilter)
//    fun getGenreFilterFlow(): Flow<GenreFilter>
//
//    suspend fun setThemeOption(themeOption: String)
//    suspend fun getThemeOption(): String
//    fun getThemeOptionFlow(): Flow<String>
//
//    suspend fun setFavoriteTheaterList(list: List<Theater>)
//    suspend fun getFavoriteTheaterList(): List<Theater>
//    fun getFavoriteTheaterListFlow(): Flow<List<Theater>>
//}