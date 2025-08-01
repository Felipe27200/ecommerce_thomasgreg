package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit,Long>
{
    List<Audit> findByEntity(String entity);
}
