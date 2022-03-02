package com.keelim.data.model

data class Topic(
    val name: String,
    val courses: Int,
    val imageUrl: String
)


val topics = listOf(
    Topic("I", 321, "https://source.unsplash.com/8nXKXYdO-Wk"),
    Topic("LOVE", 321, "https://source.unsplash.com/8nXKXYdO-Wk"),
    Topic("Samsung", 321, "https://source.unsplash.com/8nXKXYdO-Wk"),
    Topic("Algorithms", 58, "https://source.unsplash.com/RFDP7_80v5A"),
    Topic("DB", 121, "https://source.unsplash.com/Tq4YjCa2BSc"),
    Topic("Network", 78, "https://source.unsplash.com/MYbhN8KaaEc"),
    Topic("OS", 118, "https://source.unsplash.com/uB7q7aipU2o"),
    Topic("DataStructure", 423, "https://source.unsplash.com/cXkrqY2wFyc"),
    Topic("AI", 92, "https://source.unsplash.com/omgRZCmTvUM"),
    Topic("Big DATA", 165, "https://source.unsplash.com/9lTUAlNB87M"),
    Topic("Block Chain", 164, "https://source.unsplash.com/xITnxxlzGAE"),
    Topic("Meet Up", 326, "https://source.unsplash.com/lxoq0zppH5w"),
    Topic("Google", 305, "https://source.unsplash.com/DzIt-fTYv4E"),
    Topic("Meta", 212, "https://source.unsplash.com/LoppUA_9F1w"),
    Topic("Netflix", 172, "https://source.unsplash.com/FwF_fKj5tBo"),
)