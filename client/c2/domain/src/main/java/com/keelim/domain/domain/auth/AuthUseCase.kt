//package com.keelim.domain.domain.auth
//class AuthUseCase(
//  private val loginRepository: LoginRepository
//) {
//
//  suspend fun login(username: String, password: String): Result<LoggedInUser> {
//    return loginRepository.login(username, password)
//  }
//
//  suspend fun logout() {
//    loginRepository.logout()
//  }
//
//  suspend fun signup(
//    username: String,
//    password: String,
//    nickname: String,
//  ): Result<LoggedInUser> {
//    return loginRepository.signup(username, password, nickname)
//  }
//}
