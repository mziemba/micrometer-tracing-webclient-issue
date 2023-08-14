package demo

import io.micrometer.tracing.Tracer
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class SetCustomBaggageTracingFilter(private val tracer: Tracer) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        val clientId = "demo-client"
        tracer.createBaggageInScope(CLIENT_ID_BAGGAGE_NAME, clientId)
        val sessionId = UUID.randomUUID().toString()
        tracer.createBaggageInScope(SESSION_ID_BAGGAGE_NAME, sessionId)
        MDC.put(CLIENT_ID_BAGGAGE_NAME, clientId)
        MDC.put(SESSION_ID_BAGGAGE_NAME, sessionId)
        filterChain.doFilter(request, response)
    }

    private companion object {
        const val CLIENT_ID_BAGGAGE_NAME = "client_id"
        const val SESSION_ID_BAGGAGE_NAME = "session_id"
    }
}
