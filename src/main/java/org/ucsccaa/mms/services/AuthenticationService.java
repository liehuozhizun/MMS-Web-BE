package org.ucsccaa.mms.services;

import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Service;
import org.ucsccaa.mms.domains.UserDetails;

import java.util.Date;

@Service
public interface AuthenticationService {
    public UserDetails register();
    public String getUserNameFromToken(String token);
    public UserDetails loadUserByUsername(String name);
    public String generateJwtToken(UserDetails userDetails);
    public Date getIssuedDateFromToken(String token);
    public Boolean validateJwtToken(String token);
    public void authenticate(UserDetails userDetails);
}
