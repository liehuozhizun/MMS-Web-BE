package org.ucsccaa.mms.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.domains.Orders;
import org.ucsccaa.mms.models.ServiceResponse;
import org.ucsccaa.mms.models.Status;
import org.ucsccaa.mms.services.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.PutMapping;

@Api(tags = "Order RESTful API")
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService service;
    
    @ApiOperation("Create new order")
    @PostMapping
    public ServiceResponse<URI> createOrder(@RequestBody Orders order, HttpServletRequest req) throws URISyntaxException {
        try {
            Long id = service.createOrder(order);
            return new ServiceResponse<URI>(new URI(req.getRequestURL() + "/" + id));
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update order by ID")
    @PutMapping
    public ServiceResponse<Orders> updateOrder(@RequestBody Orders order) {
        Orders updatedOrder = null;
        try {
            updatedOrder = service.updateOrder(order);
            if (updatedOrder == null) 
                return new ServiceResponse<>(Status.NOT_FOUND, "ORDER NOT FOUND");
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        return new ServiceResponse<>(updatedOrder);
    }

    @ApiOperation("Get order by OrderID")
    @GetMapping("/{id}")
    public ServiceResponse<Orders> getOrderById(@PathVariable Long id) {
        Orders order = null;
        try {
            order = service.getOrderById(id);
            if (order == null) 
                return new ServiceResponse<>(Status.NOT_FOUND, "ID NOT FOUND");
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        return new ServiceResponse<>(order);
    }

    @ApiOperation("Get all orders")
    @GetMapping
    public ServiceResponse<List<Orders>> getAll() {
        List<Orders> all = service.getAll();
        return new ServiceResponse<>(all);
    }

    @ApiOperation("Get orders by MemberID")
    @GetMapping("/_member")
    public ServiceResponse<List<Orders>> getOrderByMember(@RequestBody Member member) {
        List<Orders> list;
        try {
            list = service.getOrderByMember(member);
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        return new ServiceResponse<>(list);
    }

    @ApiOperation("Delete order by OrderID")
    @DeleteMapping("/{id}")
    public ServiceResponse<Object> cancelOrderById(@PathVariable Long id) {
        try {
            boolean deleted = service.deleteById(id);
            if (!deleted) 
                return new ServiceResponse<>(Status.NOT_FOUND, "ID NOT FOUND");
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        return new ServiceResponse<>();
    }
}
