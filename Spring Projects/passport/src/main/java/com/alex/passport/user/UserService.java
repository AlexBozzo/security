package com.alex.passport.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("Utente non trovato con email: " + email);
    }
    return user;
  }

  public void register(User user) {
    // Effettua la logica di validazione dell'utente

    // Verifica se l'utente esiste già nel database
    if (userRepository.findByEmail(user.getUsername()) != null) {
      throw new IllegalArgumentException("Email già utilizzata");
    }

    // Esempio di validazione della password
    if (user.getPassword().length() < 8) {
      throw new IllegalArgumentException("La password deve contenere almeno 8 caratteri");
    }

    // Esempio di validazione di altri campi dell'utente
    if (user.getFirstname() == null || user.getFirstname().isEmpty()) {
      throw new IllegalArgumentException("Il nome dell'utente è obbligatorio");
    }

    if (user.getLastname() == null || user.getLastname().isEmpty()) {
      throw new IllegalArgumentException("Il cognome dell'utente è obbligatorio");
    }

    // Cripta la password prima di salvarla nel database
    String criptedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(criptedPassword);

    // Salva l'utente nel database
    userRepository.save(user);
  }
}