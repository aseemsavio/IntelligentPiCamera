package com.aseemsavio.intelligentpicamera.camera.dsl

import com.aseemsavio.intelligentpicamera.server.CameraConfig
import com.aseemsavio.intelligentpicamera.server.Interval
import java.util.*

/**
 * Accepts a lambda that will be run within the context of the [CameraConfigBuilder] class.
 */
fun cameraConfig(lambda: CameraConfigBuilder.() -> Unit): CameraConfig =
    CameraConfigBuilder().apply(lambda).build()

/**
 * Builds a [CameraConfig] object with a kick-ass DSL.
 */
class CameraConfigBuilder {
    private var interval = Interval(500L)
    private var timer = Timer()

    fun interval(lambda: () -> Long) { this.interval = Interval(lambda()) }
    fun timer(lambda: () -> Timer) { this.timer = lambda() }
    fun build() = CameraConfig(interval, timer)
}