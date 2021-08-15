package com.aseemsavio.intelligentpicamera.common

import java.awt.image.BufferedImage
import java.io.InputStream
import java.time.Instant
import javax.imageio.ImageIO


suspend fun info(logMessage: () -> String) {  println("📸 INTELLIGENT PI CAMERA | ⏰ ${Instant.now()} | ${logMessage()}") }

suspend fun showWelcomeMessage(message: () -> String) { println(message()) }



class ImageLoader {

    /**
     * Remove this later
     */
    suspend fun readImage(): BufferedImage {
        val data: InputStream = Thread.currentThread().contextClassLoader.getResourceAsStream("images/friends.jpg")
        return ImageIO.read(data)
    }

}