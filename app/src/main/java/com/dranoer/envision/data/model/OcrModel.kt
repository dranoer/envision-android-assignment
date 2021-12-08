package com.dranoer.envision.data.model

import com.google.gson.annotations.SerializedName

data class OCRModel(

    @field:SerializedName("response")
    val response: Response,
)

data class Response(

    @field:SerializedName("paragraphs")
    val paragraphs: List<Paragraph>,
)

data class Paragraph(

    @field:SerializedName("paragraph")
    val paragraph: String,

    @field:SerializedName("language")
    val language: String,
)