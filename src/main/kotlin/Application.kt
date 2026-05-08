import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import model.LogEvento
import service.LogService
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

fun main() {

    println("🚀 Iniciando servidor...")

    embeddedServer(Netty, port = 8080) {

        install(ContentNegotiation) {
            jackson()
        }

        routing {

            // =========================
            // 📦 ARCHIVOS ESTÁTICOS
            // =========================
            staticFiles("/", File("src/main/resources/public")) {
                default("index.html")
            }

            // =========================
            // 📩 REGISTRAR EVENTO
            // =========================
            post("/log") {
                try {
                    val log = call.receive<LogEvento>()
                    LogService.registrarEvento(log)

                    call.respond(
                        HttpStatusCode.OK,
                        mapOf("message" to "Evento registrado")
                    )

                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "Formato de log inválido")
                    )
                }
            }

            // =========================
            // 📊 ESTADÍSTICAS USUARIO
            // =========================
            get("/stats/{usuario}") {
                val usuario = call.parameters["usuario"]

                if (usuario == null) {
                    call.respond(HttpStatusCode.BadRequest, "Usuario no válido")
                    return@get
                }

                call.respond(LogService.obtenerEstadisticasUsuario(usuario))
            }

            // =========================
            // 🎯 MEJOR POR ESTÍMULO
            // =========================
            get("/stats/estimulos/{usuario}") {
                val usuario = call.parameters["usuario"]

                if (usuario == null) {
                    call.respond(HttpStatusCode.BadRequest, "Usuario no válido")
                    return@get
                }

                call.respond(LogService.mejorPorEstimulo(usuario))
            }

            // =========================
            // 🏆 RANKING GLOBAL TOP 3
            // =========================
            get("/ranking") {
                call.respond(LogService.rankingTop3())
            }

            // =========================
            // 📄 GENERAR INFORME (XML + XSLT)
            // =========================
            get("/generar-informe") {

                LogService.generarInformeHtml()

                val file = File("informe_resultados.html")

                if (file.exists()) {
                    call.respondFile(file)
                } else {
                    call.respondText(
                        "Error generando informe",
                        status = HttpStatusCode.InternalServerError
                    )
                }
            }

            // =========================
            // 📄 VER XML (DEBUG)
            // =========================
            get("/ver-xml") {

                val path = Paths.get("src/main/resources/data/eventos.xml")

                if (Files.exists(path)) {
                    call.respondText(
                        Files.readString(path),
                        ContentType.Text.Xml
                    )
                } else {
                    call.respond(HttpStatusCode.NotFound, "XML no encontrado")
                }
            }

            // =========================
            // ❤️ HEALTH CHECK
            // =========================
            get("/health") {
                call.respond(mapOf("status" to "ok"))
            }
        }

    }.start(wait = true)
}