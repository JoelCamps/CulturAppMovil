import java.io.Serializable

data class Usuario(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val email: String,
    val contra: String,
    val tipo: Tipo,
    val activo: Boolean
    ) : Serializable {

    enum class Tipo(val value: String) {
        SUPER("super"),
        ORGANIZADOR("organizador"),
        BASICO("basico");

        companion object {
            fun fromValue(value: String): Tipo {
                return when (value) {
                    "super" -> SUPER
                    "organizador" -> ORGANIZADOR
                    "basico" -> BASICO
                    else -> throw IllegalArgumentException("Tipo de usuario desconocido: $value")
                }
            }
        }
    }
}