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

    private Set<Authorization.Authority_GET> authority_gets = new HashSet<>();
    private Set<Authorization.Authority_POST> authority_posts = new HashSet<>();
    private Set<Authorization.Authority_PUT> authority_puts = new HashSet<>();
    private Set<Authorization.Authority_DELETE> authority_deletes = new HashSet<>();

    private final Authorization authorization = new Authorization(Authorization.LEVEL.LEVEL_1, authority_gets, authority_puts, authority_posts, authority_deletes);
    private final Member member = new Member(1L,"test","test","test","test",
            "test","test","test","test","test","test","test",
            "test","test","test","test","test","test","test","test",
            "test","test","test",true);
    private final Staff staff = new Staff(1L, "test", "test", authorization, member);
    private final UserDetails expectedUser = new UserDetails(1L, "test", "test", staff);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void testGenerateJwtToken() {
        String token = authenticationService.generateToken(expectedUser);
        Assert.assertNotNull(token);
    }

    @Test(expected = RuntimeException.class)
    public void testGenerateJwtToken_exception() {
        authenticationService.generateToken(null);
    }

    @Test
    public void testGetUserNameFromToken() {
        String token = authenticationService.generateToken(expectedUser);
        String username = authenticationService.getUserNameFromToken(token);
        Assert.assertEquals(expectedUser.getUserName(), username);
    }

    @Test(expected = RuntimeException.class)
    public void testGetUserNameFromToken_exception() {
        authenticationService.getUserNameFromToken(null);
    }

    @Test
    public void testGetLevelFromToken() {
        when(userDetailsRepository.findByUserName("test")).thenReturn(Optional.of(expectedUser));
        String token = authenticationService.generateToken(expectedUser);
        String level = authenticationService.getLevelFromToken(token);
        Assert.assertEquals(Authorization.LEVEL.LEVEL_1.toString(), level);
    }

    @Test
    public void testGetLevelFromToken_exception() {
        String token = authenticationService.getLevelFromToken(null);
        Assert.assertEquals(token, null);
    }

    @Test
    public void testGetIssuedDateFromToken() {
        String token = authenticationService.generateToken(expectedUser);
        Date date = authenticationService.getIssuedDateFromToken(token);
        Assert.assertNotNull(date);
    }

    @Test(expected = RuntimeException.class)
    public void testGetIssuedDateFromToken_exception() {
        authenticationService.getIssuedDateFromToken(null);
    }

    @Test
    public void testLoadUserByUsername() {
        when(userDetailsRepository.findByUserName("test")).thenReturn(Optional.of(expectedUser));
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
        when(userDetailsRepository.findByUserName("test")).thenReturn(Optional.of(expectedUser));
        String token = authenticationService.generateToken(expectedUser);
        Boolean test = authenticationService.validateToken(token);
        Assert.assertEquals(test, true);
    }

    @Test(expected = RuntimeException.class)
    public void testValidateJwtToken_exception() {
        authenticationService.validateToken(null);
    }

    @Test
    public void testAuthenticate() {
        UserDetails testUser = new UserDetails(1L, "test", "test", staff);
        expectedUser.setPassword(encoder.encode(expectedUser.getPassword()));
        when(userDetailsRepository.findByUserName("test")).thenReturn(Optional.of(expectedUser));
        authenticationService.authenticate("test", "test");
    }

    @Test(expected = RuntimeException.class)
    public void testAuthenticate_exception() {
        authenticationService.authenticate(null, null);
    }
}
