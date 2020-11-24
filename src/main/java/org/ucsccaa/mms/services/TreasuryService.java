package org.ucsccaa.mms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ucsccaa.mms.repositories.StaffRepository;
import org.ucsccaa.mms.repositories.TreasuryRepository;
import org.ucsccaa.mms.domains.Staff;
import org.ucsccaa.mms.domains.Treasury;

import java.util.List;
import java.util.Optional;

@Service
public class TreasuryService {

    @Autowired
    private TreasuryRepository treasuryRepository;

    @Autowired
    private StaffRepository staffRepository;

    public Long addTreasury(Treasury treasury) {
        if (treasury == null) {
            throw new IllegalArgumentException("Treasury cannot be null");
        }
        if (treasury.getId() != null) {
            throw new IllegalArgumentException("Treasury ID cannot be predefined");
        }
        return treasuryRepository.save(treasury).getId();
    }

    public Optional<Treasury> updateTreasury(Treasury treasury) {
        if (treasury == null) {
            throw new IllegalArgumentException("Treasury cannot be null");
        } else if (treasury.getId() == null) {
            throw new IllegalArgumentException("Treasury ID cannot be null");
        } else if (!treasuryRepository.existsById(treasury.getId())) {
            return Optional.empty();
        }
        return Optional.of(treasuryRepository.save(treasury));
    }

    public Optional<Treasury> getTreasury(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Treasury ID cannot be null");
        }
        return treasuryRepository.findById(id);
    }

    public Boolean deleteTreasury(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Treasury ID cannot be null");
        } else if (!treasuryRepository.existsById(id)) {
            return false;
        }
        treasuryRepository.deleteById(id);
        return true;
    }

    public List<Treasury> getTreasuriesByStaff(Long staffId) {
        if (staffId == null) {
            throw new IllegalArgumentException("Staff ID cannot be null");
        }
        Optional<Staff> staff = staffRepository.findById(staffId);
        if (!staff.isPresent()) {
            throw new IllegalArgumentException("Staff does not exist");
        }
        return treasuryRepository.findByStaff(staff.get());
    }

    public List<Treasury> getAll() {
        return treasuryRepository.findAll();
    }
}
