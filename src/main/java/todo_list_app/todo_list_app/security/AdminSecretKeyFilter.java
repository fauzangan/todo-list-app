package todo_list_app.todo_list_app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AdminSecretKeyFilter extends OncePerRequestFilter {
    private static final String ADMIN_EXPECTED_SECRET_KEY = "admin turing machine alpha beta";
    private static final String SUPER_ADMIN_EXPECTED_SECRET_KEY = "superman admin turing machine gigachad sigma";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String superAdminKey = request.getHeader("X-Super-Admin-Secret-Key");
        String adminKey = request.getHeader("X-Admin-Secret-Key");

        // Cek untuk endpoint yang memerlukan Super Admin Secret Key
        if (path.startsWith("/api/admin/super-admin")) {
            if (superAdminKey == null || !superAdminKey.equals(SUPER_ADMIN_EXPECTED_SECRET_KEY)) {
                response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid Super Admin Secret Key");
                return;
            }
        }
        // Cek untuk endpoint yang memerlukan Admin Secret Key
        else if (path.startsWith("/api/admin/users/") && path.contains("role")) {
            if (adminKey == null || !adminKey.equals(ADMIN_EXPECTED_SECRET_KEY)) {
                response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid Admin Secret Key");
                return;
            }

            // Cek otorisasi untuk memastikan pengguna memiliki peran SUPER_ADMIN
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_SUPER_ADMIN"))) {
                response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied");
                return;
            }
        }

        // Lanjutkan filter chain
        filterChain.doFilter(request, response);
    }
}
