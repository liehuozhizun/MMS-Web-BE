package org.ucsccaa.mms.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.ucsccaa.mms.domains.Authorization;
import org.ucsccaa.mms.repositories.AuthorizationRepository;
import org.ucsccaa.mms.services.impl.AuthorizationServiceImpl;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationTest {
    @Mock
    private AuthorizationRepository authorizationRepository;
    @InjectMocks
    private AuthorizationServiceImpl authorService;

    private final Authorization.LEVEL level = Authorization.LEVEL.LEVEL_1;
    private final Set<Authorization.Authority_GET> authority_gets = new HashSet<Authorization.Authority_GET>() {
        {
            add(Authorization.Authority_GET.READ_STAFF_DEPT);
            add(Authorization.Authority_GET.READ_STAFF_POSITION);
        }
    };
    private final Set<Authorization.Authority_PUT> authority_puts = new HashSet<Authorization.Authority_PUT>() {
        {
            add(Authorization.Authority_PUT.EDIT_STAFF);
        }
    };
    private final Set<Authorization.Authority_POST> authority_posts = new HashSet<Authorization.Authority_POST>() {
        {
            add(Authorization.Authority_POST.ADD_STAFF);
        }
    };
    private final Set<Authorization.Authority_DELETE> authority_deletes = new HashSet<Authorization.Authority_DELETE>() {
        {
            add(Authorization.Authority_DELETE.DELETE_STAFF);
        }
    };
    private Authorization expectedAuthor = new Authorization(1L, level, authority_gets, authority_puts, authority_posts, authority_deletes);
    private final String stringLevel = level.toString();
    private final String checkURI = "STAFF";
    private final Boolean expectedResult = true;

    private Authorization expectedAddAuthor = expectedAuthor;

    @Before
    public void configuration() {
        Mockito.when(authorizationRepository.findByLevel(Mockito.eq(level))).thenReturn(expectedAuthor);
    }

    @Test
    public void testCheckAuthority() {
        authorService.initializor();
        Boolean actualResult = authorService.checkAuthority(stringLevel, "GET", checkURI);
        Assert.assertEquals(expectedResult, actualResult);
        actualResult = authorService.checkAuthority(stringLevel, "PUT", checkURI);
        Assert.assertEquals(true, actualResult);
        actualResult = authorService.checkAuthority(stringLevel, "POST", checkURI);
        Assert.assertEquals(true, actualResult);
        actualResult = authorService.checkAuthority(stringLevel, "DELETE", checkURI);
        Assert.assertEquals(true, actualResult);
        actualResult = authorService.checkAuthority(stringLevel, "GET", "MEMBER");
        Assert.assertFalse(actualResult);

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

    @Test
    public void testAddAuthority() {
        expectedAddAuthor.getAuthoritySet_GET().add(Authorization.Authority_GET.valueOf("READ_MEMBER_NAME"));
        expectedAddAuthor.getAuthoritySet_PUT().add(Authorization.Authority_PUT.valueOf("EDIT_MEMBER"));
        expectedAddAuthor.getAuthoritySet_POST().add(Authorization.Authority_POST.valueOf("ADD_MEMBER"));
        expectedAddAuthor.getAuthoritySet_DELETE().add(Authorization.Authority_DELETE.valueOf("DELETE_MEMBER"));
        Mockito.when(authorizationRepository.save(Mockito.eq(expectedAddAuthor))).thenReturn(expectedAddAuthor);
        authorService.addAuthority(Authorization.LEVEL.LEVEL_1, "READ_MEMBER_NAME");
        authorService.addAuthority(Authorization.LEVEL.LEVEL_1, "EDIT_MEMBER");
        authorService.addAuthority(Authorization.LEVEL.LEVEL_1, "ADD_MEMBER");
        authorService.addAuthority(Authorization.LEVEL.LEVEL_1, "DELETE_MEMBER");
    }

    @Test(expected = RuntimeException.class)
    public void testAddAuthority_nullLevel() {
        authorService.addAuthority(null, "READ_MEMBER_NAME");
    }

    @Test(expected = RuntimeException.class)
    public void testAddAuthority_invalidLevel() {
        authorService.addAuthority(Authorization.LEVEL.valueOf("LEVEL_6"), "READ_MEMBER_NAME");
    }
}