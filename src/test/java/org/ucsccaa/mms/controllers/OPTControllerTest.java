package org.ucsccaa.mms.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucsccaa.mms.MembershipManagementSystemApplication;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.domains.OPT;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.services.OPTService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MembershipManagementSystemApplication.class)
public class OPTControllerTest {

    protected MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private OPTService optService;

    @InjectMocks
    private OPTController optController;
    private final OPT expectOPT = new OPT(
            (long) 1,"STATUS",new Staff(),new Member(),LocalDateTime.now(),LocalDateTime.now(),"TITLE","POSITION","TEST-CARD-ID",LocalDateTime.now(),LocalDateTime.now()
    );
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(optController).build();
    }

    @Test
    public void createOPTTest() throws Exception {
        when(optService.createOPT(any())).thenReturn(expectOPT.getId());
        String json = objectMapper.writeValueAsString(expectOPT);
        mockMvc.perform(MockMvcRequestBuilders.post("/opts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload").value("http://localhost/opts/" + expectOPT.getId().toString()));
    }

    @Test
    public void createEmptyBodyTest() throws Exception {
        doThrow(new IllegalArgumentException("OPT can't be NULL")).when(optService).createOPT(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/opts")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getOPTByIDTest() throws Exception {
        when(optService.findOPTByID(any())).thenReturn(Optional.of(expectOPT));
        mockMvc.perform(MockMvcRequestBuilders.get("/opts/" + expectOPT.getId().toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(MockMvcResultMatchers.jsonPath("$.payload.cardNumber").value(expectOPT.getCardNumber()),
                        MockMvcResultMatchers.jsonPath("$.payload.id").value(expectOPT.getId()),
                        MockMvcResultMatchers.jsonPath("$.payload.status").value(expectOPT.getStatus()),
                        MockMvcResultMatchers.status().isOk()));
    }

    @Test
    public void getOPTByIDNoFoundTest() throws Exception {
        when(optService.findOPTByID(any())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/opts/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(ResultMatcher.matchAll(
                        MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND")
                ));
    }

    @Test
    public void updateOPTByIDTest() throws Exception {
        String json = objectMapper.writeValueAsString(expectOPT);
        System.out.println(json);
        when(optService.updateOPTByID(any())).thenReturn(Optional.of(expectOPT));
        mockMvc.perform(MockMvcRequestBuilders.put("/opts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(ResultMatcher.matchAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.payload.status").value("STATUS"),
                        MockMvcResultMatchers.jsonPath("$.payload.cardNumber").value("TEST-CARD-ID")
                ));
    }

    @Test
    public void updateOPTNotFoundTest() throws Exception {
        String json = objectMapper.writeValueAsString(expectOPT);
        System.out.println(json);
        when(optService.updateOPTByID(any())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.put("/opts").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(ResultMatcher.matchAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND")
                ));

    }

    @Test
    public void deleteByIDTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/opts/1"))
                .andExpect(ResultMatcher.matchAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS")
                ));
    }

    @Test
    public void deleteByIDNotFoundTest() throws Exception {
        doThrow(new EmptyResultDataAccessException(0)).when(optService).deleteOPT(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/opts/1"))
                .andExpect(ResultMatcher.matchAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND")
                ));
    }

}
