package data

class ServerError(val mes: String): Throwable() {
    override val message: String?
        get() = mes
}