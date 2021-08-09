package com.aseemsavio.intelligentpicamera

import com.github.chen0040.objdetect.ObjectDetector
import java.io.File
import javax.imageio.ImageIO

/**
 * @author Aseem Savio
 *
 * This app will be the entry point for this application.
 */
class App {

}

fun main() {

    val objectDetector = ObjectDetector()
    objectDetector.loadModel()

    val image = ImageIO.read(File("src/main/resources/images/friends.JPG"))
    val results = objectDetector.detectObjects(image)
    println(results)

}
