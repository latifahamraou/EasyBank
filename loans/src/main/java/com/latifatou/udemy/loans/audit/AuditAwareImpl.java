package com.latifatou.udemy.loans.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * pring Data provides sophisticated support to transparently keep track
 * of who created or changed an entity and when the change happened.
 * To benefit from that functionality, you have to equip your entity classes with
 * auditing metadata that can be defined either using annotations
 * or by implementing an interface.
 * Additionally, auditing has to be enabled either through Annotation configuration
 * or XML configuration to register the required infrastructure components.
 * please refer to the store-specific section for configuration samples.
 */
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("LOANS_MS");
    }

}