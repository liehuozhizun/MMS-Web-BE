package org.ucsccaa.mms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ucsccaa.mms.repositories.AuthorizeRepository;

@Service
public interface AuthorService {
    Boolean checkAuthority(String level, String... authority);
}
