package com.aseemsavio.intelligentpicamera.model

import kotlinx.coroutines.runBlocking
import java.util.*

class ImageInferenceTask(val lambda: () -> Unit) : TimerTask() {
    override fun run() {
        runBlocking { lambda() }
    }
}

