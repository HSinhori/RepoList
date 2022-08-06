package net.heedstudio.repolist.data.repositories

import kotlinx.coroutines.flow.flow
import net.heedstudio.repolist.core.RemoteException
import net.heedstudio.repolist.data.services.GitHubService
import retrofit2.HttpException

class RepoRepositoryImpl(private val service: GitHubService) : RepoRepository {

    override suspend fun listRepositories(user: String) = flow {
        try {
            val repoList = service.listRepositories(user)
            emit(repoList)
        } catch (ex: HttpException) {
            throw RemoteException(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }
}