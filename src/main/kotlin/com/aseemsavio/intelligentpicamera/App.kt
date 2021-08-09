package com.aseemsavio.intelligentpicamera

import com.aseemsavio.intelligentpicamera.camera.dsl.cameraConfig
import com.aseemsavio.intelligentpicamera.server.IntelligentCameraServer
import java.util.*

/**
 * @author Aseem Savio
 *
 * This app will be the entry point for this application.
 */
class App {

}

suspend fun main() {

    // todo: Move these over to an external config later.
    val period: Long = 2000

    val cameraConfig = cameraConfig {
        interval { period }
        timer { Timer() }
    }

    val server = IntelligentCameraServer(cameraConfig)

    server forEverAndEver {
        println("I'm being run every n seconds.")
        println("Hey! multiple instruction")
    }

    /*val objectDetector = ObjectDetector()
    objectDetector.loadModel()

    val image = ImageIO.read(File("src/main/resources/images/friends.JPG"))
    val results = objectDetector.detectObjects(image)
    println(results)*/

}
