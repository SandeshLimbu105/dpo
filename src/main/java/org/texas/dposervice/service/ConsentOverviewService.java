package org.texas.dposervice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.texas.dposervice.dto.ConsentOverviewResponse;
import org.texas.dposervice.repository.ConsentRepository;

@Service
public class ConsentOverviewService {

    private static final String STATUS_ACTIVE = "ACTIVE";
    private static final String STATUS_EXPIRED = "EXPIRED";
    private static final String STATUS_REVOKED = "REVOKED";

    private final ConsentRepository consentRepository;

    public ConsentOverviewService(ConsentRepository consentRepository) {
        this.consentRepository = consentRepository;
    }

    @Transactional(readOnly = true)
    public ConsentOverviewResponse getOverview() {
        long total = consentRepository.count();
        long active = consentRepository.countByStatus(STATUS_ACTIVE);
        long expired = consentRepository.countByStatus(STATUS_EXPIRED);
        long revoked = consentRepository.countByStatus(STATUS_REVOKED);

        return ConsentOverviewResponse.builder()
                .totalConsents(total)
                .activeConsents(active)
                .expiredConsents(expired)
                .revokedConsents(revoked)
                .build();
    }
}