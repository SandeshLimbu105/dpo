package org.texas.dposervice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsentOverviewResponse {

    private long totalConsents;
    private long activeConsents;
    private long expiredConsents;
    private long revokedConsents;
}