package org.ucsccaa.mms.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucsccaa.mms.MembershipManagementSystemApplication;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.domains.Orders;
import org.ucsccaa.mms.services.OrderService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MembershipManagementSystemApplication.class)
public class OrderControllerTest {
    protected MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private OrderService service;
    @InjectMocks
    private OrderController controller;
    private final Orders expectedOrder = 
        new Orders((long)1, "status", null, "item", (long)10, 10.0, "comment", new Member());

    @Before
    public void Before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void createOrderTest() throws Exception {
        when(service.createOrder(any())).thenReturn(expectedOrder.getId());
        String json = objectMapper.writeValueAsString(expectedOrder);
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload")
                        .value("http://localhost/orders/" + expectedOrder.getId().toString()));
    }

    @Test
    public void createOrderNullTest() throws Exception {
        doThrow(new RuntimeException("ORDER CANNOT BE NULL")).when(service).createOrder(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateOrderTest() throws Exception {
        String json = objectMapper.writeValueAsString(expectedOrder);
        when(service.updateOrder(any())).thenReturn(expectedOrder);
        mockMvc.perform(MockMvcRequestBuilders.put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.id").value(expectedOrder.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.status").value(expectedOrder.getStatus()));
    }

    @Test
    public void updateOrderNotFoundTest() throws Exception {
        String json = objectMapper.writeValueAsString(expectedOrder);
        when(service.updateOrder(any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void getOrderByIdTest() throws Exception {
        when(service.getOrderById(anyLong())).thenReturn(expectedOrder);
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/" + expectedOrder.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.id").value(expectedOrder.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.item").value(expectedOrder.getItem()));
    }

    @Test
    public void getOrderByIdNotFoundTest() throws Exception {
        when(service.getOrderById(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void cancelOrderByIdTest() throws Exception {
        when(service.deleteById(anyLong())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    public void cancelOrderByIdNotFoundTest() throws Exception {
        when(service.deleteById(anyLong())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }
}
