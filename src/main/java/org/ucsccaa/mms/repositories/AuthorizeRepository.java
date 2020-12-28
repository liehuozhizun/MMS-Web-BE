package org.ucsccaa.mms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ucsccaa.mms.domains.Authorization;

@Repository
public interface AuthorizeRepository extends JpaRepository<Authorization, Long> {
//    Boolean checkAuthority(String level, String authority);
}
