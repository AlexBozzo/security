package com.alex.security.config;
// CLASSE N°6

import com.alex.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Questa classe ApplicationConfig fornisce una configurazione per l'applicazione che definisce il UserDetailsService.
// Il UserDetailsService viene utilizzato da Spring Security per recuperare i dettagli dell'utente durante l'autenticazione.
// Il metodo userDetailsService implementa questa funzionalità cercando un utente nel UserRepository
// utilizzando l'indirizzo email come criterio di ricerca.
// Se l'utente non viene trovato, viene lanciata un'eccezione UsernameNotFoundException.
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    // Variabile che rappresenta una repository per l'accesso e la manipolazione dei dati degli utenti.
    private final UserRepository repository;

    // Metodo di configurazione che restituisce un'istanza di UserDetailsService, che è un'interfaccia di
    // Spring Security utilizzata per recuperare i dettagli dell'utente durante l'autenticazione.
    // Questo metodo viene utilizzato per cercare un utente nel repository utilizzando l'indirizzo email come criterio di ricerca.
    // Se l'utente non viene trovato, viene lanciata un'eccezione UsernameNotFoundException.
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
