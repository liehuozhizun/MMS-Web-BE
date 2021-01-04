package org.ucsccaa.mms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ucsccaa.mms.domains.Record;
import org.ucsccaa.mms.models.ServiceResponse;
import org.ucsccaa.mms.models.Status;
import org.ucsccaa.mms.services.RecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(tags = "RECORD RESTFUL API")
@RestController
@RequestMapping("record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @ApiOperation("get Record by Member ID")
    @GetMapping("/member/{memberId}")
    public ServiceResponse<List<Record>> getRecordByMemberId(@PathVariable Long memberId) {
        try {
            List<Record> recordList = recordService.getRecordByMemberId(memberId);
            if (!recordList.isEmpty()) {
                return new ServiceResponse<>(recordList);
            } else {
                return new ServiceResponse<>(Status.NOT_FOUND, "couldn't find record by the memberId");
            }
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
    }

    @ApiOperation("get Record by Staff ID")
    @GetMapping("/staff/{staffId}")
    public ServiceResponse<List<Record>> getRecordByStaffId(@PathVariable Long staffId) {
        try {
                List<Record> recordList = recordService.getRecordByStaffId(staffId);
                if(!recordList.isEmpty()) {
                    return new ServiceResponse<>(recordList);
                } else {
                 return new ServiceResponse<>(Status.NOT_FOUND, "couldn't find record by the staffId");
                }
        } catch (Exception e) {
            return new ServiceResponse<>(Status.ERROR, e.getMessage());
        }
    }

    @ApiOperation("get all Records")
    @GetMapping
    public ServiceResponse<List<Record>> getAllRecords() {
        List<Record> recordList = recordService.ListAllRecord();
        if (!recordList.isEmpty()) {
            return new ServiceResponse<>(recordList);
        } else {
            return new ServiceResponse<>(Status.ERROR, "Record is empty");
        }
    }
}