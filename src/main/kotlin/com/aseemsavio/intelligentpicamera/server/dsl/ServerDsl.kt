package com.aseemsavio.intelligentpicamera.server.dsl

import com.aseemsavio.intelligentpicamera.server.CameraConfig
import com.aseemsavio.intelligentpicamera.server.IntelligentCameraServer

fun intelligentCameraServer(lambda: () -> CameraConfig): IntelligentCameraServer = IntelligentCameraServer(lambda())

