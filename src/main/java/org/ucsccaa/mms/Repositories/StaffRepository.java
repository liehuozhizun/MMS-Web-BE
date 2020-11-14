package org.ucsccaa.mms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ucsccaa.mms.domains.Staff;
import java.util.*;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    public List<Staff> findByDept(String dept);
}
