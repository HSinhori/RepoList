package net.heedstudio.repolist.domain

import kotlinx.coroutines.flow.Flow
import net.heedstudio.repolist.core.UseCase
import net.heedstudio.repolist.data.model.Repo
import net.heedstudio.repolist.data.repositories.RepoRepository

class ListUserRepositoriesUseCase(
    private val repository: RepoRepository
) : UseCase<String, List<Repo>>() {

    override suspend fun execute(param: String): Flow<List<Repo>> {
        return repository.listRepositories(param)
    }
}