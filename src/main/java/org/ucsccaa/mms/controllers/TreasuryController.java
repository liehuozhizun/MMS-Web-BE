package org.ucsccaa.mms.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucsccaa.mms.domains.Treasury;
import org.ucsccaa.mms.models.ServiceResponse;
import org.ucsccaa.mms.models.Status;
import org.ucsccaa.mms.services.TreasuryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Treasury RESTFUL API")
@RestController
@RequestMapping("/treasury")
public class TreasuryController {
    @Autowired
    private TreasuryService treasuryService;

    @ApiOperation("create new treasury")
    @PostMapping
    public ServiceResponse<URI> addTreasury(@RequestBody Treasury treasury, HttpServletRequest req)
            throws URISyntaxException {
        try {
            Long id = treasuryService.addTreasury(treasury);
            return new ServiceResponse<>(new URI(req.getRequestURI() + "/" + id));
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
    }

    @ApiOperation("update treasury")
    @PutMapping
    public ServiceResponse<Treasury> updateTreasury(@RequestBody Treasury treasury) {
        try {
            Optional<Treasury> updatedTreasury = treasuryService.updateTreasury(treasury);
            return updatedTreasury.map(ServiceResponse::new)
                    .orElseGet(() -> new ServiceResponse<>(Status.NOT_FOUND, "Treasury is not found"));
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
    }

    @ApiOperation("get treasury by ID")
    @GetMapping("/{id}")
    public ServiceResponse<Treasury> getTreasury(@PathVariable Long id) {
        try {
            Optional<Treasury> treasury = treasuryService.getTreasury(id);
            return treasury.map(ServiceResponse::new)
                    .orElseGet(() -> new ServiceResponse<>(Status.NOT_FOUND, "Treasury is not found"));
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
    }

    @ApiOperation("delete treasury by ID")
    @DeleteMapping("/{id}")
    public ServiceResponse<Object> deleteTreasury(@PathVariable Long id) {
        try {
            Boolean result = treasuryService.deleteTreasury(id);
            return (result ? new ServiceResponse<>(Status.SUCCESS, "Successfully deleted") : new ServiceResponse<>(Status.NOT_FOUND, "Treasury is not found"));
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
    }

    @ApiOperation("get treasuries by Staff ID")
    @GetMapping("/staff/{staff_id}")
    public ServiceResponse<List<Treasury>> getTreasuriesByStaff(@PathVariable("staff_id") Long staffId) {
        try {
            List<Treasury> treasuries = treasuryService.getTreasuriesByStaff(staffId);
            return new ServiceResponse<>(treasuries);
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
    }

    @ApiOperation("get all treasuries")
    @GetMapping
    public ServiceResponse<List<Treasury>> getAllTreasuries() {
        List<Treasury> treasuries = treasuryService.getAll();
        return new ServiceResponse<>(treasuries);
    }
}
