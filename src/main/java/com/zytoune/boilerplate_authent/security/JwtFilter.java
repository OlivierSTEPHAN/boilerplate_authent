package com.zytoune.boilerplate_authent.security;

import com.zytoune.boilerplate_authent.entity.Jwt;
import com.zytoune.boilerplate_authent.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@AllArgsConstructor
@Service
public class JwtFilter extends OncePerRequestFilter {

    private HandlerExceptionResolver handlerExceptionResolver;
    private UserService userService;
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String token = null;
        Jwt tokenInDb = null;
        String username = null;
        boolean isTokenExpired = true;

        try {
            final String authorization = request.getHeader("Authorization");
            if(authorization != null && authorization.startsWith("Bearer ")){
                token = authorization.substring(7);
                tokenInDb = this.jwtService.tokenByValue(token);
                isTokenExpired = this.jwtService.isTokenExpired(token);
                username = this.jwtService.extractUsername(token);
            }

            if(!isTokenExpired
                    && tokenInDb.getUser().getUsername().equals(username)
                    && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            this.handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
