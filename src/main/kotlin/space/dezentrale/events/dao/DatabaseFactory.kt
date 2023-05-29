package space.dezentrale.events.dao

import io.ktor.server.config.*

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("storage.driverClassName").getString()
        val jdbcURL = config.property("storage.jdbcURL").getString()
    //    val database = Database.connect(jdbcURL, driverClassName)
    }
}