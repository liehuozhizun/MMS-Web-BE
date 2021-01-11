package org.ucsccaa.mms.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.models.ServiceResponse;
import org.ucsccaa.mms.models.Status;
import org.ucsccaa.mms.services.StaffService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffService service;

    @PostMapping
    public ServiceResponse<URI> addStaff(@RequestBody Staff staff, HttpServletRequest req) throws URISyntaxException {
        try {
            Long id = service.addStaff(staff);
            return new ServiceResponse<>(new URI(req.getRequestURL() + "/" + id));
        } catch (Exception e) {
            System.out.println("asdfasdf");
            e.printStackTrace();
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
    }

    @PutMapping
    public ServiceResponse<Staff> updateStaff(@RequestBody Staff staff) {
        Staff updatedStaff = null;
        try {
            updatedStaff = service.updateStaff(staff);
            if (updatedStaff == null)
                return new ServiceResponse<>(Status.NOT_FOUND, "STAFF NOT FOUND");
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        return new ServiceResponse<>(updatedStaff);
    }

    @GetMapping("/{id}") 
    public ServiceResponse<Staff> getStaff(@PathVariable Long id) {
        Staff staff = null;
        try {
            staff = service.getStaff(id);
            if (staff == null)
                return new ServiceResponse<>(Status.NOT_FOUND, "ID NOT FOUND");
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        return new ServiceResponse<>(staff);
    }

    @GetMapping("/_dept")
    public ServiceResponse<List<Staff>> getStaffByDept(@RequestBody Staff staff) {
        try {
            List<Staff> StaffByDept = service.getStaffByDept(staff.getDept());
            return new ServiceResponse<>(StaffByDept);
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
    }

    @GetMapping()
    public ServiceResponse<List<Staff>> getAll() {
        List<Staff> allStaff = service.getAll();
        return new ServiceResponse<>(allStaff);
    }
    
    @DeleteMapping("/{id}")
    public ServiceResponse<Staff> deleteStaffById(@PathVariable Long id) {
        try {
            boolean deleted = service.deleteStaffById(id);
            if (!deleted)
                return new ServiceResponse<>(Status.NOT_FOUND, "ID NOT FOUND");
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
        return new ServiceResponse<>();
    }
}
