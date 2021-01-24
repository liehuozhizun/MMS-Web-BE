package org.ucsccaa.mms.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.ucsccaa.mms.domains.UserDetails;
import org.ucsccaa.mms.models.ServiceResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

public interface AuthenticationController {
    ServiceResponse<?> createJwt(@PathVariable String username, @PathVariable String password);
}
