package com.aseemsavio.intelligentpicamera.model

@JvmInline
value class ImageLabel(val value: String)

@JvmInline
value class ImageScore(val value: Float)

data class DetectedObject(
    val label: ImageLabel,
    val score: ImageScore
)
