package com.swahilib.data.sources.local.daos

import androidx.room.*
import com.swahilib.core.utils.Collections
import com.swahilib.data.models.Idiom
import com.swahilib.data.models.Proverb
import kotlinx.coroutines.flow.Flow

@Dao
interface IdiomDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(idiom: Idiom)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(idioms: List<Idiom>)

    @Update
    fun update(idiom: Idiom)

    @Query("SELECT * FROM ${Collections.IDIOMS} WHERE rid = :rid")
    fun getById(rid: String): Flow<Idiom>

    @Query("DELETE FROM ${Collections.IDIOMS}")
    fun delete()

    @Query("SELECT * FROM ${Collections.IDIOMS} WHERE title LIKE '%' || :title || '%'")
    fun searchIdiomByTitle(title: String?): Flow<List<Idiom>>

    @Query("SELECT * FROM ${Collections.IDIOMS} WHERE title IN (:titles)")
    fun getIdiomsByTitles(titles: List<String>): Flow<List<Idiom>>

    @Query("SELECT * FROM ${Collections.IDIOMS}")
    fun getAll(): Flow<List<Idiom>>
}