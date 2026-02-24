package com.bank.middleware.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    // Erzeuge einen sicheren SecretKey für HS256 (mindestens 256 Bit)
    private static final String SECRET = "12345678901234567890123456789012"; // 32 Zeichen!
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // Token-Gültigkeit: 1 Stunde (in Millisekunden)
    private static final long EXPIRATION_TIME_MS = 60 * 60 * 1000;

    /**
     * Erzeugt ein JWT für den angegebenen Benutzernamen und die Rolle.
     * 
     * @param username Der Benutzername (wird als "subject" gespeichert)
     * @param role     Die Benutzerrolle (wird als Claim gespeichert)
     * @return Das signierte JWT als String
     */
    public static String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username) // Setze den subject-Claim (Benutzername)
                .claim("role", role) // Füge einen eigenen Claim "role" hinzu
                .setIssuedAt(new Date()) // Setze das Ausstellungsdatum auf jetzt
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS)) // Ablaufzeit
                .signWith(SECRET_KEY) // Signiere das Token mit dem SecretKey
                .compact(); // Baue das Token als kompakten String
    }

    /**
     * Prüft ein JWT und gibt die enthaltenen Claims zurück.
     * 
     * @param token Das zu prüfende JWT
     * @return Die Claims (z.B. subject, role, issuedAt, expiration)
     * @throws JwtException Falls das Token ungültig oder abgelaufen ist
     */
    public static Claims validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Setze den Schlüssel zum Prüfen der Signatur
                .build()
                .parseClaimsJws(token) // Parsen und prüfen
                .getBody(); // Claims extrahieren
    }

    /**
     * Extrahiert die Rolle aus einem JWT.
     * 
     * @param token Das JWT
     * @return Die Rolle als String
     */
    public static String getRoleFromToken(String token) {
        Claims claims = validateToken(token); // Token prüfen und Claims holen
        return claims.get("role", String.class); // Rolle auslesen
    }

    /**
     * Extrahiert den Benutzernamen (subject) aus einem JWT.
     * 
     * @param token Das JWT
     * @return Der Benutzername
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = validateToken(token); // Token prüfen und Claims holen
        return claims.getSubject(); // Subject (Benutzername) auslesen
    }
}