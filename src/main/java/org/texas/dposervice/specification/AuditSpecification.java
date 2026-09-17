package org.texas.dposervice.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.texas.dposervice.entity.AuditLog;

import java.time.LocalDateTime;

public final class AuditSpecification {

    private AuditSpecification() {}

    public static Specification<AuditLog> timestampAfter(LocalDateTime from) {
        if (from == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("timestamp"), from);
    }

    public static Specification<AuditLog> timestampBefore(LocalDateTime to) {
        if (to == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("timestamp"), to);
    }

    public static Specification<AuditLog> hasSystem(String system) {
        if (system == null || system.isBlank()) return null;
        return (root, query, cb) -> cb.equal(root.get("requestingSystem"), system);
    }

    public static Specification<AuditLog> hasOfficer(String officer) {
        if (officer == null || officer.isBlank()) return null;
        return (root, query, cb) -> cb.equal(root.get("officerId"), officer);
    }

    public static Specification<AuditLog> hasCitizenNid(String nid) {
        if (nid == null || nid.isBlank()) return null;
        return (root, query, cb) -> cb.equal(root.get("citizenNid"), nid);
    }

    public static Specification<AuditLog> hasPurpose(String purpose) {
        if (purpose == null || purpose.isBlank()) return null;
        return (root, query, cb) -> cb.equal(root.get("purpose"), purpose);
    }

    public static Specification<AuditLog> hasResult(String result) {
        if (result == null || result.isBlank()) return null;
        return (root, query, cb) -> {
            try {
                return cb.equal(
                        root.get("result"),
                        org.texas.dposervice.entity.enums.AuditResult.valueOf(result.toUpperCase())
                );
            } catch (IllegalArgumentException e) {
                // Invalid enum value — match nothing
                return cb.disjunction();
            }
        };
    }
}