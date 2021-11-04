package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.api.APIConstants
import no.usn.mob3000_disky.api.filter.PostFilterByUser
import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.User

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface MyProfileAPIService {

    @POST("get")
    suspend fun getProfileFeed(@Body user: User): List<Post>

    @POST("create")
    suspend fun createFeedPost(@Body post: Post): Post

//    companion object {
//        var apiService: MyProfileAPIService? = null
//        fun getInstance() : MyProfileAPIService {
//            if (apiService == null) {
//                apiService = Retrofit.Builder()
//                    .baseUrl(APIConstants.APIHOST + APIConstants.APIPORT + APIConstants.APIPOSTPREFIX)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build().create(MyProfileAPIService::class.java)
//            }
//            return apiService!!
//        }
//    }
}