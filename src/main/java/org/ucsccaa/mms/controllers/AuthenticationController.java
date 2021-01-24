package org.ucsccaa.mms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.ucsccaa.mms.domains.UserDetails;
import org.ucsccaa.mms.models.ServiceResponse;
import org.ucsccaa.mms.models.Status;
import org.ucsccaa.mms.services.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("authenticate")
    public ServiceResponse<String> authenticate(@RequestBody Map<String,Object>param){
        UserDetails newUser = new UserDetails();
        newUser.setPassword((String) param.get("password"));
        newUser.setUserName((String) param.get("username"));
        try {
            String token = authenticationService.generateToken(newUser);
            return new ServiceResponse<>(token);
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, "Bad Request");
        }
    }
    @GetMapping("login")
    public ServiceResponse<UserDetails> getUserInfo(HttpServletRequest request){
        String token = request.getHeader("authorization");
        try {
            Boolean result = authenticationService.validateToken(token);

            if (result){
                UserDetails userDetails = authenticationService.loadUserByUsername(authenticationService.getUserNameFromToken(token));
                return new ServiceResponse<>(userDetails);
            }
            else {
                return new ServiceResponse<>(Status.ERROR,"Unauthorized");
            }
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR,"Bad Request");
        }
    }
}