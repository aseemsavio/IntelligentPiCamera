package com.aseemsavio.intelligentpicamera.model

import com.github.chen0040.objdetect.ObjectDetector
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

@JvmInline
value class ImageLabel(val value: String)

@JvmInline
value class ImageScore(val value: Float)

data class DetectedObject(
    val label: ImageLabel,
    val score: ImageScore
)

interface Model {
    suspend fun loadModel(): ObjectDetector
    suspend fun readImage(): BufferedImage
}

/**
 * Code related to the Deep Learning Model
 */
class ModelManager : Model {

    /**
     * Loads the model
     */
    override suspend fun loadModel(): ObjectDetector {
        val objectDetector = ObjectDetector()
        objectDetector.loadModel()
        return objectDetector
    }

    /**
     * Reads image from the Raspberry Pi Camera
     */
    override suspend fun readImage(): BufferedImage =
        ImageIO.read(File("src/main/resources/images/friends.JPG")) // todo: replace with actual code
}

/**
 * Gives a list of objects detected in a given image.
 */
suspend infix fun ObjectDetector.infer(image: BufferedImage): List<DetectedObject> {
    return detectObjects(image).map { DetectedObject(ImageLabel(it.label), ImageScore(it.score)) }
}