import java.io.Serializable

data class Usuario(
    val nombre: String,
    val apellido: String,
    val email: String,
    val contra: String,
    val tipo: Tipo
    ) : Serializable {

    enum class Tipo(val value: String) {
        SUPER("super"),
        ORGANIZADOR("organidator"),
        BASICO("basico");

        companion object {
            fun fromValue(value: String): Tipo {
                return when (value) {
                    "super" -> SUPER
                    "organidator" -> ORGANIZADOR
                    "basico" -> BASICO
                    else -> throw IllegalArgumentException("Tipo de usuario desconocido: $value")
                }
            }
        }
    }
}
