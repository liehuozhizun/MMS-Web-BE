package org.ucsccaa.mms.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.ucsccaa.mms.domains.UserDetails;
import org.ucsccaa.mms.models.ServiceResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

public interface AuthenticationController {
    ServiceResponse<?> createJwt(@RequestBody UserDetails userDetails);
    ServiceResponse<UserDetails> createUserDetail(@RequestBody UserDetails userDetails, HttpServletRequest req) throws URISyntaxException;
}
