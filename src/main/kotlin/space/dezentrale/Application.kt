package space.dezentrale

import io.ktor.server.application.*
import io.ktor.server.netty.*
import space.dezentrale.plugins.configureRouting
import space.dezentrale.plugins.configureSerialization
import space.dezentrale.plugins.connectToPostgres

fun main(args: Array<String>): Unit =
    EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureRouting(connectToPostgres())
}
