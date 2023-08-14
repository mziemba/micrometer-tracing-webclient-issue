package demo

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class DemoController(
    private val demoClient: DemoClient
) {

    @GetMapping("/demo")
    fun demo(): Mono<HttpBinResponse> {
        logger.info { "Starting to process request in controller" }
        val response = demoClient.executeHttpCall()
            .doOnEach { logger.info { "This log is missing baggage information!!!" } }
        logger.info { "Finishing to process request in controller" }
        return response
    }

    private companion object : KLogging()
}
