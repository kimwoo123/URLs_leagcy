package com.keelim.domain.domain

import javax.inject.Inject

class SendTokenServerUseCase @Inject constructor() {
    suspend operator fun invoke(accessToken: String) {
    }
}
