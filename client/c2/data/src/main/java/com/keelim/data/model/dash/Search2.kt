package com.keelim.data.model.dash

data class Search2(
    val categoryId: Int,
    val challengeDescribe: String,
    val challengeId: Int,
    val challengeTitle: String,
    val currentMembers: Int,
    val endDate: String,
    val imagePath: String,
    val managerId: Int,
    val maxMembers: Int,
    val minMembers: Int,
    val periodId: Int,
    val startDate: String,
    val totalPoint: Int,
)
