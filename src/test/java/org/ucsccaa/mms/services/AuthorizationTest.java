package org.ucsccaa.mms.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.ucsccaa.mms.domains.Authorization;
import org.ucsccaa.mms.repositories.AuthorizeRepository;
import org.ucsccaa.mms.services.impl.AuthorizationServiceImpl;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationTest {
    @Mock
    private AuthorizeRepository authorizeRepository;
    @InjectMocks
    private AuthorizationServiceImpl authorService;

    private final Authorization.LEVEL level = Authorization.LEVEL.LEVEL_1;
    private final Set<Authorization.Authority_GET> authority_gets = new HashSet<Authorization.Authority_GET>() {
        {
            add(Authorization.Authority_GET.READ_STAFF_DEPT);
            add(Authorization.Authority_GET.READ_STAFF_POSITION);
        }
    };
    private Authorization expectedAuthor = new Authorization(level, authority_gets, null, null, null);
    private final String stringLevel = level.toString();
    private final String checkMethod = "GET";
    private final String checkURI = "STAFF";
    private final Boolean expectedResult = true;

    @Before
    public void configuration() {
        Mockito.when(authorizeRepository.findByLevel(Mockito.eq(level))).thenReturn(expectedAuthor);
    }


    @Test
    public void testCheckAuthority() {
        ReflectionTestUtils.invokeMethod(AuthorizationServiceImpl.class, "initializeAuthor");
        Boolean actualResult = authorService.checkAuthority(stringLevel, checkMethod, checkURI);
        Assert.assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testGetByLevel() {
        Authorization actualAuthor = authorService.getByLevel(level);
        Assert.assertEquals(expectedAuthor, actualAuthor);
        Assert.assertEquals(expectedAuthor.getAuthoritySet_GET(), actualAuthor.getAuthoritySet_GET());
    }
    @Test(expected = RuntimeException.class)
    public void testGetByLevel_invalidArgument() {
        authorService.getByLevel(null);
    }
}