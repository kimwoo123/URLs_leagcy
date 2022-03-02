package com.keelim.data.model.open

data class LinkSourceContent(
    var ogTitle: String,
    var ogDescription: String,
    var ogUrl: String,
    var images: String,
    var ogSiteName: String,
    var ogType: String
) {
    constructor() : this("", "", "", "", "", "")
}