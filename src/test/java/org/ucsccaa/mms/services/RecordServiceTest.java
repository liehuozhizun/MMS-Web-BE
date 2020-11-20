package org.ucsccaa.mms.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.domains.Record;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.repositories.MemberRepository;
import org.ucsccaa.mms.repositories.RecordRepository;
import org.ucsccaa.mms.repositories.StaffRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RecordServiceTest {
    @Mock
    private RecordRepository recordRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private RecordService recordService;

    private final Long id = 1L;
    private final Member member = new Member(1L,"...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...","...",false);
    private final Staff staff = new Staff(id, "dept","pos", "auth", member);
    private final String logInfo = "...";
    private final Record expectedRecord = new Record(id, staff, member, LocalDateTime.now(), logInfo);
    private final List<Record> expectedRecordList = new ArrayList() {{ add(expectedRecord);}};
    private final Optional<Staff> optionalExpectedStaff = Optional.of(staff);
    private final Optional<Member> optionalExpectedMember = Optional.of(member);

    @Before
    public void configuration() {
        Mockito.when(recordRepository.save(Mockito.eq(expectedRecord))).thenReturn(expectedRecord);
        Mockito.when(staffRepository.findById(Mockito.eq(id))).thenReturn(optionalExpectedStaff);
        Mockito.when(memberRepository.findById(Mockito.eq(id))).thenReturn(optionalExpectedMember);
        Mockito.when(recordRepository.findByStaff(Mockito.eq(staff))).thenReturn(expectedRecordList);
        Mockito.when(recordRepository.findByMember(Mockito.eq(member))).thenReturn(expectedRecordList);
    }

    @Test
    public void testAddRecord() {
        recordService.addRecord(staff, member, logInfo);
    }
    @Test(expected = RuntimeException.class)
    public void testAddRecord_invalidArgument() {
        recordService.addRecord(null, member, logInfo);
    }

    @Test
    public void testGetRecordByStaffId() {
        List<Record> actualRecordList = recordService.getRecordByStaffId(id);
        Assert.assertEquals(expectedRecordList, actualRecordList);
        Assert.assertEquals(expectedRecordList.get(0).getStaff(), actualRecordList.get(0).getStaff());
    }
    @Test(expected = RuntimeException.class)
    public void testGetRecordByStaffId_invalidArgument() {
        recordService.getRecordByStaffId(0L);
    }

    @Test
    public void testGetRecordByMemberId() {
        List<Record> actualRecordList = recordService.getRecordByMemberId(id);
        Assert.assertEquals(expectedRecordList.get(0).getLogMessage(), actualRecordList.get(0).getLogMessage());
        Assert.assertEquals(expectedRecordList.get(0).getMember(), actualRecordList.get(0).getMember());
    }
    @Test(expected = RuntimeException.class)
    public void testGetRecordByMemberId_invalidArgument() {
        recordService.getRecordByMemberId(0L);
    }
}
