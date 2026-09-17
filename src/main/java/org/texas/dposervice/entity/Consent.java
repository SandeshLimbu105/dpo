package org.texas.dposervice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Read-only view of the shared consents table.
 * Simplified for DPO use — no Citizen relation, no ConsentField relation.
 * DPO computes consent statistics without ever traversing into citizen data.
 */
@Entity
@Table(name = "consents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consent {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "citizen_nid", length = 20, nullable = false)
    private String citizenNid;

    @Column(name = "requesting_system", length = 50, nullable = false)
    private String requestingSystem;

    @Column(name = "purpose", length = 100, nullable = false)
    private String purpose;

    @Column(name = "granted", nullable = false)
    private Boolean granted;

    @Column(name = "granted_at", nullable = false)
    private LocalDateTime grantedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;

    @Column(name = "status", length = 20, nullable = false)
    private String status;
}