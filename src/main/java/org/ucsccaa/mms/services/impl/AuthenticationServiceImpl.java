package org.ucsccaa.mms.services.impl;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.ucsccaa.mms.domains.UserDetails;
import org.ucsccaa.mms.repositories.UserDetailsRepository;
import org.ucsccaa.mms.services.AuthenticationService;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Value("${jwt.secret")
    private String secretKey = "secret";

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Override
    public UserDetails register() {
        UserDetails userDetails = new UserDetails();
        String password = userDetails.getPassword();
        userDetails.setPassword(encoder.encode(userDetails.getPassword()));
        userDetailsRepository.save(userDetails);
        userDetails.setPassword(password);
        return userDetails;
    }

    @Override
    public String generateJwtToken(UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("argument cannot be null");
        }
        String authorities = userDetails.getStaff().getAuthorization().getLevel().toString();
        return Jwts.builder()
                .setSubject(userDetails.getUserName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1h
                .claim("authority", authorities)
                .signWith(SignatureAlgorithm.HS256, secretKey)
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
    public String getAuthorityFromToken(String token) {
        if (token == null) {
            throw new RuntimeException("argument cannot be null");
        }
        String userName;
        try {
            userName = getUserNameFromToken(token);
        } catch (Exception e) {
            throw e;
        }
        UserDetails userDetails;
        try {
            userDetails = loadUserByUsername(userName);
        } catch (Exception e) {
            throw e;
        }
        String authority = userDetails.getStaff().getAuthorization().getAuthorityList().toString();
        return authority;
    }

    @Override
    public String getLevelFromToken(String token) {
        if (token == null) {
            throw new RuntimeException("argument cannot be null");
        }
        String userName;
        try {
            userName = getUserNameFromToken(token);
        } catch (Exception e) {
            throw e;
        }
        UserDetails userDetails;
        try {
            userDetails = loadUserByUsername(userName);
        } catch (Exception e) {
            throw e;
        }
        String level = userDetails.getStaff().getAuthorization().getLevel().toString();
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
        UserDetails userDetails = userDetailsRepository.findByUserName(name);
        if (userDetails == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return userDetails;
    }

    @Override
    public Boolean validateJwtToken(String token) {
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
    public void authenticate(UserDetails userDetails) {
        if(userDetails == null) {
            throw new RuntimeException("argument cannot be null");
        }
        UserDetails userDetails1 = userDetailsRepository.findByUserName(userDetails.getUserName());
        if (userDetails1 == null) {
            throw new RuntimeException("user not found");
        } else if (!encoder.matches(userDetails.getPassword(), userDetails1.getPassword())) {
            throw new RuntimeException("wrong password");
        }
    }
}
