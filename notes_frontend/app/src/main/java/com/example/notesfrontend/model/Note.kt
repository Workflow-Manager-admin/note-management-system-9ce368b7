package com.example.notesfrontend.model

import java.util.UUID

// PUBLIC_INTERFACE
data class Note(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var content: String,
    val createdAt: Long = System.currentTimeMillis(),
    var updatedAt: Long = System.currentTimeMillis()
)
