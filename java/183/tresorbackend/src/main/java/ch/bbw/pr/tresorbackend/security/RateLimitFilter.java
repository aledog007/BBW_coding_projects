package ch.bbw.pr.tresorbackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RateLimitFilter - Begrenzt die Anzahl Login-Versuche pro IP.
 * Wow-Effekt: Schutz vor Brute-Force-Angriffen.
 * Max 10 Versuche pro IP in 15 Minuten.
 */
@Component
@Order(1)
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int MAX_ATTEMPTS = 10;
    private static final long WINDOW_MS = 15 * 60 * 1000; // 15 Minuten

    // IP -> {count, windowStart}
    private final Map<String, long[]> attemptMap = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Nur Login-Endpoint rate-limiten
        if (path.equals("/api/auth/login") && "POST".equalsIgnoreCase(request.getMethod())) {
            String ip = getClientIp(request);
            long now = System.currentTimeMillis();

            long[] data = attemptMap.computeIfAbsent(ip, k -> new long[]{0, now});

            // Fenster zurücksetzen falls abgelaufen
            if (now - data[1] > WINDOW_MS) {
                data[0] = 0;
                data[1] = now;
            }

            data[0]++;

            if (data[0] > MAX_ATTEMPTS) {
                response.setStatus(429);
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"Zu viele Login-Versuche. Bitte warten Sie 15 Minuten.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
