package com.hemanth.redditclone.security;

import com.hemanth.redditclone.exceptions.ApiRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {
    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/redditclone.jks");
            keyStore.load(resourceAsStream, "Pega@519".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new ApiRequestException("Exception occurred while loading keystore");
        }
    }

    public String generateToken(Authentication authenticate) {
        UserDetails principal = (UserDetails) authenticate.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private Key getPrivateKey() {
        try {
            return keyStore.getKey("redditclone", "Pega@519".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new ApiRequestException("Exception occured while retrieving public key from keystore");
        }
    }

    public boolean validateToken(String jwt) {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private Key getPublicKey() {
        try {
            return keyStore.getCertificate("redditclone").getPublicKey();
        } catch (KeyStoreException e) {
            throw new ApiRequestException("Exception occured while retrieving public key from keystore");
        }
    }

    public String getUsernameFromToken(String jwt) {
        //parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
        Claims claims = parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(jwt)
                .getBody();
        return claims.getSubject();
    }
}
