package org.ucsccaa.mms.services.Implements;

import org.springframework.stereotype.Service;
import org.ucsccaa.mms.services.AuthorService;
@Service
public class AuthorServiceImp implements AuthorService {

    @Override
    public Boolean checkAuthority(String level, String... authority) {
        return false;
    }
}
