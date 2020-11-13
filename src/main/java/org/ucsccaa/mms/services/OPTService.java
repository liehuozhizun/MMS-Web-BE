package org.ucsccaa.mms.services;

import org.ucsccaa.mms.domains.OPT;

import java.util.List;
import java.util.Optional;

public interface OPTService {
    Long createOPT(OPT opt);
    Optional<OPT> updateOPTByID(OPT opt);
    void deleteOPT(Long id);
    Optional<OPT> findOPTByID(Long id);
    List<OPT> listAll();
}
