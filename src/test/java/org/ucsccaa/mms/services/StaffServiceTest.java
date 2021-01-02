package org.ucsccaa.mms.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.*;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.ucsccaa.mms.domains.Authorization;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.repositories.StaffRepository;

@RunWith(MockitoJUnitRunner.class)
public class StaffServiceTest {
    @Mock
    private StaffRepository repository;
    @InjectMocks
    private StaffService service;

    @Test
    public void addStaffTest() {
        Staff expectedStaff = new Staff();
        when(repository.save(any())).thenReturn(expectedStaff);
        Long id = service.addStaff(expectedStaff);
        Assert.assertEquals(id, expectedStaff.getId());
    }

    @Test(expected = RuntimeException.class)
    public void addStaffNullTest() {
        service.addStaff(null);
    }

    @Test
    public void updateStaffTest() {
        Staff expectedStaff = new Staff();
        expectedStaff.setId((long)1);
        expectedStaff.setAuthorization(new Authorization());
        expectedStaff.setDept("A");
        expectedStaff.setPosition("admin");
        expectedStaff.setMember(new Member());

        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.save(any())).thenReturn(expectedStaff);
        Staff staff = service.updateStaff(expectedStaff);
        Assert.assertNotNull(staff);
        Assert.assertEquals(expectedStaff.getId(), staff.getId());
        Assert.assertEquals(expectedStaff.getDept(), staff.getDept());
        Assert.assertEquals(expectedStaff.getPosition(), staff.getPosition());
    }

    @Test
    public void updateStaffNotFoundTest() {
        Staff staff = service.updateStaff(new Staff());
        Assert.assertEquals(null, staff);
    }

    @Test(expected = RuntimeException.class)
    public void updateStaffNullTest() {
        service.updateStaff(null);
    }

    @Test
    public void getStaffTest() {
        Staff expectedStaff = new Staff();
        expectedStaff.setId((long)1);
        expectedStaff.setAuthorization(new Authorization());
        expectedStaff.setDept("A");
        expectedStaff.setPosition("admin");
        expectedStaff.setMember(new Member());

        when(repository.findById(expectedStaff.getId())).thenReturn(Optional.of(expectedStaff));
        Staff staff = service.getStaff(expectedStaff.getId());
        Assert.assertEquals(expectedStaff.getId(), staff.getId());
        Assert.assertEquals(expectedStaff.getAuthorization(), staff.getAuthorization());
        Assert.assertEquals(expectedStaff.getDept(), staff.getDept());
        Assert.assertEquals(expectedStaff.getMember(), staff.getMember());
        Assert.assertEquals(expectedStaff.getPosition(), staff.getPosition());
    }

    @Test
    public void getStaffNotFoundTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Staff staff = service.getStaff(anyLong());
        Assert.assertEquals(null, staff);
    }

    @Test(expected = RuntimeException.class)
    public void getStaffNullTest() {
        service.getStaff(null);
    }

    @Test
    public void deleteStaffTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Staff()));
        boolean result = service.deleteStaffById((long)1);
        Assert.assertEquals(true, result);
    }

    @Test
    public void deleteStaffNotFoundTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        boolean result = service.deleteStaffById((long)1);
        Assert.assertEquals(false, result);
    }

    @Test(expected = RuntimeException.class)
    public void deleteStaffNullTest() {
        service.deleteStaffById(null);
    }

    @Test
    public void getStaffByDeptTest() {
        Staff staff = new Staff();
        staff.setDept("B");
        List<Staff> expected = new ArrayList<>();
        expected.add(staff);

        when(repository.findByDept("B")).thenReturn(expected);
        List<Staff> result = service.getStaffByDept(staff.getDept());
        Assert.assertEquals(expected, result);
    }

    @Test(expected = RuntimeException.class)
    public void getStaffByDeptNullTest() {
        service.getStaffByDept(null);
    }

}
