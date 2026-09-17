package org.texas.dposervice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponse {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private String requestingSystem;
    private String officerId;
    private String citizenNid;
    private String purpose;
    private String consentStatus;
    private String fieldsReturned;
    private Integer responseTimeMs;
    private String result;
    private String ipAddress;
}