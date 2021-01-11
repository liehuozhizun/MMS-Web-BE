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
import java.net.URISyntaxException;

@Api(tags = "Authentication RESTFUL API")
@RestController
@RequestMapping("/authentications")
public class AuthenticationControllerImpl implements AuthenticationController {
    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @ApiOperation("create new jwt")
    @Override
    @PostMapping
    public ServiceResponse<?> createJwt(@RequestBody UserDetails userDetails) {
        try {
            authenticationServiceImpl.authenticate(userDetails);
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        String token;
        try {
           token = authenticationServiceImpl.generateJwtToken(userDetails);
        } catch (Exception e) {
            return  new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        return new ServiceResponse<>(token);
    }
    @Override
    @PostMapping("/createUserDetail")
    public ServiceResponse<UserDetails> createUserDetail(@RequestBody UserDetails userDetails, HttpServletRequest req) throws URISyntaxException {
        System.out.println(userDetails);
        userDetailsRepository.save(userDetails);
        return new ServiceResponse<>(userDetails);
    }
}
