package com.scheduly.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Filtro para adicionar informações de contexto nos logs (MDC - Mapped
 * Diagnostic Context)
 * Adiciona requestId, userId, path, method para rastreabilidade
 */
@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID = "requestId";
    private static final String USER_ID = "userId";
    private static final String REQUEST_PATH = "requestPath";
    private static final String REQUEST_METHOD = "requestMethod";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            // Gerar ou recuperar requestId
            String requestId = request.getHeader("X-Request-ID");
            if (requestId == null || requestId.isEmpty()) {
                requestId = UUID.randomUUID().toString();
            }

            // Adicionar informações ao MDC
            MDC.put(REQUEST_ID, requestId);
            MDC.put(REQUEST_PATH, request.getRequestURI());
            MDC.put(REQUEST_METHOD, request.getMethod());

            // Adicionar userId se autenticado (será implementado com JWT)
            // String userId = extractUserIdFromToken(request);
            // if (userId != null) {
            // MDC.put(USER_ID, userId);
            // }

            // Adicionar requestId no response header
            response.setHeader("X-Request-ID", requestId);

            // Log da requisição
            long startTime = System.currentTimeMillis();
            log.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());

            // Continuar a cadeia de filtros
            filterChain.doFilter(request, response);

            // Log da resposta
            long duration = System.currentTimeMillis() - startTime;
            log.info("Request completed: {} {} - Status: {} - Duration: {}ms",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    duration);

        } finally {
            // Limpar MDC para evitar memory leak
            MDC.clear();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Não filtrar recursos estáticos e health checks
        String path = request.getRequestURI();
        return path.startsWith("/actuator/") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs/") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".png") ||
                path.endsWith(".ico");
    }
}
