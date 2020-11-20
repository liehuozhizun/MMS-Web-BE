package org.ucsccaa.mms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.domains.Record;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.repositories.MemberRepository;
import org.ucsccaa.mms.repositories.RecordRepository;
import org.ucsccaa.mms.repositories.StaffRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepo;
    @Autowired
    private StaffRepository staffRepo;
    @Autowired
    private MemberRepository memberRepo;
    /**
     * Return the id of the new Record
     * @param staff, member: the staff/member who is included in this record
     * will be represented as staffId and memberId in database
     * @param logInfo: A message of the operations. For example "Log in"
     * @return id if the record is exist otherwise return 0
     * @Hint: the LocalDateTime will be automatically added into database
     */
    public void addRecord(Staff staff, Member member, String logInfo) {
        if(staff == null || member == null) {
            throw new RuntimeException("Invalid argument");
        }
        Record record = new Record();
        record.setMember(member);
        record.setStaff(staff);
        record.setLogMessage(logInfo);
        recordRepo.save(record);
    }

    /**
     * Returns a list of Record that related to a single Staff
     * @param id: the id of the staff
     * @return List of Record that related to the <tt>StaffId<tt/>
     */
    public List<Record> getRecordByStaffId(Long id) {
        Optional<Staff> staff = staffRepo.findById(id);
        if (staff.isPresent()) {
            return recordRepo.findByStaff(staff.get());
        } else {
            throw new RuntimeException("Invalid argument(staff is null)");
        }
    }

    /**
     * Returns a list of Record that related to a single Member
     * @param id: the id of the member
     * @return List of Record that related to the <tt>MemberId<tt/>
     */
    public List<Record> getRecordByMemberId(Long id) {
        Optional<Member> member = memberRepo.findById(id);
        if (member.isPresent()) {
            List<Record> record = recordRepo.findByMember(member.get());
            return record;
        } else {
            throw new RuntimeException("Invalid argument(member is null)");
        }
    }

    /**
     * Returns All the Records from the Database
     * @return List of Record
     */
    public List<Record> ListAllRecord() {
        return recordRepo.findAll();
    }

}