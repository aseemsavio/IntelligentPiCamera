package com.aseemsavio.intelligentpicamera.common

import java.awt.image.BufferedImage
import java.io.File
import java.time.Instant
import javax.imageio.ImageIO

suspend fun info(logMessage: () -> String) {  println("📸 INTELLIGENT PI CAMERA | ⏰ ${Instant.now()} | ${logMessage()}") }

suspend fun showWelcomeMessage(message: () -> String) { println(message()) }

/**
 * Remove this later
 */
suspend fun readImage(): BufferedImage =
    ImageIO.read(File("src/main/resources/images/friends.JPG"))