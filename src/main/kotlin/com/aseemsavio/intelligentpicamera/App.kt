package com.aseemsavio.intelligentpicamera

import org.tensorflow.SavedModelBundle
import org.tensorflow.Tensor
import org.tensorflow.TensorFlow
import org.tensorflow.types.UInt8
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO

/**
 * @author Aseem Savio
 *
 * This app will be the entry point for this application.
 */
class App {

}

fun main() {
    println(TensorFlow.version())

    val model = SavedModelBundle.load("src/main/resources/tf_models/openimages_v4_ssd_mobilenet_v2_1/saved_model")
    val image = ImageIO.read(File("src/main/resources/images/friends.JPG"))
    val input = makeImageTensor(image)

    val output: MutableList<Tensor<*>> = model
        .session()
        .runner()
        .feed("image_tensor", input)
        .fetch("detection_class_labels")
        .run()

    println(output)
}

private fun bgr2rgb(data: ByteArray) {
    var i = 0
    while (i < data.size) {
        val tmp = data[i]
        data[i] = data[i + 2]
        data[i + 2] = tmp
        i += 3
    }
}

@Throws(IOException::class)
fun makeImageTensor(img: BufferedImage): Tensor<UInt8?>? {
    if (img.type != BufferedImage.TYPE_3BYTE_BGR) {
        throw IOException(
            String.format(
                "Expected 3-byte BGR encoding in BufferedImage, found %d. This code could be made more robust",
                img.type
            )
        )
    }
    val data = (img.data.dataBuffer as DataBufferByte).data
    // ImageIO.read seems to produce BGR-encoded images, but the model expects RGB.
    bgr2rgb(data)
    val batchSize: Long = 1
    val channels: Long = 3
    val shape = longArrayOf(batchSize, img.height.toLong(), img.width.toLong(), channels)
    return Tensor.create(UInt8::class.java, shape, ByteBuffer.wrap(data))
}