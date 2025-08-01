package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.entity.Audit;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.enums.audit.Action;
import com.ecommerce.ecommerce.enums.audit.Entity;
import com.ecommerce.ecommerce.repository.AuditRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuditService
{
    private AuditRepository auditRepository;

    @Autowired
    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Transactional
    public Audit create(Action action, Entity entity, Long entityId, User user)
    {
        Audit audit = new Audit();

        audit.setAction(action.getAction());
        audit.setDateTime(LocalDateTime.now());
        audit.setEntity(entity.getEntity());
        audit.setEntitdyId(entityId);
        audit.setUser(user);

        return auditRepository.save(audit);
    }

    public Audit findById(Long id)
    {
        if (id == null || id <= 0L)
            throw new IllegalArgumentException("Invalid id: " + id);

        Optional<Audit> audit = auditRepository.findById(id);

        return audit.orElse(null);
    }

    public List<Audit> findByEntity(Entity entity)
    {
        return auditRepository.findAll();
    }
}
