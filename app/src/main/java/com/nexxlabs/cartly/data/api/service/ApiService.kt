package com.nexxlabs.cartly.data.api.service

import com.nexxlabs.cartly.data.api.model.ApiResponse
import com.nexxlabs.cartly.data.api.model.request.AddMemberRequest
import com.nexxlabs.cartly.data.api.model.request.CreateGroupRequest
import com.nexxlabs.cartly.data.api.model.request.CreateItemRequest
import com.nexxlabs.cartly.data.api.model.request.UpdateItemRequest
import com.nexxlabs.cartly.data.models.GroceryItem
import com.nexxlabs.cartly.data.models.ShoppingList
import com.nexxlabs.cartly.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.reflect.Member

interface ApiService {

    // --- Groups ---

    @POST("groups")
    suspend fun createGroup(
        @Body body: CreateGroupRequest
    ): Response<ApiResponse<ShoppingList>>

    @GET("users/{user_id}/groups")
    suspend fun getUserGroups(
        @Path("user_id") userId: String
    ): Response<ApiResponse<List<ShoppingList>>>

    // --- Members ---

    @POST("groups/{group_id}/members")
    suspend fun addMember(
        @Path("group_id") groupId: String,
        @Body body: AddMemberRequest
    ): Response<ApiResponse<Member>>

    @DELETE("groups/{group_id}/members/{user_id}")
    suspend fun removeMember(
        @Path("group_id") groupId: String,
        @Path("user_id") userId: String
    ): Response<ApiResponse<Unit>>

    // --- Items ---

    @POST("groups/{group_id}/items")
    suspend fun createItem(
        @Path("group_id") groupId: String,
        @Body body: CreateItemRequest
    ): Response<ApiResponse<GroceryItem>>

    @GET("groups/{group_id}/items")
    suspend fun listGroupItems(
        @Path("group_id") groupId: String
    ): Response<ApiResponse<List<GroceryItem>>>

    @PUT("groups/{group_id}/items/{item_id}")
    suspend fun updateItem(
        @Path("group_id") groupId: String,
        @Path("item_id") itemId: String,
        @Body body: UpdateItemRequest
    ): Response<ApiResponse<Unit>>

    @DELETE("groups/{group_id}/items/{item_id}")
    suspend fun deleteItem(
        @Path("group_id") groupId: String,
        @Path("item_id") itemId: String
    ): Response<ApiResponse<Unit>>

    // --- Users ---

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") userId: String
    ): Response<ApiResponse<User>>

    @GET("users/email")
    suspend fun getUserByEmail(
        @Query("email") email: String
    ): Response<ApiResponse<User>>

}
