package org.ucsccaa.mms.services.impl;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.ucsccaa.mms.domains.UserDetails;
import org.ucsccaa.mms.repositories.UserDetailsRepository;
import org.ucsccaa.mms.services.AuthenticationService;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.security.SecureRandom;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Value("${secretKey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    
    @Override
    public String generateToken(UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("argument cannot be null");
        }
        String level = userDetails.getStaff().getAuthorization().getLevel().toString();
        return Jwts.builder()
                .setSubject(userDetails.getUserName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1h
                .claim("authorizationLevel", level)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    @Override
    public String getUserNameFromToken(String token) {
        if (token == null) {
            throw new RuntimeException("argument cannot be null");
        }
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public String getLevelFromToken(String token) {
        if (token == null) {
            return null;
        }
        String level = (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("authorizationLevel");
        return level;
    }

    @Override
    public Date getIssuedDateFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getIssuedAt();
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        if (name == null) {
            throw new RuntimeException("argument cannot be null");
        }
        Optional<UserDetails> userDetails = userDetailsRepository.findByUserName(name);
        if (!userDetails.isPresent()) {
            throw new RuntimeException("user not found");
        }
        return userDetails.get();
    }

    @Override
    public Boolean validateToken(String token) {
        String userName;
        try {
            userName = getUserNameFromToken(token);
        } catch (Exception e) {
            throw e;
        }

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT string is empty: " + e.getMessage());
        }
        return (userDetailsRepository.findByUserName(userName) != null);
    }

    @Override
    public UserDetails authenticate(String username, String password) {
        if(username == null || password == null) {
            throw new RuntimeException("argument cannot be null");
        }
        Optional<UserDetails> userDetails = userDetailsRepository.findByUserName(username);
        if(!userDetails.isPresent()){
            throw new RuntimeException("user not found");
        }
        byte[] salt = userDetails.get().getSalt();
        String loginPassword = encrypt(password, salt);
        if(!loginPassword.equals(userDetails.get().getPassword()) ) {
            throw new RuntimeException("wrong password");
        }
        return userDetails.get();
    }

    public Long addUserDetail(UserDetails userDetails) {
        if(userDetails == null) {
            throw new RuntimeException("argument cannot be null");
        }
        if(userDetailsRepository.exists(Example.of(userDetails))) {
            throw new RuntimeException("user already exists");
        }
        byte[] salt;
        salt = getSalt();
        String encryptedPassword;
        try {
            encryptedPassword = encrypt(userDetails.getPassword(), salt);
        } catch (Exception e) {
            throw new RuntimeException("fail to encrypt password");
        }
        userDetails.setPassword(encryptedPassword);
        userDetails.setSalt(salt);
        return userDetailsRepository.save(userDetails).getId();
    }

    public String encrypt(String password, byte[] salt) {
        if (password == null || salt == null) {
            throw new RuntimeException("argument cannot be null");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] messageDigest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                sb.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String hashText = sb.toString();
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }
}
