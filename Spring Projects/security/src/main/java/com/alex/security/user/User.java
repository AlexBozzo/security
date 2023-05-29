package com.alex.security.user;
// CLASSE N°1

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Classe per definire l'utente
@Data                   // Genera automaticamente getter e setter e altri metodi
@Builder                // Semplifica la creazione di istanze di questa classe, vedi esempio
@NoArgsConstructor      // Genere automaticamente un costruttore senza argomenti, vedi esempio
@AllArgsConstructor     // Genera automaticamente un costruttire con tutti gli argomenti della classi, vedi esempio
@Entity                 // Viene utilizzata per definire una classe che mappa una tabella del database, vedi esempio
@Table(name = "_user")  // Specifica il nome della tabella nel database (in questo caso viene usato per rimuovere ambiguità), vedi esempio
public class User implements UserDetails {

    // Il numero identificativo dell'utente (ad esempio 1,2.. ecc, oppure il codice fiscale)
    @Id                 // Annotazione che serve ad entity per fargli capire che questo parametro è l'id della classe
    @GeneratedValue     // Genera automaticamente il valore dell'id (con postgres partiamo da 1, con incremente 50)
    private Integer id;

    // Il nome dell'utente
    private String firstname;

    // Il cognome dell'utente
    private String lastname;

    // La email dell'utente
    private String email;

    // La password dell'utente
    private String password;

    // Il ruolo dell'utente(in questo caso usero o admin)
    @Enumerated(EnumType.STRING)                // Annotazione che serve per far capire a spring che è un enum
    private Role role;

    // Ritorna il ruolo di questa classe
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // Ritorna la password di questa classe
    @Override
    public String getPassword() {
        return password;
    }

    // Ritorna l'username di questa classe
    @Override
    public String getUsername() {
        return email;
    }

    // Ritorna un boolean che dichiara se l'utente non è scaduto
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Ritorna un boolean che dichiara se l'utente non è bloccato
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Ritorna un boolean che dichiara se le credenziali non sono scadute
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Ritorna un boolean che dichiara se l'utente è abilitato
    @Override
    public boolean isEnabled() {
        return true;
    }
}
