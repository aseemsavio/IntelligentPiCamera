package com.aseemsavio.intelligentpicamera.model.identifiers

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class LabelName(val value: String)

@JvmInline
@Serializable
value class LabelId(val value: Int)

@JvmInline
@Serializable
value class LabelDisplayName(val value: String)

@Serializable
data class LabelItem(
    val name: LabelName,
    val id: LabelId,
    val displayName: LabelDisplayName
)