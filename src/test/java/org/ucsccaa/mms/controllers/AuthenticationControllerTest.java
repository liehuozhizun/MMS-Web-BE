package org.ucsccaa.mms.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucsccaa.mms.controllers.impl.AuthenticationControllerImpl;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.domains.UserDetails;
import org.ucsccaa.mms.domains.Authorization;
import org.ucsccaa.mms.services.impl.AuthenticationServiceImpl;

@RunWith(SpringRunner.class)
public class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationServiceImpl authenticationService;

    @InjectMocks
    private AuthenticationControllerImpl authenticationController;

    private final Authorization authorization = null;
    private final Member member = new Member(1L,"test","test","test","test",
            "test","test","test","test","test","test","test",
            "test","test","test","test","test","test","test","test",
            "test","test","test",true);
    private final Staff staff = new Staff(1L, "test", "test", authorization, member);
    private final UserDetails expectedUser = new UserDetails(1L, "test", "test", staff);
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Before
    public void configure() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    public void testCreateJwt() throws Exception {
        when(authenticationService.generateJwtToken(any())).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDM2MCIsImlhd" +
                "CI6MTYwNjY4ODA5MCwiZXhwIjoxNjA2NjkxNjkwfQ.pXUooP4QWoePGYtXgKTdNkzm8zyi5ecqx_sC_ILigKo");
        String json = objectMapper.writeValueAsString(expectedUser);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/authentications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload")
                .value("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDM2MCIsImlhdCI6MTYwNjY4ODA5MCwiZXhwIjoxNjA2Njkx" +
                        "NjkwfQ.pXUooP4QWoePGYtXgKTdNkzm8zyi5ecqx_sC_ILigKo"));
    }

    @Test
    public void testCreateJwt_exception() throws Exception {
        doThrow(new RuntimeException()).when(authenticationService).authenticate(null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/authentications");

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testRegister() throws Exception {
        when(authenticationService.register()).thenReturn(expectedUser);
        String json = objectMapper.writeValueAsString(expectedUser);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/authentications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.id")
                        .value(expectedUser.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.userName")
                        .value(expectedUser.getUserName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.password")
                        .value(expectedUser.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.staff")
                        .value(expectedUser.getStaff()));
    }
}
