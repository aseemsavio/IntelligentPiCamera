package com.aseemsavio.intelligentpicamera.app

import java.time.Instant

val now = Instant.now()!!


suspend fun info(logMessage: () -> String) {  println("📸 INTELLIGENT PI CAMERA | ⏰ $now | ${logMessage()}") }

suspend fun showWelcomeMessage(message: () -> String) { println(message()) }