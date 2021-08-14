package com.aseemsavio.intelligentpicamera.common

import com.aseemsavio.intelligentpicamera.model.identifiers.LabelId
import com.aseemsavio.intelligentpicamera.model.identifiers.LabelItem
import org.tensorflow.SavedModelBundle

typealias Model = Pair<SavedModelBundle, Labels>

typealias Labels = Map<LabelId, LabelItem>