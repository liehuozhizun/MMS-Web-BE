package org.ucsccaa.mms.services;

import org.springframework.stereotype.Service;
import org.ucsccaa.mms.domains.UserDetails;

import java.util.Date;

public interface AuthenticationService {
    String getUserNameFromToken(String token);
    String getLevelFromToken(String token);
    UserDetails loadUserByUsername(String name);
    String generateToken(UserDetails userDetails);
    Date getIssuedDateFromToken(String token);
    Boolean validateToken(String token);
    UserDetails authenticate(String username, String password);
}
