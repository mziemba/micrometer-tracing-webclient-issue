package demo

import brave.baggage.BaggageField
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ClientIdTracingFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        val clientId = "demo-client"
        val baggageField = BaggageField.create(CLIENT_ID_BAGGAGE_NAME)
        baggageField.updateValue(clientId)
        MDC.put(CLIENT_ID_BAGGAGE_NAME, clientId)
        filterChain.doFilter(request, response)
    }

    private companion object {
        const val CLIENT_ID_BAGGAGE_NAME = "client_id"
    }
}
