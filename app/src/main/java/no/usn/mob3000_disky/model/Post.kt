package no.usn.mob3000_disky.model

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class Post(
    @SerializedName("postId") var postId : Int?,
    @SerializedName("user") var user : User,
    @SerializedName("message") var message : String,
    @SerializedName("type") var type : Int,
    @SerializedName("scoreCard") var scoreCard : ScoreCard?,
    @SerializedName("postedTs") var postedTs : String,
    @SerializedName("updatedTs") var updatedTs : String,
    @SerializedName("interactions") var interactions : Interactions,
    @Ignore var sortDate: Date? = null
)

data class PostFilter(
    @SerializedName("user") var user: User,
    @SerializedName("getFromConnections") var getFromConnections: Boolean = false,
    @SerializedName("getUserLinks") var getUserLinks: Boolean = false
)

class Interactions (
    var likedByUser: Boolean = false,
    var interactions: List<Interactions>? = ArrayList(),
        )

class Interaction (
    var user: User,
    var post: Post,
    var type: Int
)





