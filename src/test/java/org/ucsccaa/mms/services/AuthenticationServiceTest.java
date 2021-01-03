package org.ucsccaa.mms.services;

import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.domains.UserDetails;
import org.ucsccaa.mms.domains.Authorization;
import org.ucsccaa.mms.repositories.UserDetailsRepository;
import org.ucsccaa.mms.services.impl.AuthenticationServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private ArrayList<Authorization.Authorities> authorList = new ArrayList<Authorization.Authorities>();
    private final Authorization authorization = new Authorization(1L, Authorization.LEVEL.LEVEL_1, authorList);
    private final Member member = new Member(1L,"test","test","test","test",
            "test","test","test","test","test","test","test",
            "test","test","test","test","test","test","test","test",
            "test","test","test",true);
    private final Staff staff = new Staff(1L, "test", "test", authorization, member);
    private final UserDetails expectedUser = new UserDetails(1L, "test", "test", staff);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void testRegister() {
        UserDetails userDetails = authenticationService.register();
        Assert.assertNotNull(userDetails);
    }

    @Test
    public void testGenerateJwtToken() {
        String token = authenticationService.generateJwtToken(expectedUser);
        Assert.assertNotNull(token);
    }

    @Test(expected = RuntimeException.class)
    public void testGenerateJwtToken_exception() {
        authenticationService.generateJwtToken(null);
    }

    @Test
    public void testGetUserNameFromToken() {
        String token = authenticationService.generateJwtToken(expectedUser);
        String username = authenticationService.getUserNameFromToken(token);
        Assert.assertEquals(expectedUser.getUserName(), username);
    }

    @Test(expected = RuntimeException.class)
    public void testGetUserNameFromToken_exception() {
        authenticationService.getUserNameFromToken(null);
    }

    @Test
    public void testGetAuthorityFromToken() {
        when(userDetailsRepository.findByUserName("test")).thenReturn(expectedUser);
        String token = authenticationService.generateJwtToken(expectedUser);
        String authority = authenticationService.getAuthorityFromToken(token);
        Assert.assertEquals(authorList.toString(), authority);
    }

    @Test(expected = RuntimeException.class)
    public void testGetAuthorityFromToken_exception() {
        authenticationService.getAuthorityFromToken(null);
    }

    @Test
    public void testGetLevelFromToken() {
        when(userDetailsRepository.findByUserName("test")).thenReturn(expectedUser);
        String token = authenticationService.generateJwtToken(expectedUser);
        String level = authenticationService.getLevelFromToken(token);
        Assert.assertEquals(Authorization.LEVEL.LEVEL_1.toString(), level);
    }

    @Test(expected = RuntimeException.class)
    public void testGetLevelFromToken_exception() {
        authenticationService.getLevelFromToken(null);
    }

    @Test
    public void testGetIssuedDateFromToken() {
        String token = authenticationService.generateJwtToken(expectedUser);
        Date date = authenticationService.getIssuedDateFromToken(token);
        Assert.assertNotNull(date);
    }

    @Test(expected = RuntimeException.class)
    public void testGetIssuedDateFromToken_exception() {
        authenticationService.getIssuedDateFromToken(null);
    }

    @Test
    public void testLoadUserByUsername() {
        when(userDetailsRepository.findByUserName("test")).thenReturn(expectedUser);
        UserDetails userDetails = authenticationService.loadUserByUsername("test");
        Assert.assertEquals(userDetails, expectedUser);
    }

    @Test(expected = RuntimeException.class)
    public void testLoadUserByUsername_exception() {
        when(userDetailsRepository.findByUserName("test")).thenReturn(null);
        authenticationService.loadUserByUsername("test");
    }

    @Test
    public void testValidateJwtToken() {
        when(userDetailsRepository.findByUserName("test")).thenReturn(expectedUser);
        String token = authenticationService.generateJwtToken(expectedUser);
        Boolean test = authenticationService.validateJwtToken(token);
        Assert.assertEquals(test, true);
    }

    @Test(expected = RuntimeException.class)
    public void testValidateJwtToken_exception() {
        authenticationService.validateJwtToken(null);
    }

    @Test
    public void testAuthenticate() {
        UserDetails testUser = new UserDetails(1L, "test", "test", staff);
        expectedUser.setPassword(encoder.encode(expectedUser.getPassword()));
        when(userDetailsRepository.findByUserName("test")).thenReturn(expectedUser);
        authenticationService.authenticate(testUser);
    }

    @Test(expected = RuntimeException.class)
    public void testAuthenticate_exception() {
        authenticationService.authenticate(null);
    }
}
