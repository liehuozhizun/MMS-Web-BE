package org.ucsccaa.mms.controllers.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ucsccaa.mms.controllers.AuthenticationController;
import org.ucsccaa.mms.domains.UserDetails;
import org.ucsccaa.mms.models.ServiceResponse;
import org.ucsccaa.mms.models.Status;
import org.ucsccaa.mms.repositories.UserDetailsRepository;
import org.ucsccaa.mms.services.impl.AuthenticationServiceImpl;

import javax.servlet.http.HttpServletRequest;

import java.net.URI;

@Api(tags = "Authentication RESTFUL API")
@RestController

public class AuthenticationControllerImpl implements AuthenticationController {

    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @ApiOperation("create new jwt")
    @Override

    @PostMapping("/authenticate")
    public ServiceResponse<?> createJwt(@PathVariable String username, @PathVariable String password) {
        UserDetails userDetails;
        try {
            userDetails = authenticationServiceImpl.authenticate(username, password);
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        String token;
        try {
           token = authenticationServiceImpl.generateToken(userDetails);
        } catch (Exception e) {
            return  new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        return new ServiceResponse<>(token);
    }

    @PostMapping("/createUserDetail")
    public ServiceResponse<URI> createUserDetail(@RequestBody UserDetails userDetails, HttpServletRequest req) throws URISyntaxException {
        Long id;
        try {
            id = authenticationServiceImpl.addUserDetail(userDetails);
            return new ServiceResponse<>(new URI(req.getRequestURL() + "/" + id));
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
}
