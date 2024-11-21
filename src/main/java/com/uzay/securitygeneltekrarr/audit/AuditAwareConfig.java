package com.uzay.securitygeneltekrarr.audit;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration("AuditAwareConfig")
public class AuditAwareConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ROLE_ADMIN");

    }
}
