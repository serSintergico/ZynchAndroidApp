data class DataResponse (
    val ok: String,
    val usuario: String,
    val motos: List<motosList>
)

data class motosList (
    val idMoto: String,
    val moto: String,
    val bateria1: String,
    val bateria2: String
)