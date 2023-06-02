package demo

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class DemoClient(webClientBuilder: WebClient.Builder) {

    private val webClient = webClientBuilder
        .baseUrl("https://httpbin.org")
        .build()

    fun executeHttpCall(): Mono<HttpBinResponse> {
        val path = "/get"
        return webClient.get()
            .uri(path)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(HttpBinResponse::class.java)
    }
}

data class HttpBinResponse(
    val origin: String,
)
