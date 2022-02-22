package com.github.ncliff.passet.model.boxes

import com.github.ncliff.passet.model.boxes.entities.Box
import com.github.ncliff.passet.model.boxes.entities.BoxCreateData
import kotlinx.coroutines.flow.Flow

interface BoxesRepository {

    suspend fun createBox(boxCreateData: BoxCreateData)

    suspend fun getBox(): Flow<Box?>

    suspend fun updateBox(box: Box)
}