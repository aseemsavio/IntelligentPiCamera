package com.aseemsavio.intelligentpicamera.model

import com.aseemsavio.intelligentpicamera.common.Labels
import com.aseemsavio.intelligentpicamera.model.identifiers.LabelId
import org.tensorflow.SavedModelBundle
import org.tensorflow.Tensor
import org.tensorflow.types.UInt8
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.io.IOException
import java.nio.ByteBuffer

/**
 * Analyses the image and returns the list of objects the model found.
 *
 */
suspend fun SavedModelBundle.infer(image: BufferedImage, labels: Labels): List<DetectedObject> {
    val input = makeImageTensor(image)
    val outputs = session().runner()
        .feed("image_tensor", input)
        .fetch("detection_scores")
        .fetch("detection_classes")
        .run()

    /*
     * The following tensors have 1 as the first dimension and maxObjects as the second dimension.
     * This can be verified by scoresTensor.shape() for instance.
     */
    val scoresTensor = outputs[0]
    val classesTensor = outputs[1]

    val maxObjects = scoresTensor.shape()[1].toInt()

    val scores = scoresTensor.copyTo(Array(1) {
        FloatArray(maxObjects)
    })[0].toList()

    val classes = classesTensor.copyTo(Array(1) {
        FloatArray(maxObjects)
    })[0].toList()

    return classes.zip(scores) { imgClass, score ->
        DetectedObject(
            ImageLabel(labels[LabelId(imgClass.toInt())]?.displayName?.value!!),
            ImageScore(score)
        )
    }.filter { it.score > ImageScore(0.5F) }
}

/**
 * Makes image tensor.
 */
private suspend fun makeImageTensor(img: BufferedImage): Tensor<UInt8?>? {
    if (img.type != BufferedImage.TYPE_3BYTE_BGR) {
        throw IOException("Expected 3-byte BGR encoding in BufferedImage, found ${img.type}. This code could be made more robust")
    }
    val data = (img.data.dataBuffer as DataBufferByte).data
    /* ImageIO.read seems to produce BGR-encoded images, but the model expects RGB. */
    bgr2rgb(data)
    val batchSize: Long = 1
    val channels: Long = 3
    val shape = longArrayOf(batchSize, img.height.toLong(), img.width.toLong(), channels)
    return Tensor.create(UInt8::class.java, shape, ByteBuffer.wrap(data))
}

/**
 * Converts BGR encoded images to RGB images.
 */
private suspend fun bgr2rgb(data: ByteArray) {
    var i = 0
    while (i < data.size) {
        val tmp = data[i]
        data[i] = data[i + 2]
        data[i + 2] = tmp
        i += 3
    }
}
