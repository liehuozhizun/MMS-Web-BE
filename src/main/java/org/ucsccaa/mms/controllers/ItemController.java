package org.ucsccaa.mms.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucsccaa.mms.domains.Item;
import org.ucsccaa.mms.models.ServiceResponse;
import org.ucsccaa.mms.models.Status;
import org.ucsccaa.mms.services.ItemService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService service;

    @PostMapping
    public ServiceResponse<URI> addItem(@RequestBody Item item, HttpServletRequest req) throws URISyntaxException {
        try {
            Long id = service.addItem(item);
            return new ServiceResponse<>(new URI(req.getRequestURL() + "/" + id));
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
    }

    @PutMapping
    public ServiceResponse<Item> updateItem(@RequestBody Item item) {
        Item updatedItem = null;
        try {
            updatedItem = service.updateItem(item);
            if (updatedItem == null) 
                return new ServiceResponse<>(Status.NOT_FOUND, "ITEM NOT FOUND");
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        return new ServiceResponse<Item>(updatedItem);
    }

    @GetMapping("/{id}")
    public ServiceResponse<Item> getItem(@PathVariable Long id) {
        Item item = null;
        try {
            item = service.getItem(id);
            if (item == null)
                return new ServiceResponse<>(Status.NOT_FOUND, "ID NOT FOUND");
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        return new ServiceResponse<Item>(item);
    }

    @GetMapping
    public ServiceResponse<List<Item>> getAll() {
        List<Item> allItem = service.getAll();
        return new ServiceResponse<List<Item>>(allItem);
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<Object> deleteItemById(@PathVariable Long id) {
        try {
            boolean deleted = service.deleteItemById(id);
            if (!deleted)
                return new ServiceResponse<>(Status.NOT_FOUND, "ID NOT FOUND");
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        return new ServiceResponse<>();
    }
}
