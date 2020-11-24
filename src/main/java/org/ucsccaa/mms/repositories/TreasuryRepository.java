package org.ucsccaa.mms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ucsccaa.mms.domains.Treasury;
import org.ucsccaa.mms.domains.Staff;

import java.util.List;

@Repository
public interface TreasuryRepository extends JpaRepository<Treasury, Long> {
    List<Treasury> findByStaff(Staff staff);
}
