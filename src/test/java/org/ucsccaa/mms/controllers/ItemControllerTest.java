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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucsccaa.mms.MembershipManagementSystemApplication;
import org.ucsccaa.mms.domains.Item;
import org.ucsccaa.mms.services.ItemService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MembershipManagementSystemApplication.class)
public class ItemControllerTest {
    protected MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private ItemService service;
    @InjectMocks
    private ItemController controller;
    private final Item expectedItem = new Item((long)1, "status", "name", "comment", 1.0);

    @Before
    public void before() {
        // MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void addItemTest() throws Exception {
        when(service.addItem(any())).thenReturn(expectedItem.getId());
        String json = objectMapper.writeValueAsString(expectedItem);
        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload")
                        .value("http://localhost/items/" + expectedItem.getId().toString()));
    }

    @Test
    public void addItemNullTest() throws Exception {
        doThrow(new RuntimeException("ITEM CANNOT BE NULL")).when(service).addItem(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateItemTest() throws Exception {
        String json = objectMapper.writeValueAsString(expectedItem);
        when(service.updateItem(any())).thenReturn(expectedItem);
        mockMvc.perform(MockMvcRequestBuilders.put("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.id").value(expectedItem.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.status").value(expectedItem.getStatus()));
    }

    @Test
    public void updateItemNotFoundTest() throws Exception {
        String json = objectMapper.writeValueAsString(expectedItem);
        when(service.updateItem(any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void updateItemNullTest() throws Exception {
        doThrow(new RuntimeException("ITEM CANNOT BE NULL")).when(service).updateItem(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/items")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getItemTest() throws Exception {
        when(service.getItem(anyLong())).thenReturn(expectedItem);
        mockMvc.perform(MockMvcRequestBuilders.get("/items/" + expectedItem.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.id").value(expectedItem.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.name").value(expectedItem.getName()));
    }

    @Test
    public void getItemNotFoundTest() throws Exception {
        when(service.getItem(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/items/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void deleteItemTest() throws Exception {
        when(service.deleteItemById(anyLong())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    public void deleteItemNotFoundTest() throws Exception {
        when(service.deleteItemById(anyLong())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void deleteItemNullTest() throws Exception {
        doThrow(new RuntimeException("ID CANNOT BE NULL")).when(service).deleteItemById(null);
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
