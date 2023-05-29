package com.alex.security.config;
// CLASSE N°4

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Classe per implementare il filtro per l'autenticazione jwt
@Component                              // Annotazione per dire a spring di gestire questa classe come un bean nel framework, vedi esempio
@RequiredArgsConstructor                // Genera automaticamente un costruttore che accetta solo i campi finali o marcati come @NonNull, vedi esempio
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // variabile che ci permette di usare i metodi di servizio di JwtService
    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,                 // La nostra richiesta dal server, cosi possiamo utilizzarne i dati
            @NonNull HttpServletResponse response,               // La nostra risposta dal server, cosi possiamo aggiungere nuovi dati
            @NonNull FilterChain filterChain                     // Consente di passare il controllo al filtro successivo nella catena e di proseguire nell'elaborazione della richiesta
    ) throws ServletException, IOException {

        // Stringa contenente il jwt token dalla nostra richiesta, l'header si chiama di default Authorization
        // ed è scritto in formato "Bearer xxxxxxxxx"
        final String authHeader = request.getHeader("Authorization");

        // Stringa in cui caricheremo solo il token
        final String jwt;

        // Stringa in cui caricheremo l'username dal token
        final String userEmail;

        // Controllo per verificare che authHeader non sia vuoto è che abbia il formato corretto
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Carichiamo solo la parte del token da authHeader
        jwt = authHeader.substring(7);

        // Carichiamo l'username nella nostra stringa
        userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
