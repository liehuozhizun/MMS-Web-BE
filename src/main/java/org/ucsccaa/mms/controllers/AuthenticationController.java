package org.ucsccaa.mms.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.ucsccaa.mms.domains.UserDetails;
import org.ucsccaa.mms.models.ServiceResponse;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationController {
    ServiceResponse<?> createJwt(@RequestBody UserDetails userDetails);
    ServiceResponse<UserDetails> register(HttpServletRequest req);
}
