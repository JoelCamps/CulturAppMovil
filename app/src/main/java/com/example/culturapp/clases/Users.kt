import java.io.Serializable

data class Users(
    val id: Int?,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val type: String,
    val active: Boolean ) : Serializable