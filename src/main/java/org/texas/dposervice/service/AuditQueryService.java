package org.texas.dposervice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.texas.dposervice.dto.AuditLogResponse;
import org.texas.dposervice.entity.AuditLog;
import org.texas.dposervice.exception.ResourceNotFoundException;
import org.texas.dposervice.repository.AuditLogRepository;
import org.texas.dposervice.specification.AuditSpecification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditQueryService {

    private final AuditLogRepository auditLogRepository;

    public AuditQueryService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional(readOnly = true)
    public Page<AuditLogResponse> query(int page,
                                        int size,
                                        LocalDateTime from,
                                        LocalDateTime to,
                                        String system,
                                        String officer,
                                        String citizenNid,
                                        String purpose,
                                        String result) {

        Pageable pageable = PageRequest.of(
                page,
                Math.min(size, 100),
                Sort.by(Sort.Direction.DESC, "timestamp")
        );

        // Collect non-null specifications into a list
        List<Specification<AuditLog>> specs = new ArrayList<>();

        Specification<AuditLog> s1 = AuditSpecification.timestampAfter(from);
        if (s1 != null) specs.add(s1);

        Specification<AuditLog> s2 = AuditSpecification.timestampBefore(to);
        if (s2 != null) specs.add(s2);

        Specification<AuditLog> s3 = AuditSpecification.hasSystem(system);
        if (s3 != null) specs.add(s3);

        Specification<AuditLog> s4 = AuditSpecification.hasOfficer(officer);
        if (s4 != null) specs.add(s4);

        Specification<AuditLog> s5 = AuditSpecification.hasCitizenNid(citizenNid);
        if (s5 != null) specs.add(s5);

        Specification<AuditLog> s6 = AuditSpecification.hasPurpose(purpose);
        if (s6 != null) specs.add(s6);

        Specification<AuditLog> s7 = AuditSpecification.hasResult(result);
        if (s7 != null) specs.add(s7);

        // Combine all non-null specs with AND
        Specification<AuditLog> combined = Specification.allOf(specs);

        return auditLogRepository.findAll(combined, pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public AuditLogResponse findById(Long id) {
        AuditLog log = auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Audit log not found: " + id));
        return toResponse(log);
    }

    private AuditLogResponse toResponse(AuditLog log) {
        return AuditLogResponse.builder()
                .id(log.getId())
                .timestamp(log.getTimestamp())
                .requestingSystem(log.getRequestingSystem())
                .officerId(log.getOfficerId())
                .citizenNid(log.getCitizenNid())
                .purpose(log.getPurpose())
                .consentStatus(log.getConsentStatus())
                .fieldsReturned(log.getFieldsReturned())
                .responseTimeMs(log.getResponseTimeMs())
                .result(log.getResult() != null ? log.getResult().name() : null)
                .ipAddress(log.getIpAddress())
                .build();
    }
}