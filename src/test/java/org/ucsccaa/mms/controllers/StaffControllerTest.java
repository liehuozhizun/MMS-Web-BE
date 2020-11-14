package org.ucsccaa.mms.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucsccaa.mms.MembershipManagementSystemApplication;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.services.StaffService;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MembershipManagementSystemApplication.class)
public class StaffControllerTest {
    protected MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private StaffService service;
    @InjectMocks
    private StaffController controller;
    private final Staff expectedStaff = new Staff((long)1, "A", "admin", "authorization", new Member());

    @Before
    public void Before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void addStaffTest() throws Exception {
        when(service.addStaff(any())).thenReturn(expectedStaff.getId());
        String json = objectMapper.writeValueAsString(expectedStaff);
        mockMvc.perform(MockMvcRequestBuilders.post("/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload")
                                                .value("http://localhost/staff/" + expectedStaff.getId().toString()));
    }

    @Test
    public void addStaffNullTest() throws Exception {
        doThrow(new RuntimeException("STAFF CANNOT BE NULL")).when(service).addStaff(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/staff")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateStaffTest() throws Exception {
        String json = objectMapper.writeValueAsString(expectedStaff);
        when(service.updateStaff(any())).thenReturn(expectedStaff);
        mockMvc.perform(MockMvcRequestBuilders.put("/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.id").value(expectedStaff.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.dept").value(expectedStaff.getDept()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.position").value(expectedStaff.getPosition()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.authorization").value(expectedStaff.getAuthorization()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.member").value(expectedStaff.getMember()));
    }

    @Test
    public void updateStaffNotFoundTest() throws Exception {
        String json = objectMapper.writeValueAsString(expectedStaff);
        when(service.updateStaff(any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/staff").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void getStaffTest() throws Exception {
        when(service.getStaff(anyLong())).thenReturn(expectedStaff);
        mockMvc.perform(MockMvcRequestBuilders.get("/staff/" + expectedStaff.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.id").value(expectedStaff.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.dept").value(expectedStaff.getDept()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.position").value(expectedStaff.getPosition()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.authorization").value(expectedStaff.getAuthorization()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.member").value(expectedStaff.getMember()));
    }

    @Test
    public void getStaffNotFoundTest() throws Exception {
        when(service.getStaff(any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/staff/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void deleteStaffByIdTest() throws Exception {
        when(service.deleteStaffById(anyLong())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/staff/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    public void deleteStaffByIdNotFoundTest() throws Exception {
        when(service.deleteStaffById(anyLong())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/staff/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test 
    public void getStaffByDeptTest() throws Exception {
        List<Staff> expectedList = new ArrayList<>();
        when(service.getStaffByDept(anyString())).thenReturn(expectedList);
        mockMvc.perform(MockMvcRequestBuilders.get("/staff"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload").value(expectedList));
    }
}
