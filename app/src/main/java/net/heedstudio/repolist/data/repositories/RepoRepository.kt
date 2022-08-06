package net.heedstudio.repolist.data.repositories

import kotlinx.coroutines.flow.Flow
import net.heedstudio.repolist.data.model.Repo

interface RepoRepository {
    suspend fun listRepositories(user: String): Flow<List<Repo>>
}