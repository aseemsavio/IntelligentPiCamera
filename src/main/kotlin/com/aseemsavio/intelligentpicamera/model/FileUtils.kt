package com.aseemsavio.intelligentpicamera.model

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStream

class FileUtils {

    /**
     * Reads a file from resources as an [InputStream].
     */
    suspend infix fun getResource(filename: FileName): InputStream? {
        val classLoader: ClassLoader = FileUtils::class.java.classLoader
        return classLoader.getResource(filename.value).openStream()
    }

    /**
     * Reads a file from resources as a [String].
     */
    suspend infix fun getResourceAsString(filename: FileName): String {
        val classLoader: ClassLoader = FileUtils::class.java.classLoader
        return classLoader.getResource(filename.value).openStream().bufferedReader().use(BufferedReader::readText)
    }

    /**
     * Reads a file from resources as an object - [T] provided the JSON string.
     */
    suspend inline fun <reified T> getResourceAs(jsonString: String): T =
        Json.decodeFromString<T>(jsonString)

}