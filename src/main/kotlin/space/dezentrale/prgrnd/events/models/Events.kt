package space.dezentrale.prgrnd.events.models

import org.jetbrains.exposed.sql.*

object Events: Table() {
    val id = integer("id").autoIncrement()
//    val startTime = datetime("start_time")
}