package com.github.ncliff.passet.model.boxes.room

import com.github.ncliff.passet.model.boxes.BoxesRepository
import com.github.ncliff.passet.model.boxes.entities.Box
import com.github.ncliff.passet.model.boxes.entities.BoxCreateData
import kotlinx.coroutines.flow.Flow

class RoomBoxesRepository: BoxesRepository {
    override suspend fun createBox(boxCreateData: BoxCreateData) {
        TODO("Not yet implemented")
    }

    override suspend fun getBox(): Flow<Box?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateBox(box: Box) {
        TODO("Not yet implemented")
    }
}