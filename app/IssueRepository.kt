class IssueRepository(val dao: IssueDao, val api: IssueApi) {
    suspend fun sync() {
        // Send local changes up
        dao.getUnsynced().forEach {
            try {
                api.create(it)
                dao.save(it.copy(isSynced = true))
            } catch (e: Exception) {
                // stays unsynced, will retry later
            }
        }
        // Pull server changes down
        try {
            api.getIssues().forEach {
                dao.save(it.copy(isSynced = true))
            }
        } catch (e: Exception) { }
    }
}