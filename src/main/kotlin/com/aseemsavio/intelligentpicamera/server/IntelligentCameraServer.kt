package com.aseemsavio.intelligentpicamera.server

import com.aseemsavio.intelligentpicamera.model.ImageInferenceTask
import java.util.*

@JvmInline
value class Interval(val value: Long)

/**
 * This class will contain the Configuration required to operate the camera.
 */
data class CameraConfig(
    val interval: Interval,
    val timer: Timer,
)

interface Server {
    suspend fun forEverAndEver(lambda: () -> Unit)
    suspend fun stop()
}

class IntelligentCameraServer(private val cameraConfig: CameraConfig) : Server {

    override suspend infix fun forEverAndEver(lambda: () -> Unit) =
        with(cameraConfig) {
            timer.schedule(ImageInferenceTask { lambda() }, 0, interval.value)
        }

    override suspend fun stop() {
        TODO("Not yet implemented")
    }

}