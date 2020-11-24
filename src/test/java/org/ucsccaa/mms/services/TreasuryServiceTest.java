package org.ucsccaa.mms.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.ucsccaa.mms.domains.Treasury;
import org.ucsccaa.mms.repositories.StaffRepository;
import org.ucsccaa.mms.repositories.TreasuryRepository;

@RunWith(MockitoJUnitRunner.class)
public class TreasuryServiceTest {

    @Mock
    private TreasuryRepository treasuryRepository;

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private TreasuryService treasuryService;


    @Test
    public void addTreasuryTest() {
        Treasury expectedTreasury = new Treasury(null, null, null, 100.00, "test");
        when(treasuryRepository.save(any())).thenReturn(expectedTreasury);
        Long id = treasuryService.addTreasury(expectedTreasury);
        Assert.assertEquals(expectedTreasury.getId(), id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTreasuryNullTest() {
        treasuryService.addTreasury(null);
    }

    @Test
    public void updateTreasuryTest() {
        Treasury expectedTreasury = new Treasury(null, null, null, 100.00, "beforeUpdate");
        when(treasuryRepository.save(any())).thenReturn(expectedTreasury);
        when(treasuryRepository.existsById(any())).thenReturn(true);
        treasuryService.addTreasury(expectedTreasury);
        expectedTreasury.setComment("afterUpdate");
        expectedTreasury.setId(1L);
        Optional<Treasury> actualTreasury = treasuryService.updateTreasury(expectedTreasury);
        Assert.assertEquals(expectedTreasury.getComment(), actualTreasury.get().getComment());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateTreasuryNullTest() {
        treasuryService.updateTreasury(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateTreasuryNullIdTest() {
        Treasury nullIdTreasury = new Treasury(null, null, null, 100.00, "test");
        treasuryService.updateTreasury(nullIdTreasury);
    }

    @Test
    public void updateTreasuryNonExistingEntityTest() {
        when(treasuryRepository.existsById(any())).thenReturn(false);
        Optional<Treasury> actualTreasury = treasuryService.updateTreasury(new Treasury(1L, null, null, 100.00, "test"));
        Assert.assertFalse(actualTreasury.isPresent());
    }

    @Test
    public void getTreasuryTest() {
        Treasury expectedTreasury = new Treasury(null, null, null, 100.00, "test");
        when(treasuryRepository.save(any())).thenReturn(expectedTreasury);
        treasuryService.addTreasury(expectedTreasury);
        when(treasuryRepository.findById(any())).thenReturn(Optional.of(expectedTreasury));
        Optional<Treasury> actualTreasury = treasuryService.getTreasury(1L);
        Assert.assertEquals(expectedTreasury.getComment(), actualTreasury.get().getComment());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTreasuryNullTest() {
        treasuryService.getTreasury(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteTreasuryNullIdTest() {
        treasuryService.deleteTreasury(null);
    }

    @Test
    public void deleteTreasuryNonExistingEntityTest() {
        when(treasuryRepository.existsById(any())).thenReturn(false);
        Boolean result = treasuryService.deleteTreasury(1L);
        Assert.assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTreasuryByStaffNonExistingStaffTest() {
        treasuryService.getTreasuriesByStaff(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTreasuryByStaffNullStaffIdTest() {
        treasuryService.getTreasuriesByStaff(null);
    }
}