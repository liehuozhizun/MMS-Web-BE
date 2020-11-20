package org.ucsccaa.mms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.domains.Record;
import org.ucsccaa.mms.domains.Staff;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByMember(Member m);
    List<Record> findByStaff(Staff s);
}
