package org.ucsccaa.mms.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.ucsccaa.mms.MembershipManagementSystemApplication;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.domains.Treasury;
import org.ucsccaa.mms.services.TreasuryService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MembershipManagementSystemApplication.class)
public class TreasuryControllerTest {
    protected MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TreasuryService treasuryService;
    
    @InjectMocks
    private TreasuryController treasuryController;
    private final Treasury expectedTreasury = new Treasury(1L, new Staff(), null, 100.0, "test");

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(treasuryController).build();
    }

    @Test
    public void addTreasuryTest() throws Exception {
        when(treasuryService.addTreasury(any())).thenReturn(expectedTreasury.getId());
        String json = objectMapper.writeValueAsString(expectedTreasury);
        mockMvc.perform(MockMvcRequestBuilders.post("/treasury")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload")
                                                .value("/treasury/" + expectedTreasury.getId().toString()));
    }

    @Test
    public void addTreasuryNullTest() throws Exception {
        doThrow(new IllegalArgumentException()).when(treasuryService).addTreasury(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/treasury")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateTreasuryTest() throws Exception {
        String json = objectMapper.writeValueAsString(expectedTreasury);
        when(treasuryService.updateTreasury(any())).thenReturn(Optional.of(expectedTreasury));
        mockMvc.perform(MockMvcRequestBuilders.put("/treasury")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.id").value(expectedTreasury.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.staff").value(expectedTreasury.getStaff()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.timestamp").value(expectedTreasury.getTimestamp()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.amount").value(expectedTreasury.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.comment").value(expectedTreasury.getComment()));
    }

    @Test
    public void updateStaffNonExistingEntityTest() throws Exception {
        String json = objectMapper.writeValueAsString(expectedTreasury);
        when(treasuryService.updateTreasury(any())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.put("/treasury")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void updateStaffNullTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/treasury")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteTreasuryTest() throws Exception{
        when(treasuryService.deleteTreasury(anyLong())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/treasury/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteTreasuryNonExistingEntityTest() throws Exception{
        when(treasuryService.deleteTreasury(any())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/treasury/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void getTreasuriesByStaffNullTest() throws Exception {
        doThrow(new IllegalArgumentException()).when(treasuryService).getTreasuriesByStaff(null);
        mockMvc.perform(MockMvcRequestBuilders.delete("/treasury/staff/"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
