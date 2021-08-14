package com.aseemsavio.intelligentpicamera

import com.aseemsavio.intelligentpicamera.App.Companion.period
import com.aseemsavio.intelligentpicamera.App.Companion.timer
import com.aseemsavio.intelligentpicamera.common.showWelcomeMessage
import com.aseemsavio.intelligentpicamera.camera.dsl.cameraConfig
import com.aseemsavio.intelligentpicamera.common.info
import com.aseemsavio.intelligentpicamera.common.readImage
import com.aseemsavio.intelligentpicamera.model.infer
import com.aseemsavio.intelligentpicamera.model.loadModel
import com.aseemsavio.intelligentpicamera.server.dsl.intelligentCameraServer
import org.tensorflow.TensorFlow
import java.util.*

/**
 * @author Aseem Savio
 *
 * This app will be the entry point for this application.
 */
class App {

    // todo: Move these over to an external config later.
    companion object {
        const val period = 1000L
        val timer = Timer()
    }

}

suspend fun main() {

    showWelcomeMessage {
        """
         📸 Intelligent Pi Camera for Raspberry Pi 🥧
         🤖 Tensorflow Version: ${TensorFlow.version()}
         Model Name: ssd_inception_v2_coco
        """
    }

    val server = intelligentCameraServer {
        cameraConfig {
            interval { period }
            timer { timer }
        }
    }

    val (model, labels) = loadModel()

    server forEverAndEver {
        val results = model.infer(readImage(), labels)
        info { "Results: $results" }
    }

}
