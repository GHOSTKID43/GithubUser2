package com.kdt.githubuser.data.remote

import com.kdt.githubuser.data.model.ResponseUserGithub
import retrofit2.http.GET

interface GithubService {
    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserGithub():MutableList<ResponseUserGithub.Item>
}