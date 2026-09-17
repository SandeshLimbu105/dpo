package org.texas.dposervice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.texas.dposervice.entity.Consent;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, Long> {

    long countByStatus(String status);

    long countByCitizenNid(String citizenNid);
}