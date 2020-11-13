package org.ucsccaa.mms.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ucsccaa.mms.domains.OPT;
import org.ucsccaa.mms.models.ServiceResponse;
import org.ucsccaa.mms.models.Status;
import org.ucsccaa.mms.services.OPTService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Api(tags = "OPT RESTFUL API")
@RestController
@RequestMapping(value = "/opts", produces = MediaType.APPLICATION_JSON_VALUE)
public class OPTController {
    @Autowired
    private OPTService optService;

    @ApiOperation("create new OPT")
    @PostMapping
    public ServiceResponse<URI> addOPT(@RequestBody OPT opt, HttpServletRequest req) throws URISyntaxException {
        try {
            Long id = optService.createOPT(opt);
            return new ServiceResponse<>(new URI(req.getRequestURL() + "/" + id));
        } catch (IllegalArgumentException e) {
            return new ServiceResponse<>(Status.ERROR, "Bad Request");
        }
    }

    @ApiOperation("get OPT by ID")
    @GetMapping("/{id}")
    public ServiceResponse<OPT> getUserByID(@PathVariable Long id) {
        Optional<OPT> opt = optService.findOPTByID(id);
        return opt.map((ServiceResponse::new)).orElseGet(() -> new ServiceResponse<>(Status.NOT_FOUND, "NO USER FOUND"));
    }

    @ApiOperation("update OPT by ID")
    @PutMapping
    public ServiceResponse<OPT> updateUserByID(@RequestBody OPT opt) {
        Optional<OPT> optionalOPT;
        try {
            optionalOPT = optService.updateOPTByID(opt);
        } catch (IllegalArgumentException e) {
           return new ServiceResponse<>(Status.ERROR,"BAD REQUEST NO ID");
        }
        return optionalOPT.map(ServiceResponse::new).orElseGet(() -> new ServiceResponse<>(Status.NOT_FOUND,"NO OPT FOUND"));
    }

    @ApiOperation("delete OPT by ID")
    @DeleteMapping("/{id}")
    public ServiceResponse<Object> deleteUserByID(@PathVariable Long id) {
        try {
            optService.deleteOPT(id);
        } catch (EmptyResultDataAccessException e) {
            return new ServiceResponse<>(Status.NOT_FOUND,"NO USER FOUND");
        }
        return new ServiceResponse<>(Status.SUCCESS);
    }

    @ApiOperation("list all OPTs")
    @GetMapping
    public ServiceResponse<List<OPT>> listAll(){
        List<OPT> OPTAll = optService.listAll();
        return new ServiceResponse<>(OPTAll);
    }
}
