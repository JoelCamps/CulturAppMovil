import java.io.Serializable

data class Users(
    val id: Int?,
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    val type: String,
    val active: Boolean ) : Serializable