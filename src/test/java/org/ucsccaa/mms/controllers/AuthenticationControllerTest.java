package org.ucsccaa.mms.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.doThrow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.domains.UserDetails;
import org.ucsccaa.mms.domains.Authorization;
import org.ucsccaa.mms.services.AuthenticationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AuthenticationControllerTest {

    protected MockMvc mockMvc;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private final Authorization authorization = null;
    private final Member member = new Member(1L,"test","test","test","test",
            "test","test","test","test","test","test","test",
            "test","test","test","test","test","test","test","test",
            "test","test","test",true);
    private final Staff staff = new Staff(1L, "test", "test", authorization, member);
    private final UserDetails expectedUser = new UserDetails(1L, "test", "test", staff);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String token = "test_token";
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }
    @Test
    public void generateTokenTest() throws Exception {
        when(authenticationService.generateToken(any())).thenReturn(token);

        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"username\":\"test\",\"password\":\"test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload").value(token));

    }
    @Test
    public void generateTokenNullArgTest() throws Exception{
        doThrow(new IllegalArgumentException("Null username")).when(authenticationService).generateToken(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"username\":\"test\",\"password\":\"test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ERROR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Bad Request"));
    }
    @Test
    public void loginSuccessTest() throws Exception{
        when(authenticationService.validateToken(any())).thenReturn(true);
        when(authenticationService.loadUserByUsername(any())).thenReturn(expectedUser);
        when(authenticationService.getUserNameFromToken(any())).thenReturn(expectedUser.getUserName());
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header("authorization",token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.id").value(expectedUser.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.userName").value(expectedUser.getUserName()));
    }
    @Test
    public void loginFailTest() throws Exception{
        when(authenticationService.validateToken(any())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header("authorization",token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ERROR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Unauthorized"));
    }
    @Test
    public void loginExpectionTest()throws Exception{
        when(authenticationService.validateToken(any())).thenReturn(true);
        doThrow(new IllegalArgumentException("IllegalArgument")).when(authenticationService).loadUserByUsername(any());
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header("authorization",token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ERROR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Bad Request"));
    }
}
