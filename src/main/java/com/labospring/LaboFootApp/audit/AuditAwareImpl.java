package com.labospring.LaboFootApp.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable("UNDEFINED");

//        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication() != null ?
//                SecurityContextHolder.getContext().getAuthentication().getName() : "UNDEFINED");
    }
}
