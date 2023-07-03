package space.dezentrale.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import space.dezentrale.events.exceptions.DbElementInsertException
import space.dezentrale.events.exceptions.DbElementNotFoundException
import space.dezentrale.events.models.Article
import space.dezentrale.events.service.ArticleService
import java.sql.Connection

fun Application.configureRouting(dbConnection: Connection) {
    val articleService = ArticleService(dbConnection)
    routing {
        // Create new Article
        post("/articles") {
            val article = call.receive<Article>()
            try {
                val id = articleService.create(article)
                call.respond(HttpStatusCode.Created, id)
            } catch (cause: DbElementInsertException) {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
        // Read an Article
        get("/articles/{id}") {
            try {
                val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid article ID")
                val article = articleService.read(id)
                call.respond(HttpStatusCode.OK, article)
            } catch (cause: DbElementNotFoundException) {
                call.respond(HttpStatusCode.NotFound)
            } catch (cause: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        // Update an Article
        put("/articles/{id}") {
            try {
                val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid article ID")
                val article = call.receive<Article>()
                articleService.update(id, article)
                call.respond(HttpStatusCode.OK)
            } catch (cause: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        // Delete an Article
        delete("/articles/{id}") {
            try {
                val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid article ID")
                articleService.delete(id)
                call.respond(HttpStatusCode.OK)
            } catch (cause: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
