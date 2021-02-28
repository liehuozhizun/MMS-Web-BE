package org.ucsccaa.mms.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucsccaa.mms.MembershipManagementSystemApplication;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.domains.Record;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.services.RecordService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MembershipManagementSystemApplication.class)
public class RecordControllerTest {
    private MockMvc mockMvc;
    @Mock
    private RecordService recordService;
    @InjectMocks
    private RecordController recordController;

    private final Long id = 1L;
    private final Member member = new Member(id,"...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...",false);
    private final Staff staff = new Staff(id, "dept","pos", "auth", member);
    private final Record expectedRecord = new Record(id, staff, member, LocalDateTime.now(), "...");
    private final List<Record> expectedRecordList = new ArrayList() {{ add(expectedRecord);}};
    private final List<Record> emptyRecordList = new ArrayList<>();
    @Before
    public void configuration() {
        mockMvc = MockMvcBuilders.standaloneSetup(recordController).build();
    }

    @Test
    public void getRecordByMemberIdTest() throws Exception {
        Mockito.when(recordService.getRecordByMemberId(Mockito.eq(id))).thenReturn(expectedRecordList);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/record/member/1");
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload[0].id").value(expectedRecordList.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload[0].logMessage").value(expectedRecordList.get(0).getLogMessage()));
    }
    @Test
    public void getRecordByMemberIdTest_EmptyList() throws Exception {
        Mockito.when(recordService.getRecordByMemberId(Mockito.any())).thenReturn(emptyRecordList);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/record/member/1");
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void getRecordByMemberIdTest_Null() throws Exception {
        doThrow(new RuntimeException("Invalid argument")).when(recordService).getRecordByMemberId(null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/record/member/");
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void getRecordByStaffIdTest() throws Exception {
        Mockito.when(recordService.getRecordByStaffId(Mockito.any())).thenReturn(expectedRecordList);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/record/staff/1");
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload[0].id").value(expectedRecordList.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload[0].logMessage").value(expectedRecordList.get(0).getLogMessage()));
    }
    @Test
    public void getRecordByStaffIdTest_EmptyList() throws Exception {
        Mockito.when(recordService.getRecordByStaffId(Mockito.any())).thenReturn(emptyRecordList);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/record/staff/1");
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }
    @Test
    public void getRecordByStaffId_Null() throws Exception {
        doThrow(new RuntimeException("Invalid argument")).when(recordService).getRecordByStaffId(null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/record/staff/");
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
