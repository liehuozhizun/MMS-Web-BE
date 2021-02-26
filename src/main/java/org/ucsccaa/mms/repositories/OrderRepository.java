package org.ucsccaa.mms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.domains.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    public List<Orders> findByMember(Member member);
}
