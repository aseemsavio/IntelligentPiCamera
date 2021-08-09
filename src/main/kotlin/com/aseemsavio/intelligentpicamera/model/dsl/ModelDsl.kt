package com.aseemsavio.intelligentpicamera.model.dsl

import com.aseemsavio.intelligentpicamera.app.info
import com.aseemsavio.intelligentpicamera.model.ModelManager
import com.github.chen0040.objdetect.ObjectDetector

/**
 * Returns a [ModelManager] object and executes an optional lambda.
 */
suspend fun modelManager(logMessage: () -> String): ModelManager = ModelManager().also { info { logMessage() } }

/**
 * Loads the [ObjectDetector] model and returns it.
 */
suspend fun loadModel(modelManager: ModelManager, logMessage: () -> String): ObjectDetector = modelManager.loadModel().also { info { logMessage() } }

