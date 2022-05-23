package com.aravind.pepultask.data.local.db.dao

import androidx.room.*
import com.aravind.pepultask.data.local.db.entity.PostEntity
import com.aravind.pepultask.data.model.Post
import kotlinx.coroutines.flow.Flow


@Dao
abstract class PostDao {
    @Transaction
    @Query("SELECT * FROM post_entity")
    abstract fun getAll(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entity: List<PostEntity>)

    @Delete
    abstract suspend fun delete(entity: PostEntity)

    suspend fun preparePostAndCreator(posts: List<Post>){
        for(item in posts){
            insert(PostEntity(item.id,item.file,item.file_type))
        }
    }

}