package com.aseemsavio.intelligentpicamera.model

import com.aseemsavio.intelligentpicamera.app.info
import com.aseemsavio.intelligentpicamera.common.Model
import com.aseemsavio.intelligentpicamera.model.identifiers.LabelItem
import com.aseemsavio.intelligentpicamera.common.Labels
import net.lingala.zip4j.core.ZipFile
import org.tensorflow.SavedModelBundle
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

private const val MODEL_PARENT_DIR = "model"
private const val MODEL_DIR_NAME = "ssd_inception_v2_coco"

@JvmInline
value class ImageLabel(val value: String)

@JvmInline
value class ImageScore(val value: Float)

@JvmInline
value class FileName(val value: String) {
    init {
        assert(value.contains("/"))
    }
}


data class DetectedObject(
    val label: ImageLabel,
    val score: ImageScore
)

/**
 * Loads the SSD Inception V2 COCO Model.
 * Returns a [Pair] containing the [SavedModelBundle] and [Labels].
 */
internal suspend fun loadModel(): Model {
    val utils = FileUtils()
    extractModel(utils)

    val labels = loadLabels(utils)

    val modelDirPath = FileName("${File(MODEL_PARENT_DIR).absolutePath}/$MODEL_DIR_NAME")
    val model = loadDeepLearningModel(modelDirPath, "serve")
    return Pair(model, labels)
}

private suspend fun loadDeepLearningModel(modelDirPath: FileName, vararg arguments: String) =
    SavedModelBundle.load(modelDirPath.value, *arguments)
        .also { info { "Loaded model successfully!" } }

/**
 * Extracts the model from the zipped file and stores it into [model] directory.
 */
private suspend fun extractModel(utils: FileUtils) {
    val modelParentDir = File(MODEL_PARENT_DIR)
    if (!modelParentDir.exists()) modelParentDir.mkdir()

    val modelDirPath = "${modelParentDir.absolutePath}/$MODEL_DIR_NAME"
    val modelPath = "$modelDirPath/saved_model.pb"

    val modelFile = File(modelPath)
    if (modelFile.exists()) return

    val modelDir = File(modelDirPath)
    if (!modelDir.exists()) modelDir.mkdir()

    val modelZipFileName = "${modelParentDir.absolutePath}/saved_model.zip"
    try {
        val inputStream = utils.getResource(FileName("tf_models/saved_model.zip"))
        val outputStream = FileOutputStream(File(modelZipFileName))
        val buffer = ByteArray(1024)

        writeToOutputStream(inputStream, buffer, outputStream)
        closeStreams(inputStream, outputStream)
        unzip(modelZipFileName, modelDirPath)
    } catch (e: Exception) {
        println("Exception occurred: $e")
    }
}


/**
 * Reads the labels from the [mscoco_label_map.json] file and converts it into a map.
 */
private suspend fun loadLabels(utils: FileUtils): Labels {
    val labelsAsString = utils.getResourceAsString(FileName("labels/mscoco_label_map.json"))
    return utils.getResourceAs<List<LabelItem>>(labelsAsString).associateBy { it.id }
}

private suspend fun unzip(modelZipFileName: String, modelDirPath: String) =
    ZipFile(modelZipFileName).extractAll(modelDirPath)


private suspend fun closeStreams(inputStream: InputStream?, outputStream: FileOutputStream) {
    inputStream?.close()
    outputStream?.close()
}

private suspend fun writeToOutputStream(
    inputStream: InputStream?,
    buffer: ByteArray,
    outputStream: FileOutputStream
) {
    var length: Int
    while (inputStream?.read(buffer).also { length = it!! }!! > 0) {
        outputStream.write(buffer, 0, length)
    }
}

