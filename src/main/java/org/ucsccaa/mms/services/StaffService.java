package org.ucsccaa.mms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.repositories.StaffRepository;

import java.util.*;


@Service
public class StaffService {
    @Autowired
    private StaffRepository repo;

    public Long addStaff(Staff staff) {
        if (staff == null) {
            throw new RuntimeException("STAFF CANNOT BE NULL");
        }
        return repo.save(staff).getId();
    }

    public Staff updateStaff(Staff staff) {
        if (staff == null) 
            throw new RuntimeException("STAFF CANNOT BE NULL");
        return repo.existsById(staff.getId()) ? repo.save(staff) : null;
    }

    public Staff getStaff(Long id) {
        if (id == null) 
            throw new RuntimeException("ID CANNOT BE NULL");
        Optional<Staff> staff = repo.findById(id);
        return staff.isEmpty() ? null : staff.get();
    }

    public List<Staff> getStaffByDept(String dept) {
        if (dept == null) 
            throw new RuntimeException("DEPARTMENT CANNOT BE NULL"); 
        return repo.findByDept(dept);
    }

    public List<Staff> getAll() {
        return repo.findAll();
    }

    public Boolean deleteStaffById(Long id) {
        if (id == null) 
            throw new RuntimeException("ID CANNOT BE NULL");
        Optional<Staff> staff = repo.findById(id);
        if (staff.isPresent()) {
            repo.delete(staff.get());
            return true;
        } else {
            return false;
        }
    }
}
