package org.ucsccaa.mms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ucsccaa.mms.domains.Authorization;


@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {
    Authorization findByLevel(Authorization.LEVEL level);
}
