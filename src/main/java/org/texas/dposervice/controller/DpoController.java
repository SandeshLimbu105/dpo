package org.texas.dposervice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.texas.dposervice.dto.AuditLogResponse;
import org.texas.dposervice.dto.ConsentOverviewResponse;
import org.texas.dposervice.service.AuditQueryService;
import org.texas.dposervice.service.ConsentOverviewService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/dpo")
@Tag(name = "DPO — Audit & Monitoring",
        description = "Read-only access to audit logs and consent statistics")
public class DpoController {

    private final AuditQueryService auditQueryService;
    private final ConsentOverviewService consentOverviewService;

    public DpoController(AuditQueryService auditQueryService,
                         ConsentOverviewService consentOverviewService) {
        this.auditQueryService = auditQueryService;
        this.consentOverviewService = consentOverviewService;
    }

    @GetMapping("/audit")
    @PreAuthorize("hasRole('DPO')")
    @Operation(
            summary = "Query audit logs with filters and pagination",
            description = """
            All filters are optional. Results sorted by timestamp descending.

            Examples:
            - /api/dpo/audit?page=0&size=20
            - /api/dpo/audit?result=DENIED
            - /api/dpo/audit?system=SYSTEM_B&citizenNid=1234567890
            - /api/dpo/audit?from=2026-01-01T00:00:00&to=2026-12-31T23:59:59
            """
    )
    public ResponseEntity<Page<AuditLogResponse>> queryAudit(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(required = false) String system,
            @RequestParam(required = false) String officer,
            @RequestParam(required = false) String citizenNid,
            @RequestParam(required = false) String purpose,
            @RequestParam(required = false) String result) {

        Page<AuditLogResponse> response = auditQueryService.query(
                page, size, from, to, system, officer, citizenNid, purpose, result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/audit/{id}")
    @PreAuthorize("hasRole('DPO')")
    @Operation(summary = "Get a single audit log entry by ID")
    public ResponseEntity<AuditLogResponse> getAuditById(@PathVariable Long id) {
        return ResponseEntity.ok(auditQueryService.findById(id));
    }

    @GetMapping("/consent-overview")
    @PreAuthorize("hasRole('DPO')")
    @Operation(summary = "Get consent statistics (counts by status)")
    public ResponseEntity<ConsentOverviewResponse> consentOverview() {
        return ResponseEntity.ok(consentOverviewService.getOverview());
    }
}