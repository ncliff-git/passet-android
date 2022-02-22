package com.github.ncliff.passet.model.boxes.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.ncliff.passet.model.boxes.room.entities.BoxDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BoxesDao {
    @Update
    suspend fun updateBox(boxDbEntity: BoxDbEntity)

    @Insert
    suspend fun createBox(boxDbEntity: BoxDbEntity)

    @Query("SELECT * FROM boxes WHERE id = :boxId")
    fun getById(boxId: Long): Flow<BoxDbEntity?>
}