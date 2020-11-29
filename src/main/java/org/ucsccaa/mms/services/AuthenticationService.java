package org.ucsccaa.mms.services;

import org.springframework.stereotype.Service;
import org.ucsccaa.mms.domains.UserDetails;

import java.util.Date;

@Service
public interface AuthenticationService {
    UserDetails register();
    String getUserNameFromToken(String token);
    UserDetails loadUserByUsername(String name);
    String generateJwtToken(UserDetails userDetails);
    Date getIssuedDateFromToken(String token);
    Boolean validateJwtToken(String token);
    void authenticate(UserDetails userDetails);
}
