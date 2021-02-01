package org.ucsccaa.mms.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucsccaa.mms.services.AuthenticationService;
import org.ucsccaa.mms.services.AuthorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


public class AuthorizationControllerTest {
    protected MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorizationController authenticationController;
    private static final String testLevel="LEVEL_1";
    private static final String testAuthority="test";
    private static final String testExceptionMessage="WTFException";

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }
    @Test
    public void addAuthorityTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/authorization")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"level\":\""+testLevel+"\",\"authority\":\""+testAuthority+"\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload").value("/authorization/" + testLevel + "/" + testAuthority));
    }
    @Test
    public void addAuthorityInvalidLevelTest() throws Exception {
        doThrow(new IllegalArgumentException(testExceptionMessage)).when(authorService).addAuthority(any(),any());
        mockMvc.perform(MockMvcRequestBuilders.post("/authorization")
                .content("{\"level\":\""+testLevel+"\",\"authority\":\""+testAuthority+"\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ERROR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(testExceptionMessage));
    }
}
