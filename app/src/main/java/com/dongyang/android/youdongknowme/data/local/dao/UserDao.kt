package com.dongyang.android.youdongknowme.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dongyang.android.youdongknowme.data.local.entity.KeywordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM keyword ORDER BY name DESC")
    fun getAllKeyword(): Flow<List<KeywordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeywordList(keyword: List<KeywordEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeyword(keyword: KeywordEntity)

    @Query("UPDATE keyword SET isSubscribe = :isSubscribe WHERE name = :name")
    suspend fun updateKeyword(isSubscribe: Boolean, name: String)

    @Query("DELETE FROM keyword WHERE name = :name")
    suspend fun deleteKeyword(name: String)
}