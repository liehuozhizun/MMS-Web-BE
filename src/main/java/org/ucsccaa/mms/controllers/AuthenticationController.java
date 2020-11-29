package org.ucsccaa.mms.controllers;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.ucsccaa.mms.domains.UserDetails;
import org.ucsccaa.mms.models.ServiceResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

public interface AuthenticationController {
    public ServiceResponse<?> createJwt(@RequestBody UserDetails userDetails);
    public ServiceResponse<UserDetails> register(HttpServletRequest req);
}
