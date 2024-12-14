package fr.makizart.restserver;

import fr.makizart.common.database.table.Utilisateur;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that checks if the user is enabled.
 * If the user is not enabled, it will return a 403 Forbidden status code.
 */
@Component
public class UserEnabledFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (!requestURI.contains("/api/auth/") && !(auth.getPrincipal() instanceof String)){
            Utilisateur authenticatedUser = (Utilisateur) auth.getPrincipal();

            if (!authenticatedUser.isEnabled()) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "User is not enabled");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}

