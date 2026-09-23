import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IssueDao {
    @Query("SELECT * FROM issues ORDER BY creationDate DESC")
    fun getAll(): Flow<List<Issue>>

    @Query("SELECT * FROM issues WHERE isSynced = 0")
    suspend fun getUnsynced(): List<Issue>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(issue: Issue)

    @Delete
    suspend fun delete(issue: Issue)
}