package com.alex.security.config;
// CLASSE N°5

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service                        // Annotazione che serve a spring per fargli capire che questa è una classe che fornisce dei "servizi"
public class JwtService {

    // Costante che contiene una stringa utilizzata come chiave segreta per la generazione e la verifica dei token JWT
    private static final String SECRET_KEY = "50645367566B5970337336763979244226452948404D6351665468576D5A7134";

    // Metodo che riceve un token come input e restituisce l'username estratto dal token.
    // Utilizza il metodo extractClaim per estrarre il claim "subject" dal token.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Metodo che riceve un token e una funzione claimsResolver come input.
    // Estrae tutti i claims dal token utilizzando il metodo extractAllClaims
    // e applica la funzione claimsResolver ai claims estratti per ottenere il risultato desiderato.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Metodo che genera un nuovo token JWT utilizzando un oggetto UserDetails come input.
    // Può essere utilizzato con o senza un parametro extraClaims, che rappresenta claim personalizzati da aggiungere al token.
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Metodo che verifica se un token è valido confrontando l'username estratto dal token
    // con quello fornito da un oggetto UserDetails.
    // Controlla anche se il token è scaduto utilizzando il metodo isTokenExpired.
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Metodo che controlla se un token è scaduto confrontando la data di scadenza estratta dal token con la data corrente.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Metodo che estrae la data di scadenza (claim "expiration") dal token utilizzando il metodo extractClaim.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Metodo che riceve un token e utilizza la libreria io.jsonwebtoken per analizzare il token
    // e ottenere i claims (informazioni) contenuti al suo interno.
    // Restituisce l'oggetto Claims che rappresenta tutti i claims estratti.
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    // Metodo che converte la chiave segreta dalla rappresentazione codificata in base64 a un oggetto Key
    // utilizzabile per la verifica del token JWT.
    // La chiave segreta viene decodificata dalla stringa SECRET_KEY utilizzando il metodo Decoders.BASE64.decode()
    // e quindi viene utilizzata per creare un oggetto Key mediante il metodo Keys.hmacShaKeyFor()
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
